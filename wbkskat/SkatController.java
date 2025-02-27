package wbkskat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SkatController {
    
	private Skatspiel skat;
	private SkatGUI gui;
	
	public SkatController() {
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
	    		gui.updateUI(this.skat);
	    		if (skat.getStichrunde().getKartenImStich()[2] != null) {
	    			// Stichrunde zu Ende!
	    			skat.sperreEingabe();
	    			Karte[] stichkarten = skat.getStichrunde().getKartenImStich();
	    			Spieler gewinner = skat.getStichrunde().hatKarteGespielt(skat.getSpielart().machtDenStich(stichkarten[0], 
	    					stichkarten[1], stichkarten[2]));
	    			gewinner.nimmStichZuDir(stichkarten);
	    			System.out.println("Der Stich ist " + PunkteBerechnung.berechnePunkte(stichkarten) + " Augen wert und geht an " + gewinner.getName());
	    			Stichrunde s = new Stichrunde(skat, gewinner);
	    			Timer t = new Timer(3000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("AAAA!");
							gui.updateUI(skat);
							skat.entsperreEingabe();
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
