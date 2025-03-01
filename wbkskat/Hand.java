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
	private Karte[] alleKarten;
	private Spieler besitzer;
	
	public Hand(Spieler besitzer) {
		// die Hand enthält zu Beginn noch keine Karten
		this.karten = new ArrayList<Karte>();
		this.alleKarten = new Karte[12];
		this.besitzer = besitzer;
	}
	
	public ArrayList<Karte> getKarten() {
		return this.karten;
	}
	
	public void fuegeHinzu(Karte k) {
		// füge der Hand das Karten-Objekt k hinzu
		k.setHand(this);
		this.karten.add(k);
		for (int i = 0; i < alleKarten.length; i++) {
			if (alleKarten[i] == null) alleKarten[i] = k;
		}
	}
	
	public boolean legeAb(Karte k) {
		// lege eine Karte aus der Hand ab
		k.setHand(null);
		return this.karten.remove(k);
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
	
	public Spieler getBesitzer() {
		return this.besitzer;
	}
	
	public Karte[] getZwoelfKarten() {
		return this.alleKarten;
	}
}