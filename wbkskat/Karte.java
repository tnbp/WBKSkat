package wbkskat;

/*
	Die Karte-Klasse erzeugt eine Karte eines bestimmten Werts
	(Sieben bis Ass) und einer bestimmten Farbe (Karo bis Kreuz).
	Sie enthält außerdem zwei Sortierfunktionen, mit deren Hilfe
	sich die Werte zweier Karten vergleichen lassen.
*/

public class Karte {
	// wert = { 0 = Sieben, 1 = Acht, 2 = Neun, 3 = Bube, ... 7 = Ass }
	private int wert;
	// farbe = { 0 = Karo, 1 = Herz, 2 = Pik, 3 = Kreuz }
	private int farbe;
	
	public Karte(int wert, int farbe) {
		this.wert = wert;
		this.farbe = farbe;
	}
	
	public int getFarbe() {
		return this.farbe;
	}
	
	public int getWert() {
		return this.wert;
	}
	
	public int getNullWert() {
		/*
			Beim Nullspiel sind Zehner unter den Buben eingereiht,
			d.h. die Reihenfolge muss geändert werden.
			Der "Wert" aller Karten oberhalb des Buben (wert = 3) wird
			um 10 erhöht, mit Ausnahme der 10 (wert = 6).
			Die Nullwerte lauten nun 0 = Sieben, 1 = Acht, 2 = Neun, 
			6 = Zehn, 13 = Bube, 14 = Dame, 15 = König, 17 = Ass
		*/
		int nullwert = this.getWert();
		// hässlicher Hack, sollte ersetzt werden
		if (nullwert >= 3 && nullwert != 6) nullwert += 10;
		return nullwert;
	}
	
	public String getFarbeName() {
		// hässlicher Hack, sollte ersetzt werden
		// gibt das Farbsymbol der Karte zurück
		switch (this.farbe) {
			case 0:
			return "♦";
			
			case 1:
			return "♥";
			
			case 2:
			return "♠";
			
			case 3:
			default:
			return "♣";
		}
	}
	
	public String getWertName() {
		// hässlicher Hack, sollte ersetzt werden
		// gibt den Kurznamen der Karte zurück
		switch (this.wert) {
			case 0:
			return "7";
			
			case 1:
			return "8";
			
			case 2:
			return "9";
			
			case 3:
			return "B";
			
			case 4:
			return "D";
			
			case 5:
			return "K";
			
			case 6:
			return "10";
			
			case 7:
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
		if (sp.getSpielArt() == 2) return k.getNullWert() - this.getNullWert();
		return k.getWert() - this.getWert();
	}
	
	public int vergleicheWert(Karte k, Spielart sp, boolean reverse) {
		// überladene Funktion zu oben; umgekehrte Reihenfolge, falls reverse == true
		if (reverse) return -vergleicheWert(k, sp);
		return vergleicheWert(k, sp);
	}
	
	public int vergleicheTruempfe(Karte k, Spielart sp) {
		
		// für den Vergleich zweier Trümpfe gelten andere Regeln
		
		if (this.getWert() == 3 && k.getWert() == 3) {
			// beide Karten sind Buben: Kreuz > Pik > Herz > Karo
			return k.getFarbe() - this.getFarbe();
		}
		// Buben > alle anderen Trümpfe
		if (this.getWert() == 3) return -1;
		if (k.getWert() == 3) return 1;
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
}
