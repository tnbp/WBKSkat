package wbkskat;

import java.util.ArrayList;

/* 
	Die Hand-Klasse modelliert eine Spieler-Hand, die alle Karten eines Spielers aufnimmt.
	Im Normalfall sind das bis zu 10 Karten, die übersichtlich geordnet sein sollten:
	Trümpfe links, dann nach Farbe geordnet (Kreuz > Pik > Herz > Karo); innerhalb der
	Farben absteigend nach Wert. Test.
	Anders als beim Stapel können Karten auch hinzugefügt werden.
	Nach dem Aufnehmen des Skats enthält die Hand kurzzeitig 12 Karten.
*/

public class Hand {
	// ArrayList, die die Karten-Objekte der Hand enthält
	private ArrayList<Karte> karten;
	
	public Hand() {
		// die Hand enthält zu Beginn noch keine Karten
		karten = new ArrayList<Karte>();
	}
	
	public void fuegeHinzu(Karte k) {
		// füge der Hand das Karten-Objekt k hinzu
		this.karten.add(k);
	}
	
	public boolean legeAb(Karte k) {
		// lege eine Karte aus der Hand ab
		return this.karten.remove(k);
	}
	
	public void printAllCards() {
		// Debug-Funktion: Gib alle Karten der Hand aus
		for (int i = 0; i < karten.size(); i++) {
			System.out.print(karten.get(i).getFarbeName() + karten.get(i).getWertName());
			if (i + 1 < karten.size()) System.out.print(", ");
		}
	}
	
	public void sortiereHand(Spielart sp) {
		/*
			sortiert die Hand abhängig von der aktuellen Spielart:
			Trümpfe links, dann nach Farbe geordnet (Kreuz > Pik > Herz > Karo); 
			innerhalb der Farben absteigend nach Wert.
		*/
		ArrayList<Karte> karo = new ArrayList<Karte>();
		ArrayList<Karte> herz = new ArrayList<Karte>();
		ArrayList<Karte> pik = new ArrayList<Karte>();
		ArrayList<Karte> kreuz = new ArrayList<Karte>();
		ArrayList<Karte> trumpf = new ArrayList<Karte>();
		for (int i = 0; i < this.karten.size(); i++) {
			if (sp.istTrumpf(this.karten.get(i))) {
				// Trümpfe kommen in den Trumpfstapel und sonst nirgends hin
				trumpf.add(this.karten.get(i));
				continue;
			}
			// kein Trumpf; ordne in einen Farbstapel ein
			switch (this.karten.get(i).getFarbe()) {
				case KARO:
				karo.add(this.karten.get(i));
				break;

				case HERZ:
				herz.add(this.karten.get(i));
				break;

				case PIK:
				pik.add(this.karten.get(i));
				break;
				
				case KREUZ:
				kreuz.add(this.karten.get(i));
				break;
			}
		}
		// sortiere alle "Unterstapel" je nach Spielart
		trumpf.sort((o1, o2) -> o1.vergleicheTruempfe(o2, sp));
		kreuz.sort((o1, o2) -> o1.vergleicheWert(o2, sp));
		pik.sort((o1, o2) -> o1.vergleicheWert(o2, sp));
		herz.sort((o1, o2) -> o1.vergleicheWert(o2, sp));
		karo.sort((o1, o2) -> o1.vergleicheWert(o2, sp));
		// füge die Unterstapel wieder zur Hand zusammen
		this.karten = trumpf;
		this.karten.addAll(kreuz);
		this.karten.addAll(pik);
		this.karten.addAll(herz);
		this.karten.addAll(karo);
	}
}
