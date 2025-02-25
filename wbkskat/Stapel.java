package wbkskat;

import java.util.ArrayList;

/* 
	Die Stapel-Klasse erzeugt im Konstruktor ein vollständiges
	Spiel aus 32 Karten (je 7 bis Ass in Karo, Herz, Pik und Kreuz).
	Die zieheKarte()-Methode liefert eine zufällige Karte aus dem
	Stapel und entfernt sie damit aus dem Stapel.
	Aus den Stapelkarten werden zu Beginn jeder Runde die Handkarten
	und der Skat verteilt. Anschließend wird der Stapel nicht mehr
	benötigt.
*/

public class Stapel {
	private ArrayList<Karte> karten;
	
	public Stapel() {
		karten = new ArrayList<Karte>();
		for (Kartenfarbe i : Kartenfarbe.values()) {
			if (i == Kartenfarbe.NULL) continue;
			for (Kartenwert j : Kartenwert.values()) {
				karten.add(new Karte(j, i));
			}
		}
	}
	
	public Karte zieheKarte() {
		int i = (int) (Math.random() * (karten.size()));
		//System.out.println("** DEBUG: karten.size() = " + karten.size() + ", i = " + i);
		Karte k = this.karten.get(i);
		this.karten.remove(i);
		return k;
	}
}
