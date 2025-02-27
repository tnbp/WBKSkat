package wbkskat;

import javax.swing.JOptionPane;

public class Skatspiel {
	
	private Spieler[] spieler;
	private Spieler kommtRaus;
	private Spielart spielart;
	private Karte[] skat;
	private Stichrunde stichRunde = null;
	private boolean eingabeGesperrt = false;
	private SkatController sc;
	
	public Skatspiel(Spielart sp, Spieler[] spieler, Spieler kommtRaus) {
		this.spielart = sp;
		this.spieler = spieler;
		this.kommtRaus = kommtRaus;
		Stapel stapel = new Stapel();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				this.spieler[i].getHand().fuegeHinzu(stapel.zieheKarte());
			}
		}
		this.skat = new Karte[2];
		this.skat[0] = stapel.zieheKarte();
		this.skat[1] = stapel.zieheKarte();
	}
	
	public Spieler[] getSpieler() {
		return this.spieler;
	}
	
	public Spieler werKommtRaus() {
		return this.kommtRaus;
	}
	
	public void setKommtRaus(Spieler sp) {
		this.kommtRaus = sp;
	}
	
	public Spieler naechsterSpieler(Spieler vorigerSpieler) {
		Position naechstePosition;
		switch (vorigerSpieler.getPosition()) {
			case ZWOELF:
			naechstePosition = Position.VIER;
			break;
			
			case VIER:
			naechstePosition = Position.ACHT;
			break;
			
			case ACHT:
			default:
			naechstePosition = Position.ZWOELF;
			break;
		}
		for (int i = 0; i < spieler.length; i++) {
			if (spieler[i].getPosition() == naechstePosition) return spieler[i];
		}
		return null; // sollte nie passieren!!
	}
	
	public Spielart getSpielart() {
		return this.spielart;
	}
	
	public Karte[] getSkat() {
		return this.skat;
	}
	
	public void setStichrunde(Stichrunde sr) {
		this.stichRunde = sr;
	}
	
	public Stichrunde getStichrunde() {
		return this.stichRunde;
	}
	
	public boolean eingabeGesperrt() {
		return this.eingabeGesperrt;
	}
	
	public void sperreEingabe() {
		this.eingabeGesperrt = true;
	}
	
	public void entsperreEingabe() {
		this.eingabeGesperrt = false;
	}
	
	public void setupSpielart() {
		// wir fangen von null an -- frage alles ab!
		String[] spielartOptionen = { "Farbspiel", "Grand", "Null" };
        int spielartWahl = -1;
        while (spielartWahl < 0) {
        	spielartWahl = JOptionPane.showOptionDialog(null, "Was willst du spielen?", "Spielart wählen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, spielartOptionen, null);
        }
        String wieHeisstEr = null;
        int spruchWahl = 0;
    	int farbspielWahl = -1;
    	String[] farbspielOptionen = { "Karo♦", "Herz♥", "Pik♠", "Kreuz♣" };
        switch (spielartWahl) {
        	case 0:
        	while (farbspielWahl < 0) {
        		farbspielWahl = JOptionPane.showOptionDialog(null, "Welche Farbe soll Trumpf sein?", "Farbe wählen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, farbspielOptionen, null);
        	}
        	wieHeisstEr = farbspielOptionen[farbspielWahl] + " ist angesagt!";
        	spruchWahl = farbspielWahl;
        	break;
        	
        	case 1:
        	spruchWahl = 4;
        	break;
        	
        	case 2:
        	spruchWahl = ((Math.random() * 2) > 1) ? 5 : 6;
        	break;
        }
        Kartenfarbe farbspiel;
        if (farbspielWahl < 0) farbspiel = Kartenfarbe.NULL;
        else farbspiel = Kartenfarbe.values()[farbspielWahl];
        String[] skatSprueche = {
        		"Wer nicht weiß, wie und wo, der spielt Karo!",
        		"Wer kein Herz hat, ist ein Lump!",
        		"Grünes Gras frisst der Haas!",
        		"Eine Kreuz hat jeder!",
        		"Beim Grand spielt man Asse, oder man soll's lasse!",
        		"7, 9, und Unter, da geht keiner drunter!",
        		"Ein Null gibt immer Contra!"
        };
        JOptionPane.showMessageDialog(null, skatSprueche[spruchWahl], wieHeisstEr, JOptionPane.WARNING_MESSAGE);
        // TODO: Hand, Ouvert, Schneider etc.
        this.spieler[0].setIstAlleinspieler(true);
        this.spielart = new Spielart(spielartWahl, farbspiel);
        // sortiere Spieler-Hände
        String alleinspieler = null;
        for (int i = 0; i < 3; i++) {
        	this.spieler[i].getHand().sortiereHand(this.spielart);
        	if (this.spieler[i].istAlleinspieler()) alleinspieler = this.spieler[i].getName();
        }
        String spielBezeichnung = alleinspieler + " spielt ";
        if (spielartWahl == 0) spielBezeichnung += farbspielOptionen[farbspielWahl];
        else spielBezeichnung += spielartOptionen[spielartWahl];
        this.sc.getSkatGUI().setTitle("Skat-Spiel: " + spielBezeichnung);
        this.sc.getSkatGUI().updateUI();
	}
	
	public void setSkatController(SkatController sc) {
		this.sc = sc;
	}

}
