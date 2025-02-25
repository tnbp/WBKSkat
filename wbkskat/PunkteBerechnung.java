package wbkskat;

public class PunkteBerechnung {

    public static int berechnePunkte(Karte[] stiche) {
        int punkte = 0;

        // Schleife, die jede Karte im Stich durchgeht
        for (int i = 0; i < stiche.length; i++) {
            Kartenwert karteWert = stiche[i].getWert();  // Holt den Kartenwert der aktuellen Karte
            punkte += getPunkteWert(karteWert);  // Punkte basierend auf dem Kartenwert hinzufügen
        }

        return punkte;  // Gesamtpunktzahl zurückgeben
    }

    private static int getPunkteWert(Kartenwert wert) {
        // Punkte werden nun direkt anhand von if-else-Bedingungen festgelegt
        if (wert == Kartenwert.ASS) {
            return 11;  // Ass gibt 11 Punkte
        } else if (wert == Kartenwert.ZEHN) {
            return 10;  // Zehn gibt 10 Punkte
        } else if (wert == Kartenwert.KOENIG) {
            return 4;   // König gibt 4 Punkte
        } else if (wert == Kartenwert.DAME) {
            return 3;   // Dame gibt 3 Punkte
        } else if (wert == Kartenwert.BUBE) {
            return 2;   // Bube gibt 2 Punkte
        } else {
            return 0;   // Sieben, Acht und Neun geben 0 Punkte
        }
    }
}
