package wbkskat;

import java.util.ArrayList;

public class SkatKI {
	
	public ArrayList<SpielOption> spo;	// sollte private sein; DEBUG
	
	public SkatKI() {
		
	}
	
	public void berechneSpielOptionen(Hand h, boolean kommeRaus) {
		this.spo = new ArrayList<SpielOption>();
		
		// Grand?
		spo.add(bewerteGrand(h, kommeRaus));

		// Farbspiel?
		spo.add(bewerteFarbspiel(Kartenfarbe.KARO, h, kommeRaus));
		spo.add(bewerteFarbspiel(Kartenfarbe.HERZ, h, kommeRaus));
		spo.add(bewerteFarbspiel(Kartenfarbe.PIK, h, kommeRaus));
		spo.add(bewerteFarbspiel(Kartenfarbe.KREUZ, h, kommeRaus));
		
		// Nullspiel?
		spo.add(bewerteNullspiel(h, kommeRaus));
	}
	
	private SpielOption bewerteGrand(Hand h, boolean kommeRaus) {
		double grandWahrscheinlichkeit = 0.0;
		double bubenBewertung = 0.0;
		for (Karte k : h.getKarten()) {
			if (k.getWert() == Kartenwert.BUBE) bubenBewertung += 0.1;
		}
		if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.BUBE, Kartenfarbe.KREUZ))) bubenBewertung *= 2.5;
		if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.BUBE, Kartenfarbe.PIK))) bubenBewertung *= 1.8;
		if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.BUBE, Kartenfarbe.HERZ))) bubenBewertung *= 1.2;
		System.out.println("DEBUG: Buben-Bewertung: " + bubenBewertung);
		
		double floetenBewertung = 0.0;
		for (Kartenfarbe farbe : Kartenfarbe.values()) {
			if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.ASS, farbe))) {
				floetenBewertung += 0.1;
				if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.ZEHN, farbe))) {
					floetenBewertung += 0.1;
					if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.KOENIG, farbe))) {
						floetenBewertung += 0.15;
						if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.DAME, farbe))) {
							floetenBewertung += 0.15;
							if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.NEUN, farbe))) {
								floetenBewertung += 0.2;
								if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.ACHT, farbe))) {
									floetenBewertung += 0.2;
									if (enthaeltKarte(h.getKarten(), new Karte(Kartenwert.SIEBEN, farbe))) {
										floetenBewertung += 0.25;
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("DEBUG: Flöten-Bewertung: " + floetenBewertung);
		if (bubenBewertung < 0.25) floetenBewertung = 0.0; // zu wenig Trumpf
		grandWahrscheinlichkeit = bubenBewertung * floetenBewertung;
		Spielart spa = new Spielart(Spielart.GRAND, Kartenfarbe.NULL, -1, false, false, false, false, 0);
		Karte[] hand = new Karte[10];
		for (int i = 0; i < 10; i++) { 
			// Zehn Karten auf der Hand, sonst ist was falsch!
			hand[i] = h.getKarten().get(i);
		}
		int reizwert = PunkteBerechnung.berechneSpielwert(hand, null, spa);
		return new SpielOption(reizwert, grandWahrscheinlichkeit, spa);
	}
	
	private SpielOption bewerteFarbspiel(Kartenfarbe f, Hand h, boolean kommeRaus) {
		double farbenWahrscheinlichkeit = 0.0;
		// TODO: farbenWahrscheinlichkeit soll von Handkarten abhängen
		
		Spielart spa = new Spielart(Spielart.FARBSPIEL, f, -1, false, false, false, false, 0);
		Karte[] hand = new Karte[10];
		for (int i = 0; i < 10; i++) { 
			// Zehn Karten auf der Hand, sonst ist was falsch!
			hand[i] = h.getKarten().get(i);
		}
		int reizwert = PunkteBerechnung.berechneSpielwert(hand, null, spa);
		return new SpielOption(reizwert, farbenWahrscheinlichkeit, spa);
	}
	
	private SpielOption bewerteNullspiel(Hand h, boolean kommeRaus) {
		double nullWahrscheinlichkeit = 1.0;
		
		for (Karte k : h.getKarten()) {
			switch (k.getWert()) {
				case ASS:
				nullWahrscheinlichkeit = 0;
				break;
				case KOENIG:
				nullWahrscheinlichkeit *= 0.2;
				break;
				case DAME:
				nullWahrscheinlichkeit *= 0.4;
				break;
				case BUBE:
				nullWahrscheinlichkeit *= 0.7;
				break;
				case ZEHN:
				nullWahrscheinlichkeit *= 0.9;
				break;
				case NEUN:
				nullWahrscheinlichkeit *= 1.0;
				break;
				case ACHT:
				nullWahrscheinlichkeit *= 1.5;
				break;
				case SIEBEN:
				nullWahrscheinlichkeit *= 2.0;
				break;
			}
		}
		// TODO: betrachte: wie viele Karten fehlen "von unten"? (eine Flöte ist kein Problem...)
		
		Spielart spa = new Spielart(Spielart.NULLSPIEL, Kartenfarbe.NULL, -1, false, false, false, false, 0);
		Karte[] hand = new Karte[10];
		for (int i = 0; i < 10; i++) { 
			// Zehn Karten auf der Hand, sonst ist was falsch!
			hand[i] = h.getKarten().get(i);
		}
		int reizwert = PunkteBerechnung.berechneSpielwert(hand, null, spa);
		return new SpielOption(reizwert, nullWahrscheinlichkeit, spa);
	}
	
	private boolean enthaeltKarte(ArrayList<Karte> karten, Karte k) {
		for (int i = 0; i < karten.size(); i++) {
			if (karten.get(i).getFarbe() == k.getFarbe() && karten.get(i).getWert() == k.getWert()) 
				return true;
		}
		return false;
	}

}

class SpielOption {
	// SpielOption: Reizwert:Confidence:Spielart
	public int reizWert;
	public double spielWahrscheinlichkeit;
	public Spielart spielart;
	
	public SpielOption(int r, double w, Spielart spa) {
		this.reizWert = r;
		this.spielWahrscheinlichkeit = w;
		this.spielart = spa;
	}
	
}
