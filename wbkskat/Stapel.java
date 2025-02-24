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
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				karten.add(new Karte(j, i));
			}
		}
	}
	
	public Karte zieheKarte() {
		int i = (int) (Math.random() * (karten.size()));
		Karte k = this.karten.get(i);
		this.karten.remove(i);
		return k;
	}
}
