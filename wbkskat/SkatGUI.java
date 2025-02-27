package wbkskat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SkatGUI extends JFrame {

	private static final long serialVersionUID = 263116004611475177L;
	//private Skatspiel skat;
	private SkatController sc;

    public SkatGUI(SkatController sc) {
        this.sc = sc;
        Skatspiel skat = this.sc.getSkatSpiel();
        sc.setGUI(this);
        setTitle("Skat-Spiel");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // auf x klicken schließt
        setLayout(new BorderLayout());

        // GUI-Bereiche erstellen
        initUI();

        setVisible(true);
        updateUI();
        if (skat.getSpielart() == null) {
        	skat.setupSpielart();
        }
    }

    private void initUI() {
    	Skatspiel skat = this.sc.getSkatSpiel();
    	//  Spielerhände
        JPanel handOben = new JPanel(new GridLayout(1, 0)); // Grid für Zeilen/Spalten
        handOben.setName("handOben");
        JPanel handLinks = new JPanel(new GridLayout(0, 1));
        handLinks.setName("handLinks");
        JPanel handRechts = new JPanel(new GridLayout(0, 1));
        handRechts.setName("handRechts");

        handOben.setBorder(BorderFactory.createTitledBorder(skat.getSpieler()[0].getName()));
        handLinks.setBorder(BorderFactory.createTitledBorder(skat.getSpieler()[2].getName()));
        handRechts.setBorder(BorderFactory.createTitledBorder(skat.getSpieler()[1].getName()));

        //  Hände
        handOben.setPreferredSize(new Dimension(800, 150));
        handLinks.setPreferredSize(new Dimension(200, 600));
        handRechts.setPreferredSize(new Dimension(200, 600));
        
        //  Stich-Bereich 
        JLayeredPane stichBereich = new JLayeredPane();
        stichBereich.setName("stichBereich");
        stichBereich.setBorder(BorderFactory.createTitledBorder("Stich"));
        stichBereich.setPreferredSize(new Dimension(150, 100));
        stichBereich.setLayout(null);
        
        //  Skat-Bereich
        JPanel skatBereich = new JPanel();
        skatBereich.setName("skatBereich");
        skatBereich.setBorder(BorderFactory.createTitledBorder("Skat"));
        skatBereich.setPreferredSize(new Dimension(80, 80));

        // Punkteanzeige
        JLabel punkteAnzeige = new JLabel("Punkte: 0 - 0 - 0", SwingConstants.CENTER);
        punkteAnzeige.setName("punkteAnzeige");
        punkteAnzeige.setFont(new Font("Arial", Font.BOLD, 16));

        /*JButton soundButton = new JButton("Sound abspielen");
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("sounds/KarteSpielen.wav");
            }
        });*/
        
        add(handOben, BorderLayout.NORTH);
        add(handLinks, BorderLayout.WEST);
        add(handRechts, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setName("centerPanel");
        centerPanel.add(stichBereich, BorderLayout.CENTER);
        centerPanel.add(skatBereich, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        add(punkteAnzeige, BorderLayout.SOUTH);
    }

    // Methode zum Erstellen einer Karten-Schaltfläche mit skaliertem Bild und Klick-Aktion
    private JButton erzeugeButton(Karte k, boolean rueckseite, Position pos) {
    	String kartenName = k.getFarbe().name() + k.getWert().name();
        String dateiName =  kartenName + ".png";
        if (rueckseite) dateiName = "RUECKSEITE.png";

        ImageIcon icon = new ImageIcon("images/" + dateiName);

        // Bild drehen? und skalieren
        if (pos == null) icon = new StretchIcon(icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));
        else switch (pos) {
        	default:
        	case ZWOELF:
        	icon = new StretchIcon(icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));
        	break;
        	
        	case ACHT:
        	icon = new ImageIcon(rotateClockwise90(toBufferedImage(icon.getImage())));
        	icon = new StretchIcon(icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH));
        	break;
        	
        	case VIER:
    		icon = new ImageIcon(rotateCounterClockwise90(toBufferedImage(icon.getImage())));
        	icon = new StretchIcon(icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH));
        	break;
        }

        // Erstelle Button mit Bild & füge Klick-Aktion hinzu
        JButton button = new JButton(icon);
        button.setName(kartenName);
        button.setPreferredSize(new Dimension(80, 120));

        return button;
    }
    
    private JButton erzeugeSkatButton(Karte k, boolean rueckseite) {
    	JButton skatButton = erzeugeButton(k, rueckseite, null);
        skatButton.addActionListener(e -> this.sc.behandleSkatKlick(k));
        skatButton.setToolTipText("Karte im Skat");
        return skatButton;
    }
    
    private JButton erzeugeHandButton(Karte k, boolean rueckseite, Position pos) {
    	JButton handButton = erzeugeButton(k, rueckseite, pos);
        handButton.addActionListener(e -> this.sc.behandleHandKlick(k));
        if (rueckseite) handButton.setToolTipText(k.getHand().getBesitzer().getName() + "s Karte");
        else handButton.setToolTipText(k.getFarbeName() + " " + k.getWertName());
        return handButton;
    }
    
    public void updateUI() {
    	Skatspiel skat = this.sc.getSkatSpiel();
    	Component[] frameElemente = this.getContentPane().getComponents();
    	JPanel handOben = null, handLinks = null, handRechts = null, 
    			centerPanel = null, skatBereich = null;
    	JLayeredPane stichBereich = null;
    	JLabel punkteAnzeige = null;
    	for (int i = 0; i < frameElemente.length; i++) {
    		if (frameElemente[i].getName() == "handOben") handOben = (JPanel) frameElemente[i];
    		if (frameElemente[i].getName() == "handLinks") handLinks = (JPanel) frameElemente[i];
    		if (frameElemente[i].getName() == "handRechts") handRechts = (JPanel) frameElemente[i];
    		if (frameElemente[i].getName() == "centerPanel") centerPanel = (JPanel) frameElemente[i];
    		if (frameElemente[i].getName() == "punkteAnzeige") punkteAnzeige = (JLabel) frameElemente[i];
    	}
    	for (int i = 0; i < centerPanel.getComponents().length; i++) {
    		if (centerPanel.getComponents()[i].getName() == "stichBereich") stichBereich = (JLayeredPane) centerPanel.getComponents()[i];
    		if (centerPanel.getComponents()[i].getName() == "skatBereich") skatBereich = (JPanel) centerPanel.getComponents()[i];
    	}
    	handOben.removeAll();
    	handLinks.removeAll();
    	handRechts.removeAll();
    	stichBereich.removeAll();
    	skatBereich.removeAll();
        for (int i = 0; i < 3; i++) {
        	for (int j = 0; j < skat.getSpieler()[i].getHand().getKarten().size(); j++) {
        		String kartenFarbe = skat.getSpieler()[i].getHand().getKarten().get(j).getFarbe().name();
            	String kartenWert = skat.getSpieler()[i].getHand().getKarten().get(j).getWert().name();
            	String kartenName = kartenFarbe + kartenWert;
            	boolean verdeckt = false;
            	if (skat.getStichrunde() == null) {
            		// Beginn des Spiels - noch keine Stichrunde!
            		verdeckt = (skat.werKommtRaus() != skat.getSpieler()[i]);
            	}
            	else if (skat.getStichrunde().getKartenImStich()[2] != null) verdeckt = true;
            	else verdeckt = skat.getStichrunde().amZug() != skat.getSpieler()[i];
                JButton cardButton = erzeugeHandButton(skat.getSpieler()[i].getHand().getKarten().get(j), 
                		verdeckt, skat.getSpieler()[i].getPosition());
                if (skat.getSpieler()[i].getPosition() == Position.ACHT) handLinks.add(cardButton);
                if (skat.getSpieler()[i].getPosition() == Position.VIER) handRechts.add(cardButton);
                if (skat.getSpieler()[i].getPosition() == Position.ZWOELF) handOben.add(cardButton);
        	}
        }
        Karte skat1 = skat.getSkat()[0];
        Karte skat2 = skat.getSkat()[1];
        File skat1Bild = new File("images/" + skat1.getFarbe().name() + skat1.getWert().name() + ".png");
        File skat2Bild = new File("images/" + skat2.getFarbe().name() + skat2.getWert().name() + ".png");
        JButton cardButton = erzeugeSkatButton(skat1, true);
        cardButton.setName("skat1");
        skatBereich.add(cardButton);
    	cardButton = erzeugeSkatButton(skat2, true);
    	cardButton.setName("skat2");
    	skatBereich.add(cardButton);
    	// Karten im Stich
    	for (int i = 0; i < 3; i++) {
    		if (skat.getStichrunde() == null) break;
    		Karte stichkarte = skat.getStichrunde().getKartenImStich()[i];
    		if (stichkarte == null) break;
    		ImageIcon img = new ImageIcon("images/" + stichkarte.getFarbe().name() + stichkarte.getWert().name() + ".png");
    		img = new ImageIcon(img.getImage().getScaledInstance(2*100, 2*146, Image.SCALE_SMOOTH));
    		JLabel imStich = new JLabel(img);
    		stichBereich.add(imStich);
    		stichBereich.setComponentZOrder(imStich, 0);
    		int mitteX = stichBereich.getWidth() / 2 - 100;
    		int mitteY = stichBereich.getHeight() / 2 - 146;
    		switch (skat.getStichrunde().hatKarteGespielt(stichkarte).getPosition()) {
    			case VIER:
    			//mitteY += 50;
    			mitteX += 100;
    			break;
    			
    			case ACHT:
    			mitteY += 80;
    			mitteX -= 50;
    			break;
    			
    			case ZWOELF:
    			mitteY -= 100;
    			break;
    		}
    		imStich.setLocation(mitteX, mitteY);
    		imStich.setSize(2*100, 2*146);
    	}
		revalidate();
		repaint();
    }
    
    // shamelessly stolen from stackoverflow.com/questions/20959796
    public static BufferedImage rotateClockwise90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest = new BufferedImage(height, width, src.getType());
        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }
    
    // screw efficiency :^)
    public static BufferedImage rotateCounterClockwise90(BufferedImage src) {
        return rotateClockwise90(rotateClockwise90(rotateClockwise90(src)));
    }
    
    // shamelessly stolen from stackoverflow.com/questions/13605248
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
