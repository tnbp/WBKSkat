package wbkskat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class SkatGUI extends JFrame {

    public SkatGUI(Skatspiel sk) {
        setTitle("Skat-Spiel");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // auf x klicken schließt
        setLayout(new BorderLayout());

        // GUI-Bereiche erstellen
        initUI(sk);

        setVisible(true);
        updateUI(sk);
    }

    private void initUI(Skatspiel sk) {
        // TEST!!!
    	
    	// ENDE TEST!!!
    	
    	//  Spielerhände
        JPanel handOben = new JPanel(new GridLayout(1, 0)); // Grid für Zeilen/Spalten
        handOben.setName("handOben");
        JPanel handLinks = new JPanel(new GridLayout(0, 1));
        handLinks.setName("handLinks");
        JPanel handRechts = new JPanel(new GridLayout(0, 1));
        handRechts.setName("handRechts");

        handOben.setBorder(BorderFactory.createTitledBorder(sk.getSpieler()[0].getName()));
        handLinks.setBorder(BorderFactory.createTitledBorder(sk.getSpieler()[2].getName()));
        handRechts.setBorder(BorderFactory.createTitledBorder(sk.getSpieler()[1].getName()));

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
    private JButton createCardButton(Skatspiel sk, Karte k, boolean rueckseite) {
    	String kartenName = k.getFarbe().name() + k.getWert().name();
        String dateiName =  kartenName + ".png";
        if (rueckseite) dateiName = "RUECKSEITE.png";

        // Entferne die Endung .png, .jpg oder .jpeg
        //String cardName = dateiName.replaceAll("\\.(png|jpg|jpeg)$", ""); // regulärer Ausdruck

        ImageIcon icon = new ImageIcon("images/" + dateiName);


        // Bild skalieren und als Icon setzen
        icon = new ImageIcon(icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));

        // Erstelle Button mit Bild & füge Klick-Aktion hinzu
        JButton button = new JButton(icon);
        button.setName(kartenName);
        button.setPreferredSize(new Dimension(80, 120));

        // Ausgabe bei Klick
        button.addActionListener(e -> testfunktion(sk, k));

        return button;
    }
    
    private void testfunktion(Skatspiel Skat, Karte k) {
    	System.out.println(k.getFarbe().name()+k.getWert().name());
    	Stichrunde sr = new Stichrunde(Skat);
    	for (int i = 0; i < Skat.getStichrunde().amZug().getHand().getKarten().size(); i++) {
    		if (Skat.getStichrunde().amZug().getHand().getKarten().get(i).getFarbe() == Kartenfarbe.PIK) {
    			sr.spieleKarte(Skat.getStichrunde().amZug().getHand().getKarten().get(i), Skat.getStichrunde().amZug());
    			break;
    		}
    	}
    	int i = 0;
    	while (!sr.spieleKarte(Skat.getStichrunde().amZug().getHand().getKarten().get(i++), Skat.getStichrunde().amZug())) ;
    	i = 0;
    	while (!sr.spieleKarte(Skat.getStichrunde().amZug().getHand().getKarten().get(i++), Skat.getStichrunde().amZug())) ;
    	updateUI(Skat);
    }
    
    private void updateUI(Skatspiel sk) {
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
        	for (int j = 0; j < sk.getSpieler()[i].getHand().getKarten().size(); j++) {
        		String kartenFarbe = sk.getSpieler()[i].getHand().getKarten().get(j).getFarbe().name();
            	String kartenWert = sk.getSpieler()[i].getHand().getKarten().get(j).getWert().name();
            	String kartenName = kartenFarbe + kartenWert;
                JButton cardButton = createCardButton(sk, sk.getSpieler()[i].getHand().getKarten().get(j), 
                		sk.getStichrunde().amZug() != sk.getSpieler()[i]);
                if (sk.getSpieler()[i].getPosition()==Position.ACHT) handLinks.add(cardButton);
                if (sk.getSpieler()[i].getPosition()==Position.VIER) handRechts.add(cardButton);
                if (sk.getSpieler()[i].getPosition()==Position.ZWOELF) handOben.add(cardButton);
        	}
        }
        Karte skat1 = sk.getSkat()[0];
        Karte skat2 = sk.getSkat()[1];
        File skat1Bild = new File("images/" + skat1.getFarbe().name() + skat1.getWert().name() + ".png");
        File skat2Bild = new File("images/" + skat2.getFarbe().name() + skat2.getWert().name() + ".png");
        JButton cardButton = createCardButton(sk, skat1, true);
        cardButton.setName("skat1");
        skatBereich.add(cardButton);
    	cardButton = createCardButton(sk, skat2, true);
    	cardButton.setName("skat2");
    	skatBereich.add(cardButton);
    	// Karten im Stich
    	for (int i = 0; i < 3; i++) {
    		Karte stichkarte = sk.getStichrunde().getKartenImStich()[i];
    		if (stichkarte == null) break;
    		BufferedImage img = null;
    		try {
    			img = ImageIO.read(new File("images/" + stichkarte.getFarbe().name() + stichkarte.getWert().name() + ".png"));
    		} catch (Exception e) {
    		}
    		Image dimg = img.getScaledInstance(2*100, 2*146, Image.SCALE_SMOOTH);
    		ImageIcon imgico = new ImageIcon(dimg);
    		JLabel imStich = new JLabel(imgico);
    		stichBereich.add(imStich);
    		stichBereich.setComponentZOrder(imStich, 0);
    		int mitteX = stichBereich.getWidth() / 2 - 100;
    		int mitteY = stichBereich.getHeight() / 2 - 146;
    		switch (sk.getStichrunde().hatKarteGespielt(stichkarte).getPosition()) {
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
    		revalidate();
    		repaint();
    	}
    }
}
