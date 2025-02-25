package src;

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
		    /*Kartenfarbe stichFarbe = k1.getFarbe(); // Erste Karte gibt die Farbe vor

		    boolean k2BedientFarbe = k2.getFarbe() == stichFarbe;
		    boolean k3BedientFarbe = k3.getFarbe() == stichFarbe;
		    //Prüfen ob die folgenden Karten die Farbe bedienen

		    boolean k2IstTrumpf = k2.getFarbe() == this.trumpfFarbe;
		    boolean k3IstTrumpf = k3.getFarbe() == this.trumpfFarbe;
		    boolean k1IstTrumpf = k1.getFarbe() == this.trumpfFarbe;
		    //Prüfen, ob es sich irgendwo um einen Trumpf handelt.
		    
		    Karte besteKarte = k1;

		    if (k2.vergleicheWert(besteKarte, this)<0) {
		        besteKarte = k2;
		    }
		    if (k3.vergleicheWert(besteKarte, this)<0) {
		        besteKarte = k3;
		    }
		 // Falls k2 Trumpf ist und besteKarte nicht, schlägt k2
		    if (k2IstTrumpf && !k1IstTrumpf) {
		        besteKarte = k2;
		    }
		    // Falls k2 dieselbe Farbe wie besteKarte hat und höher ist
		    else if (k2BedientFarbe && k2.istHoeherAls(besteKarte, this.trumpfFarbe)) {
		        besteKarte = k2;
		    }
		    
		    // Falls k3 Trumpf ist und besteKarte kein Trumpf ist
		    if (k3IstTrumpf && !besteKarte.getFarbe().equals(this.trumpfFarbe)) {
		        besteKarte = k3;
		    }
		    // Falls k3 dieselbe Farbe wie besteKarte hat und höher ist
		    else if (k3BedientFarbe && k3.istHoeherAls(besteKarte,this.trumpfFarbe)) {
		        besteKarte = k3;
		    }
		    
		    return besteKarte;*/
		}

		    // Trumpf sticht immer
		   /* if (k3IstTrumpf && (!k2IstTrumpf || k3.istHoeherAls(k2)) && (!k1IstTrumpf || k3.istHoeherAls(k1))) {
		        return k3;
		    } else if (k2IstTrumpf && (!k1IstTrumpf || k2.istHoeherAls(k1))) {
		        return k2;
		    } else if (k2BedientFarbe && k2.istHoeherAls(k1)) {
		        return k2;
		    } else {
		        return k1;
		    }
		}*/

		/*
			public Karte machtDenStich(Karte k1, Karte k2, Karte k3) {
			// Wichtig: k1 ist die zuerst gespielte Karte
			stichFarbe = k1.getFarbe();
			//holen der 1. Farbe der Karte
			
			
			
			return k1; // dringend verändern!*/
	
}
