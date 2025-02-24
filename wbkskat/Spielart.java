package wbkskat;

/* 
	Die Spielart-Klasse enthält die Informationen darüber, welche
	Spielart gespielt wird: Farbspiel, Grand oder Null; bei einem
	Farbspiel zusätzlich, welche Farbe angesagt ist.
	Des Weiteren enthält sie eine Methode, der eine Karte übergeben
	werden kann und die überprüft, ob eine Karte eine Trumpfkarte ist
	(istTrumpf()) und eine Methode, die aus drei Karten im Stich
	diejenige zurückgibt, welche den Stich macht.
*/

public class Spielart {
	private int spielArt;
	// spielArt: { 0 = Farbspiel, 1 = Grand, 2 = Null }
	private int trumpfFarbe;
	// trumpfFarbe: { 0 = Karo, 1 = Herz, 2 = Pik, 3 = Kreuz }
	
	public Spielart(int spielart, int trumpf) {
		this.spielArt = spielart;
		this.trumpfFarbe = trumpf;
	}
	
	public int getSpielArt() {
		return this.spielArt;
	}
	
	public int getTrumpfFarbe() {
		return this.trumpfFarbe;
	}
	
	public boolean istTrumpf(Karte k) {
		// Das Nullspiel hat keine Trümpfe.
		if (this.spielArt == 2) return false;
		// Buben sind immer Trumpf.
		if (k.getWert() == 3) return true;
		/* 
			Bei Grand wird trumpfFarbe auf -1 gesetzt.
			Die nächste Zeile gibt dann immer false zurück.
			Ansonsten wird nur dann true zurückgegeben, wenn die
			Karte der aktuellen Trumpffarbe angehört.
		*/
		return (k.getFarbe() == this.trumpfFarbe);
	}
	
	public Karte machtDenStich(Karte k1, Karte k2, Karte k3) {
		/* 
			Wichtig: k1 ist die zuerst gespielte Karte und bestimmt,
			in welcher Farbe überstochen werden muss.
		*/
		return k1; // TODO: dringend verändern!
	}
}
