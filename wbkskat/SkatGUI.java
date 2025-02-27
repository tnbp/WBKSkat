package wbkskat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SkatGUI extends JFrame {
	
	//private Skatspiel skat;
	private SkatController sc;

    public SkatGUI(Skatspiel skat, SkatController sc) {
        this.sc = sc;
        sc.setGUI(this);
        setTitle("Skat-Spiel");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // auf x klicken schließt
        setLayout(new BorderLayout());

        // GUI-Bereiche erstellen
        initUI(skat);

        setVisible(true);
        updateUI(skat);
    	String[] spielartOptionen = { "Farbspiel", "Grand", "Null" };
        int spielartWahl = -1;
        while (spielartWahl < 0) {
        	spielartWahl = JOptionPane.showOptionDialog(null, "Was willst du spielen?", "Spielart wählen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, spielartOptionen, null);
        }
        /*if (spielartWahl == 1) {
        	JOptionPane.showMessageDialog(null, "Grand? Du meintest wohl \"Farbspiel\"!", "Grand noch nicht implementiert...", JOptionPane.WARNING_MESSAGE);
        	spielartWahl = 0;
        }
        if (spielartWahl == 2) {
        	JOptionPane.showMessageDialog(null, "Null? Du meintest wohl \"Farbspiel\"!", "Null noch nicht implementiert...", JOptionPane.WARNING_MESSAGE);
        	spielartWahl = 0;
        }*/
        String wieHeisstEr = null;
        int spruchWahl = 0;
        switch (spielartWahl) {
        	case 0:
        	int farbspielWahl = -1;
        	String[] farbspielOptionen = { "Karo♦", "Herz♥", "Pik♠", "Kreuz♣" };
        	while (farbspielWahl < 0) {
        		farbspielWahl = JOptionPane.showOptionDialog(null, "Welche Farbe soll Trumpf sein?", "Farbe wählen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, farbspielOptionen, null);
        	}
        	wieHeisstEr = farbspielOptionen[farbspielWahl] + " ist angesagt!";
        	spruchWahl = farbspielWahl;
        	break;
        	
        	case 1:
        	spruchWahl = 4;
        	break;
        	
        	case 2:
        	spruchWahl = ((Math.random() * 2) > 1) ? 5 : 6;
        	break;
        }
        String[] skatSprueche = {
        		"Wer nicht weiß, wie und wo, der spielt Karo!",
        		"Wer kein Herz hat, ist ein Lump!",
        		"Grünes Gras frisst der Haas!",
        		"Ein Kreuz hat jeder!",
        		"Beim Grand spielt man Asse, oder man soll's lasse!",
        		"7, 9, und Unter, da geht keiner drunter!",
        		"Ein Null gibt immer Contra!"
        };
        JOptionPane.showMessageDialog(null, skatSprueche[spruchWahl], wieHeisstEr, JOptionPane.WARNING_MESSAGE);
    }

    private void initUI(Skatspiel skat) {
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

        
        add(handOben, BorderLayout.NORTH);
        add(handLinks, BorderLayout.WEST);
        add(handRechts, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setName("centerPanel");
        centerPanel.add(stichBereich, BorderLayout.CENTER);
        centerPanel.add(skatBereich, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        add(punkteAnzeige, BorderLayout.SOUTH);
        
     // TEST!!!
    	// Eingabefeld für Spieler 1
        String spieler1Name = JOptionPane.showInputDialog(
                null,
                "Spieler 1, bitte gib deinen Namen ein:",
                "Spieler 1 Name Eingabe",
                JOptionPane.QUESTION_MESSAGE
        );

        // Überprüfung, ob eine Eingabe gemacht wurde
        if (spieler1Name != null && !spieler1Name.equals("")) {
        	skat.getSpieler()[0].setName(spieler1Name); // Name in Spieler-Objekt speichern
            System.out.println("Spieler 1 heißt jetzt: " + skat.getSpieler()[0].getName());

            // GUI aktualisieren, damit der neue Name sofort angezeigt wird
            handOben.setBorder(BorderFactory.createTitledBorder(skat.getSpieler()[0].getName()));
            handOben.repaint(); // Neu rendern
        } else {
            System.out.println("Kein Name eingegeben.");
        }
        
        String spieler2Name = JOptionPane.showInputDialog(
                null,
                "Spieler 2, bitte gib deinen Namen ein:",
                "Spieler 2 Name Eingabe",
                JOptionPane.QUESTION_MESSAGE
        );
        if (spieler2Name != null && !spieler2Name.equals("")) {
            skat.getSpieler()[1].setName(spieler2Name); // Name in Spieler-Objekt speichern
            System.out.println("Spieler 2 heißt jetzt: " + skat.getSpieler()[1].getName());

            // GUI aktualisieren, damit der neue Name sofort angezeigt wird
            handRechts.setBorder(BorderFactory.createTitledBorder(skat.getSpieler()[1].getName()));
            handRechts.repaint(); // Neu rendern
        } else {
            System.out.println("Kein Name eingegeben.");
        }
        
        String spieler3Name = JOptionPane.showInputDialog(
                null,
                "Spieler 3, bitte gib deinen Namen ein:",
                "Spieler 3 Name Eingabe",
                JOptionPane.QUESTION_MESSAGE
        );
        if (spieler3Name != null && !spieler3Name.equals("")) {
        	skat.getSpieler()[2].setName(spieler3Name); // Name in Spieler-Objekt speichern
            System.out.println("Spieler 3 heißt jetzt: " + skat.getSpieler()[2].getName());

            // GUI aktualisieren, damit der neue Name sofort angezeigt wird
            handLinks.setBorder(BorderFactory.createTitledBorder(skat.getSpieler()[2].getName()));
            handLinks.repaint(); // Neu rendern
        } else {
            System.out.println("Kein Name eingegeben.");
        }

    	// ENDE TEST!!!
    }

    // Methode zum Erstellen einer Karten-Schaltfläche mit skaliertem Bild und Klick-Aktion
    private JButton erzeugeButton(Karte k, boolean rueckseite) {
    	String kartenName = k.getFarbe().name() + k.getWert().name();
        String dateiName =  kartenName + ".png";
        if (rueckseite) dateiName = "RUECKSEITE.png";

        ImageIcon icon = new ImageIcon("images/" + dateiName);


        // Bild skalieren und als Icon setzen
        icon = new ImageIcon(icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));

        // Erstelle Button mit Bild & füge Klick-Aktion hinzu
        JButton button = new JButton(icon);
        button.setName(kartenName);
        button.setPreferredSize(new Dimension(80, 120));

        return button;
    }
    
    private JButton erzeugeSkatButton(Karte k, boolean rueckseite) {
    	JButton skatButton = erzeugeButton(k, rueckseite);
        skatButton.addActionListener(e -> this.sc.behandleSkatKlick(k));
        return skatButton;
    }
    
    private JButton erzeugeHandButton(Karte k, boolean rueckseite) {
    	JButton handButton = erzeugeButton(k, rueckseite);
        handButton.addActionListener(e -> this.sc.behandleHandKlick(k));
        return handButton;
    }
    
    public void updateUI(Skatspiel skat) {
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
                		verdeckt);
                if (skat.getSpieler()[i].getPosition()==Position.ACHT) handLinks.add(cardButton);
                if (skat.getSpieler()[i].getPosition()==Position.VIER) handRechts.add(cardButton);
                if (skat.getSpieler()[i].getPosition()==Position.ZWOELF) handOben.add(cardButton);
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
}
