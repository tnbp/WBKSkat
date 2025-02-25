package wbkskat;

public class test {
	
	public static void main(String[] args) {
		Spieler sp1 = new Spieler("Horst", Position.ZWOELF);
		sp1.setIstAlleinspieler(true);
		Spieler sp2 = new Spieler("Egon", Position.VIER);
		Spieler sp3 = new Spieler("Marga", Position.ACHT);
		Spielart spa = new Spielart(Kartenfarbe.KARO); 
		Skatspiel sksp = new Skatspiel(spa, new Spieler[]{ sp1, sp2, sp3 }, sp1);
		sksp.getSpieler()[0].getHand().sortiereHand(spa);
		sksp.getSpieler()[1].getHand().sortiereHand(spa);
		sksp.getSpieler()[2].getHand().sortiereHand(spa);
		System.out.println(sp1.getName() + "s Hand: ");
		for (int i = 0; i < 10; i++) {
			Karte k = sp1.getHand().getKarten().get(i);
			System.out.print(k.getFarbeName() + k.getWertName() + " ");
		}
		System.out.println("\n");
		System.out.println(sp2.getName() + "s Hand: ");
		for (int i = 0; i < 10; i++) {
			Karte k = sp2.getHand().getKarten().get(i);
			System.out.print(k.getFarbeName() + k.getWertName() + " ");
		}
		System.out.println("\n");
		System.out.println(sp3.getName() + "s Hand: ");
		for (int i = 0; i < 10; i++) {
			Karte k = sp3.getHand().getKarten().get(i);
			System.out.print(k.getFarbeName() + k.getWertName() + " ");
		}
		System.out.println("\n");
		System.out.println("Der Skat:");
		System.out.print(sksp.getSkat()[0].getFarbeName() + 
				sksp.getSkat()[0].getWertName() + " ");
		System.out.print(sksp.getSkat()[1].getFarbeName() + 
				sksp.getSkat()[1].getWertName() + " ");
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println(sp1.getName() + " nimmt den Skat auf und legt zufällig"
				+ " zwei Karten ab.");
		sp1.getHand().fuegeHinzu(sksp.getSkat()[0]);
		sp1.getHand().fuegeHinzu(sksp.getSkat()[1]);
		Karte k1 = sp1.getHand().getKarten().get((int)(Math.random() * 12));
		sp1.getHand().legeAb(k1);
		Karte k2 = sp1.getHand().getKarten().get((int)(Math.random() * 11));
		sp1.getHand().legeAb(k2);
		sp1.drueckeSkat(k1, k2);
		sp1.getHand().sortiereHand(spa);
		System.out.println(sp1.getName() + "s Hand: ");
		for (int i = 0; i < 10; i++) {
			Karte k = sp1.getHand().getKarten().get(i);
			System.out.print(k.getFarbeName() + k.getWertName() + " ");
		}
		System.out.println();
		System.out.println(sp1.getName() + "s Stichstapel:");
		System.out.print(sp1.getStichStapel()[0].getFarbeName() + sp1.getStichStapel()[0].getWertName() + " ");
		System.out.print(sp1.getStichStapel()[1].getFarbeName() + sp1.getStichStapel()[1].getWertName());
		for (int j = 0; j < 10; j++) {
			System.out.println("\n------------------------------------------------------------------------");
			Stichrunde sr = new Stichrunde(sksp);
			boolean next = false;
			Spieler amZug = sksp.werKommtRaus();
			Karte gespielt;
			Karte[] stich = new Karte[3];
			for (int i = 0; i < 3; i++) {
				do {
					gespielt = amZug.getHand().getKarten().get((int)(Math.random() * amZug.getHand().getKarten().size()));
					next = sr.spieleKarte(gespielt, amZug);
				} while (!next);
				System.out.println(amZug.getName() + " spielt " + gespielt.getFarbeName() + gespielt.getWertName());
				stich[i] = gespielt;
				amZug.setLetzteKarte(gespielt);
				amZug = sr.amZug();
				next = false;
			}
			Karte machtDenStich = spa.machtDenStich(stich[0], stich[1], stich[2]);
			Spieler gewinner;
			if (machtDenStich == sp1.getLetzteKarte()) gewinner = sp1;
			else if (machtDenStich == sp2.getLetzteKarte()) gewinner = sp2;
			else gewinner = sp3;
			System.out.println("Den Stich macht " + machtDenStich.getFarbeName() + machtDenStich.getWertName() 
			+ ", gespielt von " + gewinner.getName());
			gewinner.nimmStichZuDir(stich);
			sksp.setKommtRaus(gewinner);
		}
		Spieler allein = null;
		for (int i = 0; i < 3; i++) {
			if (sksp.getSpieler()[i].istAlleinspieler()) allein = sksp.getSpieler()[i];
		}
		System.out.println(allein.getName() 
				+ (PunkteBerechnung.bestimmeGewinn(allein.getStichStapel(), spa, 0) ? " gewinnt " : " verliert ")
				+ "das Spiel mit " + PunkteBerechnung.berechnePunkte(allein.getStichStapel()) + " Punkten.");
	}

}
