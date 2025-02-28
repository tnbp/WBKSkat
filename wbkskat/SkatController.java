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
        
        beginneSkatspiel(sp, new Spieler[] { S1, S2, S3 }, null);
        
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
    	if (skat.getSkatDruecken()) {
    		// bei Klick wird eine Karte gedrückt, nicht gespielt
    		k.getHand().getBesitzer().drueckeSkat(k);
    		k.getHand().legeAb(k);
    		skat.skatGedrueckt();
    		gui.updateUI();
    		return;
    	}
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
	    			if (gewinner.istAlleinspieler() && skat.getSpielart().getSpielArt() == Spielart.NULLSPIEL) {
	    				// Alleinspieler hat einen Stich gemacht und verloren!!
	    				beendeSkatspiel();
	    				return;
	    			}
	    			Timer t = new Timer(3000, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skat.setStichrunde(new Stichrunde(skat));
							skat.getStichrunde().setAmZug(gewinner);
							skat.entsperreEingabe();
							SoundPlayer.playSound("sounds/StichNehmen.wav");
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
    			JOptionPane.showMessageDialog(gui.getRootPane(), besitzer.getName() + " muss bekennen: " + zwangFarbe + " muss gespielt werden!", "Bekennen oder brennen!", JOptionPane.WARNING_MESSAGE);
    		}
    	}
    	else {
    		JOptionPane.showMessageDialog(gui.getRootPane(), skat.getStichrunde().amZug().getName() + " ist am Zug!", "Du bist nicht dran!", JOptionPane.WARNING_MESSAGE);
    	}
    }
    
    public void behandleSkatKlick(Karte k) {
    	System.out.println("Finger weg vom Skat!");
    }
    
    public void beginneSkatspiel(Spielart sp, Spieler[] spieler, Spieler kommtRaus) {
    	if (kommtRaus == null) kommtRaus = spieler[0];
    	this.skat = new Skatspiel(sp, new Spieler[] { spieler[0], spieler[1], spieler[2]}, kommtRaus);
    	kommtRaus.getHand().sortiereHand(new Spielart(Kartenfarbe.KREUZ));
    	this.skat.setSkatController(this);
    }
    
    public void beendeSkatspiel() {
    	boolean ergebnis = PunkteBerechnung.bestimmeGewinn(skat);
    	Spieler alleinspieler = skat.getAlleinspieler();
    	String spielBeendetMeldung = alleinspieler.getName() + " " + (ergebnis ? "gewinnt" : "verliert") + " das ";
    	String spielName = "";
    	switch (skat.getSpielart().getSpielArt()) {
    		default:
    		case Spielart.FARBSPIEL:
    		spielName = "Farbspiel";
    		break;
    		
    		case Spielart.GRAND:
    		spielName = "Grand";
    		break;
    		
    		case Spielart.NULLSPIEL:
    		spielName = "Nullspiel";
    		break;
    	}
    	if (skat.getSpielart().getHandspiel()) spielName += " Hand";
    	if (skat.getSpielart().getOuvert()) spielName += " Ouvert";
    	if (skat.getSpielart().getSchwarzAngesagt()) spielName += " (Schwarz angesagt)";
    	else if (skat.getSpielart().getSchneiderAngesagt()) spielName += " (Schneider angesagt)";
    	if (skat.getSpielart().getSpielArt() != Spielart.NULLSPIEL) spielBeendetMeldung += spielName + " mit " + PunkteBerechnung.berechnePunkte(alleinspieler.getStichStapel()) + " Punkten.";
    	else spielBeendetMeldung += spielName + ".";
    	int spielwert = PunkteBerechnung.berechneSpielwert(alleinspieler.getHand().getZwoelfKarten(), alleinspieler.getStichStapel(), skat.getSpielart());
    	if (!ergebnis) spielwert *= -2; // doppelter Spielwert wird dem Verlierer abgezogen
    	alleinspieler.aenderePunktestand(spielwert);
    	if (skat.getSpielErgebnisMeldung() != null) spielBeendetMeldung += " " + skat.getSpielErgebnisMeldung();
    	int nochEinSpiel = JOptionPane.showOptionDialog(gui.getRootPane(), spielBeendetMeldung, "Spiel beendet", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Noch ein Spiel", "Beenden"}, null);
    	if (nochEinSpiel == 0) {
    		Position neuePosition = Position.ZWOELF;
    		switch (alleinspieler.getPosition()) {
    			default:
    			case ZWOELF:
    			neuePosition = Position.VIER;
    			break;
    			
    			case VIER:
    			neuePosition = Position.ACHT;
    			break;
    			
    			case ACHT:
    			neuePosition = Position.ZWOELF;
    			break;
    		}
    		Spieler kommtNeuRaus = null;
    		for (int i = 0; i < 3; i++) {
    			if (skat.getSpieler()[i].getPosition() == neuePosition) {
    				kommtNeuRaus = skat.getSpieler()[i];
    				break;
    			}
    		}
    		beginneSkatspiel(null, skat.getSpieler(), kommtNeuRaus);
    	}
    	else {
    		System.exit(0);
    	}
    }
	
}
