package wbkskat;

/*
	Die Karte-Klasse erzeugt eine Karte eines bestimmten Werts
	(Sieben bis Ass) und einer bestimmten Farbe (Karo bis Kreuz).
	Sie enthält außerdem zwei Sortierfunktionen, mit deren Hilfe
	sich die Werte zweier Karten vergleichen lassen.
*/

public class Karte {
	// wert = { 0 = Sieben, 1 = Acht, 2 = Neun, 3 = Bube, ... 7 = Ass }
	private Kartenwert wert;
	// farbe = { 0 = Karo, 1 = Herz, 2 = Pik, 3 = Kreuz }
	private Kartenfarbe farbe;
	private Hand hand;
	
	public Karte(Kartenwert wert, Kartenfarbe farbe) {
		this.wert = wert;
		this.farbe = farbe;
	}
	
	public Kartenfarbe getFarbe() {
		return this.farbe;
	}
	
	public Kartenwert getWert() {
		return this.wert;
	}
	public int getRang() {
		return this.wert.getRang();
	}
	
	public int getNullRang() {
		/*
			Beim Nullspiel sind Zehner unter den Buben eingereiht,
			d.h. die Reihenfolge muss geändert werden.
			Der "Wert" aller Karten oberhalb des Buben (wert = 3) wird
			um 10 erhöht, mit Ausnahme der 10 (wert = 6).
			Die Nullwerte lauten nun 0 = Sieben, 1 = Acht, 2 = Neun, 
			6 = Zehn, 13 = Bube, 14 = Dame, 15 = König, 17 = Ass
		*/
		return this.wert.getNullrang();
	}
	
	public String getFarbeName() {
		// hässlicher Hack, sollte ersetzt werden
		// gibt das Farbsymbol der Karte zurück
		switch (this.farbe) {
			case KARO:
			return "Karo♦";
			
			case HERZ:
			return "Herz♥";
			
			case PIK:
			return "Pik♠";
			
			case KREUZ:
			default:
			return "Kreuz♣";
		}
	}
	
	public String getWertName() {
		// hässlicher Hack, sollte ersetzt werden
		// gibt den Kurznamen der Karte zurück
		switch (this.wert) {
			case SIEBEN:
			return "7";
			
			case ACHT:
			return "8";
			
			case NEUN:
			return "9";
			
			case BUBE:
			return "B";
			
			case DAME:
			return "D";
			
			case KOENIG:
			return "K";
			
			case ZEHN:
			return "10";
			
			case ASS:
			default:
			return "A";
		}
	}
	
	/*
		Funktionen, mit deren Hilfe zwei Karten verglichen und damit geordnet
		werden können; werden von .sort() verwendet.
	*/
	
	public int vergleicheWert(Karte k, Spielart sp) {
		// Bei Nullspielen (spielArt == 2) gilt eine andere Sortierreihenfolge
		if (sp.getSpielArt() == 2) return k.getNullRang() - this.getNullRang();
		return k.getRang() - this.getRang();
	}
	
	public int vergleicheWert(Karte k, Spielart sp, boolean reverse) {
		// überladene Funktion zu oben; umgekehrte Reihenfolge, falls reverse == true
		if (reverse) return -vergleicheWert(k, sp);
		return vergleicheWert(k, sp);
	}
	
	public int vergleicheTruempfe(Karte k, Spielart sp) {
		
		// für den Vergleich zweier Trümpfe gelten andere Regeln
		
		if (this.getWert() == Kartenwert.BUBE && k.getWert() == Kartenwert.BUBE) {
			// beide Karten sind Buben: Kreuz > Pik > Herz > Karo
			return k.getFarbe().getFarbrang() - this.getFarbe().getFarbrang();
		}
		// Buben > alle anderen Trümpfe
		if (this.getWert() == Kartenwert.BUBE) return -1;
		if (k.getWert() == Kartenwert.BUBE) return 1;
		/*
			Für zwei Nicht-Buben-Trümpfe gelten wieder die gleichen Regeln wie für
			Farbkarten: Ass > Zehn > König > Dame > Neun > Acht > Sieben
		*/
		return vergleicheWert(k, sp);
	}
	
	public int vergleicheTruempfe(Karte k, Spielart sp, boolean reverse) {
		// siehe oben; umgekehrte Reihenfolge, falls reverse == true
		if (reverse) return -vergleicheTruempfe(k, sp);
		return vergleicheTruempfe(k, sp);
	}
	
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public Hand getHand() {
		return this.hand;
	}
}
