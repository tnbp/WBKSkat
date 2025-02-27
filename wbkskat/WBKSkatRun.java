package wbkskat;

import javax.swing.SwingUtilities;

public class WBKSkatRun {

/*
	Driver-Klasse für das WBKSkat-Spiel.
*/

    public static void main(String[] args) {
    	Spieler S1 = new Spieler("Luffy", Position.ZWOELF);
    	Spieler S2 = new Spieler("Zorro", Position.VIER);
    	Spieler S3 = new Spieler("Buggy", Position.ACHT);
    	Spielart SP = new Spielart(Kartenfarbe.PIK);
    	Skatspiel skat = new Skatspiel(SP, new Spieler[] {S1,S2,S3}, S1);
    	SkatController sc = new SkatController();
    	sc.setSkatSpiel(skat);
        SwingUtilities.invokeLater(() -> new SkatGUI(skat, sc)); // Voodoo™
        /*sr = new Stichrunde(Skat);
        sr.spieleKarte(sr.amZug().getHand().getKarten().get(0), sr.amZug());*/
    }

}
