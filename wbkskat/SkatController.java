package wbkskat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SkatController {
    
	private Skatspiel skat;
	private SkatGUI gui;
	
	public SkatController() {
		Spielart sp = null;
		
		Spieler S1 = new Spieler("Luffy", Position.ZWOELF);
    	Spieler S2 = new Spieler("Zorro", Position.VIER);
    	Spieler S3 = new Spieler("Buggy", Position.ACHT);
    	
        String spieler1Name = JOptionPane.showInputDialog(
                null,
                "Spieler 1, bitte gib deinen Namen ein:",
                "Spieler 1 Name Eingabe",
                JOptionPane.QUESTION_MESSAGE
        );

        // Überprüfung, ob eine Eingabe gemacht wurde
        if (spieler1Name != null && !spieler1Name.equals("")) {
        	S1.setName(spieler1Name); // Name in Spieler-Objekt speichern
            System.out.println("Spieler 1 heißt jetzt: " + S1.getName());
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
            S2.setName(spieler2Name); // Name in Spieler-Objekt speichern
            System.out.println("Spieler 2 heißt jetzt: " + S2.getName());
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
        	S3.setName(spieler3Name); // Name in Spieler-Objekt speichern
            System.out.println("Spieler 3 heißt jetzt: " + S3.getName());
        } else {
            System.out.println("Kein Name eingegeben.");
        }
        
    	this.skat = new Skatspiel(sp, new Spieler[] {S1,S2,S3}, S1);
    	S1.getHand().sortiereHand(new Spielart(Kartenfarbe.KREUZ));
    	this.skat.setSkatController(this);
	}
	
	public void setGUI(SkatGUI gui) {
		this.gui = gui;
	}
	
	public void setSkatSpiel(Skatspiel skat) {
		this.skat = skat;
	}
	
	public SkatGUI getSkatGUI() {
		return this.gui;
	}
	
	public Skatspiel getSkatSpiel() {
		return this.skat;
	}
	
    public void behandleHandKlick(Karte k) {
    	if (skat.eingabeGesperrt()) return;
    	//System.out.println(k.getFarbe().name()+k.getWert().name());
    	if (skat.getStichrunde() == null) {
    		Stichrunde sr = new Stichrunde(skat);
    	}
    	Spieler besitzer = null;
    	for (int i = 0; i < 3; i++) {
    		if (besitzer != null) break;
    		for (int j = 0; j < skat.getSpieler()[i].getHand().getKarten().size(); j++) {
    			if (skat.getSpieler()[i].getHand().getKarten().get(j) == k) {
    				besitzer = skat.getSpieler()[i];
    				break;
    			}
    		}
    	}
    	System.out.println("Die Karte " + k.getFarbe().name() + k.getWert().name() + " gehört "
    			+ besitzer.getName());
    	if (besitzer == skat.getStichrunde().amZug()) {
    		if (skat.getStichrunde().spieleKarte(k, besitzer)) {
    			SoundPlayer.playSound("sounds/KarteSpielen.wav");
	    		gui.updateUI();
	    		if (skat.getStichrunde().getKartenImStich()[2] != null) {
	    			// Stichrunde zu Ende!
	    			skat.sperreEingabe();
	    			Karte[] stichkarten = skat.getStichrunde().getKartenImStich();
	    			Spieler gewinner = skat.getStichrunde().hatKarteGespielt(skat.getSpielart().machtDenStich(stichkarten[0], 
	    					stichkarten[1], stichkarten[2]));
	    			gewinner.nimmStichZuDir(stichkarten);
	    			System.out.println("Der Stich ist " + PunkteBerechnung.berechnePunkte(stichkarten) + " Augen wert und geht an " + gewinner.getName());
	    			Timer t = new Timer(3000, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.out.println("AAAA!");
							skat.setStichrunde(new Stichrunde(skat));
							skat.getStichrunde().setAmZug(gewinner);
							skat.entsperreEingabe();
							gui.updateUI();
						}
	    			});
	    			t.setRepeats(false);
	    			t.start();
	    		}
    		}
    		else {
    			Karte vorgabe = skat.getStichrunde().getKartenImStich()[0];
    			String zwangFarbe = skat.getSpielart().istTrumpf(vorgabe) ? "Trumpf" : vorgabe.getFarbeName();
    			JOptionPane.showMessageDialog(null, besitzer.getName() + " muss bekennen: " + zwangFarbe + " muss gespielt werden!", "Bekennen oder brennen!", JOptionPane.WARNING_MESSAGE);
    		}
    	}
    	else {
    		JOptionPane.showMessageDialog(null, skat.getStichrunde().amZug().getName() + " ist am Zug!", "Du bist nicht dran!", JOptionPane.WARNING_MESSAGE);
    	}
    }
    
    public void behandleSkatKlick(Karte k) {
    	System.out.println("Finger weg vom Skat!");
    }
	
}
