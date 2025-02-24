package wbkskat;

public class WBKSkatRun {

/*
	Driver-Klasse für das WBKSkat-Spiel.
*/

	public static void main(String[] args) {
		Stapel stapel = new Stapel();
		Hand[] haende = new Hand[3];
		// Stapel-Karten auf 3 Hände à 10 Karten verteilen
		for (int i = 0; i < 3; i++) {
			haende[i] = new Hand();
			for (int j = 0; j < 10; j++) {
				haende[i].fuegeHinzu(stapel.zieheKarte());
			}
		}
		// Karten der ersten Hand ausgeben: Ungeordnet...
		System.out.println("Erste Hand: ");
		haende[0].printAllCards();
		// ... geordnet nach Spielart sp1: Farbspiel Pik
		Spielart sp1 = new Spielart(0, 2);
		haende[0].sortiereHand(sp1);
		System.out.println("\nErste Hand, sortiert (Pik): ");
		haende[0].printAllCards();
		// ... geordnet nach Spielart sp2: Grand
		Spielart sp2 = new Spielart(1, -1);
		haende[0].sortiereHand(sp2);
		System.out.println("\nErste Hand, sortiert (Grand): ");
		haende[0].printAllCards();
		// ... geordnet nach Spielart sp3: Nullspiel
		Spielart sp3 = new Spielart(2, 0);
		haende[0].sortiereHand(sp3);
		System.out.println("\nErste Hand, sortiert (Null): ");
		haende[0].printAllCards();
		System.out.println();
		System.out.println();
		System.out.println();
		Karte k1 = new Karte(0, 2); // Pik Sieben
		Karte k2 = new Karte(3, 2); // Pik Bube
		Karte k3 = new Karte(7, 2); // Pik Ass
		Karte k4 = new Karte(4, 1); // Herz Dame
		Karte s1 = sp1.machtDenStich(k1, k2, k3); // erwartet: Pik Bube
		System.out.println("Stich 1: " + s1.getFarbeName() + s1.getWertName());
		Karte s2 = sp1.machtDenStich(k2, k3, k4); // erwartet: Pik Bube
		System.out.println("Stich 2: " + s2.getFarbeName() + s2.getWertName());
		Karte s3 = sp2.machtDenStich(k4, k1, k3); // erwartet: Herz Dame
		System.out.println("Stich 3: " + s3.getFarbeName() + s3.getWertName());
		Karte s4 = sp3.machtDenStich(k1, k2, k3); // erwartet: Pik Ass
		System.out.println("Stich 4: " + s4.getFarbeName() + s4.getWertName());
	}

}
