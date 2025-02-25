package wbkskat;

import java.util.ArrayList;

public class Spielart {
	private int spielArt;
	// spielArt: { 0 = Farbspiel, 1 = Grand, 2 = Null }
	private Kartenfarbe trumpfFarbe;
	// trumpfFarbe: { 0 = Karo, 1 = Herz, 2 = Pik, 3 = Kreuz }
	
	public Spielart(int spielart, Kartenfarbe trumpf) {
		this.spielArt = spielart;
		this.trumpfFarbe = trumpf;
	}
	
	public int getSpielArt() {
		return this.spielArt;
	}
	
	public Kartenfarbe getTrumpfFarbe() {
		return this.trumpfFarbe;
	}
	
	public boolean istTrumpf(Karte k) {
		// Das Nullspiel hat keine Trümpfe.
		if (this.spielArt == 2) return false;
		// Buben sind immer Trumpf.
		if (k.getWert() == Kartenwert.BUBE) return true;
		// Bei Grand wird trumpfFarbe auf -1 gesetzt.
		// Die nächste Zeile gibt dann immer false zurück.
		return (k.getFarbe() == this.trumpfFarbe);
	}
	
	
	public Karte machtDenStich(Karte k1, Karte k2, Karte k3) {
		ArrayList<Karte> truempfe = new ArrayList<Karte>();
		Karte gewinner;
		if (istTrumpf(k1)) truempfe.add(k1);
		if (istTrumpf(k2)) truempfe.add(k2);
		if (istTrumpf(k3)) truempfe.add(k3);
		if (truempfe.size() > 0) {
			// der höchste Trumpf gewinnt
			gewinner = truempfe.get(0);
			for (int i = 1; i < truempfe.size(); i++) {
				if (gewinner.vergleicheTruempfe(truempfe.get(i), this) > 0) gewinner = truempfe.get(i);
			}
			return gewinner;
		}
		gewinner = k1;
		if (k2.getFarbe() == k1.getFarbe()) {
			// übersticht k2?
			if (gewinner.vergleicheWert(k2, this) > 0) gewinner = k2;
		}
		if (k2.getFarbe() == k1.getFarbe()) {
			// übersticht k3?
			if (gewinner.vergleicheWert(k3, this) > 0) gewinner = k3;
		}
		return gewinner;
	}
	
}
