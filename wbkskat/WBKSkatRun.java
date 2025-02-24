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
	}

}
