package wbkskat;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class SkatGUI extends JFrame {

    public SkatGUI() {
        setTitle("Skat-Spiel");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //auf x klicken schließt
        setLayout(new BorderLayout());

        // GUI-Bereiche erstellen
        initUI();

        setVisible(true);
    }

    private void initUI() {
        // TEST!!!
    	Spieler S1 = new Spieler ("Luffy", Position.ZWOELF);
    	Spieler S2 = new Spieler ("Zorro", Position.VIER);
    	Spieler S3 = new Spieler ("Buggy", Position.ACHT);
    	Spielart SP = new Spielart(Kartenfarbe.PIK);
    	Skatspiel Skat = new Skatspiel(SP, new Spieler[] {S1,S2,S3}, S1);
    	
    	// ENDE TEST!!!
    	
    	//  Spielerhände
        JPanel handOben = new JPanel(new GridLayout(1, 0)); //Grid für Zeilen/Spalten
        JPanel handLinks = new JPanel(new GridLayout(0, 1));
        JPanel handRechts = new JPanel(new GridLayout(0, 1));

        handOben.setBorder(BorderFactory.createTitledBorder(Skat.getSpieler()[0].getName()));
        handLinks.setBorder(BorderFactory.createTitledBorder(Skat.getSpieler()[2].getName()));
        handRechts.setBorder(BorderFactory.createTitledBorder(Skat.getSpieler()[1].getName()));

        //  Hände
        handOben.setPreferredSize(new Dimension(800, 150));
        handLinks.setPreferredSize(new Dimension(200, 600));
        handRechts.setPreferredSize(new Dimension(200, 600));
        
        //  Stich-Bereich 
        JPanel stichBereich = new JPanel();
        stichBereich.setBorder(BorderFactory.createTitledBorder("Stich"));
        stichBereich.setPreferredSize(new Dimension(150, 100));

        //  Skat-Bereich 
        JPanel skatBereich = new JPanel();
        skatBereich.setBorder(BorderFactory.createTitledBorder("Skat"));
        skatBereich.setPreferredSize(new Dimension(80, 80));

        // Punkteanzeige
        JLabel punkteAnzeige = new JLabel("Punkte: 0 - 0 - 0", SwingConstants.CENTER);
        punkteAnzeige.setFont(new Font("Arial", Font.BOLD, 16));

        //  Lade alle Bilder  aus dem images-Ordner
        File imageFolder = new File("images/");
        for (int i = 0; i<3; i++) {
        	for (int j = 0; j < Skat.getSpieler()[i].getHand().getKarten().size(); j++) {
        		String kartenfarbe =Skat.getSpieler()[i].getHand().getKarten().get(j).getFarbe().name();
            	String kartenwert = Skat.getSpieler()[i].getHand().getKarten().get(j).getWert().name();
            	File kartenBild = new File("images/"+kartenfarbe+kartenwert+".png");
            	if (isImageFile(kartenBild)){ // Nur Bilder laden
                    JButton cardButton = createCardButton(kartenBild);
                    if(Skat.getSpieler()[i].getPosition()==Position.ACHT) handLinks.add(cardButton);
                    if(Skat.getSpieler()[i].getPosition()==Position.VIER) handRechts.add(cardButton);
                    if(Skat.getSpieler()[i].getPosition()==Position.ZWOELF) handOben.add(cardButton);
            	}
        	}       
        }
        Karte skat1 = Skat.getSkat()[0];
        Karte skat2 = Skat.getSkat()[1];
        File skat1Bild = new File("images/"+skat1.getFarbe().name()+skat1.getWert().name()+".png");
        File skat2Bild = new File("images/"+skat2.getFarbe().name()+skat2.getWert().name()+".png");
        if(isImageFile(skat1Bild)) {
        	JButton cardbutton = createCardButton(skat1Bild);
        	skatBereich.add(cardbutton);
        	
        }
        if(isImageFile(skat2Bild)) {
        	JButton cardbutton = createCardButton(skat2Bild);
        	skatBereich.add(cardbutton);
        	
        }
       
        /* if (imageFolder.exists() && imageFolder.isDirectory()) {
            for (File file : Objects.requireNonNull(imageFolder.listFiles())) {
                if (isImageFile(file)) { // Nur Bilder laden
                    JButton cardButton = createCardButton(file);
                    handLinks.add(cardButton);
                }
            }
        } else {
            System.out.println("Fehler: Der Ordner 'images/' wurde nicht gefunden!");
        }  GUTER CODE */ 

      

        // Layout setzen
        add(handOben, BorderLayout.NORTH);
        add(handLinks, BorderLayout.WEST);
        add(handRechts, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(stichBereich, BorderLayout.CENTER);
        centerPanel.add(skatBereich, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        add(punkteAnzeige, BorderLayout.SOUTH);
    }

    //  Prüft, ob eine Datei eine Bilddatei ist (PNG oder JPEG)
    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
    }

 //// Methode zum Erstellen einer Karten-Schaltfläche mit skaliertem Bild und Klick-Aktion
    private JButton createCardButton(File imageFile) {
        String fileName = imageFile.getName();

        // Entferne die Endung .png, .jpg oder .jpeg
        String cardName = fileName.replaceAll("\\.(png|jpg|jpeg)$", "");

        ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());


        // Bild skalieren und als Icon setzen
        icon = new ImageIcon(icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));

        // Erstelle Button mit Bild & füge Klick-Aktion hinzu
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(80, 120));

        // Ausgabe bei Klick
        button.addActionListener(e -> System.out.println(cardName + " wird gespielt!"));

        return button;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(SkatGUI::new);
    }
}
