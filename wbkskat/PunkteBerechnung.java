package wbkskat;

public class PunkteBerechnung {

    public static int berechnePunkte(Karte[] stiche) {
        int punkte = 0;

        // Schleife, die jede Karte im Stich durchgeht
        for (int i = 0; i < stiche.length; i++) {
        	if (stiche[i] == null) break;
            Kartenwert karteWert = stiche[i].getWert();  // Holt den Kartenwert der aktuellen Karte
            punkte += getPunkteWert(karteWert);  // Punkte basierend auf dem Kartenwert hinzufügen
        }

        return punkte;  // Gesamtpunktzahl zurückgeben
    }
    
    public static boolean bestimmeGewinn(Skatspiel sp) {
    	// true: Alleinspieler hat gewonnen; false: Alleinspieler hat verloren
    	Spieler alleinspieler = null;
    	for (int i = 0; i < 3; i++) {
    		if (sp.getSpieler()[i].istAlleinspieler()) {
    			alleinspieler = sp.getSpieler()[i];
    			break;
    		}
    	}
    	int spielwert = berechneSpielwert(alleinspieler.getHand().getZwoelfKarten(), alleinspieler.getStichStapel(), sp.getSpielart());
    	if (sp.getSpielart().getGereiztBis() > spielwert) {
    		sp.setSpielErgebnisMeldung(alleinspieler.getName() 
    				+ " hat ÜBERREIZT!\n(gereizt bis " + sp.getSpielart().getGereiztBis() + ")");
            return false;
    	}
    	if (sp.getSpielart().getSpielArt() == Spielart.NULLSPIEL) return alleinspieler.getStichStapel()[2] == null;
    	if (sp.getSpielart().getSchwarzAngesagt()) return alleinspieler.getStichStapel()[31] != null;
    	if (sp.getSpielart().getSchneiderAngesagt()) return berechnePunkte(alleinspieler.getStichStapel()) >= 90;
    	return berechnePunkte(alleinspieler.getStichStapel()) > 60;
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
    
    private static int berechneSpitzenfaktor(Karte[] zwoelfKarten, Kartenfarbe trumpfFarbe) {
    	int spf = 1;
    	int ohne = 0;
    	int mit = 0;
    	boolean gefunden = false;
    	// Buben: MIT
    	while (true) {
    		for (int i = 0; i < zwoelfKarten.length; i++) {
    			if (zwoelfKarten[i].getWert() == Kartenwert.BUBE 
    					&& zwoelfKarten[i].getFarbe() == Kartenfarbe.values()[3 - mit]) {
    				mit++;
    				gefunden = true;
    				break;
    			}
    		}
    		if (!gefunden || mit >= 4) break;
    		gefunden = false;
    	}
    	// Buben: OHNE
    	while (true) {
    		for (int i = 0; i < zwoelfKarten.length; i++) {
    			if (zwoelfKarten[i].getWert() == Kartenwert.BUBE 
    					&& zwoelfKarten[i].getFarbe() == Kartenfarbe.values()[3 - ohne]) {
    				gefunden = true;
    				break;
    			}
    		}
    		if (gefunden || ohne >= 4) break;
    		ohne++;
    	}
		Kartenwert[] reihenfolge = {
				Kartenwert.ASS, Kartenwert.ZEHN, Kartenwert.KOENIG, Kartenwert.DAME,
				Kartenwert.NEUN, Kartenwert.ACHT, Kartenwert.SIEBEN
		};
		gefunden = false;
    	// Trümpfe: MIT
    	if (mit == 4) {
    		while (true) {
    			if (trumpfFarbe == Kartenfarbe.NULL) break;
    			for (int i = 0; i < zwoelfKarten.length; i++) {
        			if (zwoelfKarten[i].getFarbe() == trumpfFarbe 
        					&& zwoelfKarten[i].getWert() == reihenfolge[mit - 4]) {
        				mit++;
        				gefunden = true;
        				break;
        			}
        		}
        		if (!gefunden || mit >= 11) break;
        		gefunden = false;
    		}
    	}
    	else if (ohne == 4) {
    		while (true) {
    			if (trumpfFarbe == Kartenfarbe.NULL) break;
    			for (int i = 0; i < zwoelfKarten.length; i++) {
        			if (zwoelfKarten[i].getFarbe() == trumpfFarbe 
        					&& zwoelfKarten[i].getWert() == reihenfolge[ohne - 4]) {
        				gefunden = true;
        				break;
        			}
        		}
        		if (gefunden || ohne >= 11) break;
        		ohne++;
    		}
    	}
    	System.out.println("Mit: " + mit + "; Ohne: " + ohne);
    	spf += Math.max(mit, ohne);
    	return spf;
    }
    
    public static int berechneSpielwert(Karte[] zwoelfKarten, Karte[] stichStapel, Spielart sp) {
    	int wert;
    	if (sp.getSpielArt() == Spielart.NULLSPIEL) {
    		if (sp.getOuvert() && sp.getHandspiel()) wert = 59;
    		else if (sp.getOuvert()) wert = 46;
    		else if (sp.getHandspiel()) wert = 35;
    		else wert = 23;
        	if (sp.getKontra() > 0) {
        		int kontra = sp.getKontra();
        		while (kontra-- > 0) wert *= 2;
        	}
        	return wert;
    	}
    	else if (sp.getSpielArt() == Spielart.GRAND) wert = 24;
    	else switch (sp.getTrumpfFarbe()) {
    		case KARO:
    		default:
    		wert = 9;
    		break;
    		
    		case HERZ:
    		wert = 10;
    		break;
    		
    		case PIK:
    		wert = 11;
    		break;
    		
    		case KREUZ:
    		wert = 12;
    		break;
    	}
    	int mul = berechneSpitzenfaktor(zwoelfKarten, sp.getTrumpfFarbe());
    	if (sp.getHandspiel()) mul++;
    	if (sp.getSchneiderAngesagt()) mul++;
    	if (sp.getSchwarzAngesagt()) mul++;
    	if (sp.getOuvert()) mul++;
    	if (sp.getKontra() > 0) {
    		int kontra = sp.getKontra();
    		while (kontra-- > 0) wert *= 2;
    	}
    	if (stichStapel[2] == null) mul++; // Alleinspieler hat keinen Stich gemacht!! (Schwarz)
    	else if (stichStapel[31] != null) mul++; // Alleinspieler hat alle Stiche gemacht!! (Schwarz)
    	if (berechnePunkte(stichStapel) <= 30 || berechnePunkte(stichStapel) >= 90) mul++; // Schneider
    	return mul * wert;
    }
}
