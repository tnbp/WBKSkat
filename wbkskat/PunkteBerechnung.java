package SKAT;

public class PunkteBerechnung {

	    public static int berechnePunkte(Karte[] stiche) {
	        int punkte = 0;

	        for (int i = 0; i < stiche.length; i++) {
	            int kartenWert = stiche[i].getWert();  // Holen des Kartenwerts mit dem Getter

	         // Berechnung der Punkte basierend auf dem Kartenwert
	            if (kartenWert == 7) {
	                punkte += 11;  // Ass gibt 11 Punkte
	            } else if (kartenWert == 6) {
	                punkte += 10;  // Zehn gibt 10 Punkte
	            } else if (kartenWert == 5) {
	                punkte += 4;   // König gibt 4 Punkte
	            } else if (kartenWert == 4) {
	                punkte += 3;   // Dame gibt 3 Punkte
	            } else if (kartenWert == 8) {
	                punkte += 2;   // Bube gibt 2 Punkte
	            } else if (kartenWert == 3 || kartenWert == 2 || kartenWert == 1) {
	                punkte += 0;   // 9, 8, 7 gibt 0 Punkte
	            }
	        }

	        return punkte;
	    }
	}
