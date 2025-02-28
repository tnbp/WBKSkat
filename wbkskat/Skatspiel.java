package wbkskat;

import javax.swing.JOptionPane;

public class Skatspiel {
	
	private Spieler[] spieler;
	private Spieler kommtRaus;
	private Spielart spielart;
	private Karte[] skat;
	private Stichrunde stichRunde = null;
	private boolean eingabeGesperrt = false;
	private boolean skatDruecken = false;
	private int skatKartenGedrueckt = 0;
	private SkatController sc;
	private String spielErgebnisMeldung;
	
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
		// erst sortieren...
		for (int i = 0; i < 3; i++) {
        	this.spieler[i].getHand().sortiereHand(new Spielart(Kartenfarbe.KREUZ));
        }
		int gereiztBis = starteReizen();
		if (gereiztBis == 0) JOptionPane.showMessageDialog(sc.getSkatGUI().getRootPane(), "Pech! Du musst trotzdem spielen.", "KI not found", JOptionPane.WARNING_MESSAGE);
		else JOptionPane.showMessageDialog(sc.getSkatGUI().getRootPane(), "Bis " + gereiztBis + " gereizt.", "Reizen gewonnen", JOptionPane.WARNING_MESSAGE);
		String[] spielartOptionen = { "Farbspiel", "Grand", "Null" };
        int spielartWahl = -1;
        while (spielartWahl < 0) {
        	spielartWahl = JOptionPane.showOptionDialog(sc.getSkatGUI().getRootPane(), "Was willst du spielen?", "Spielart wählen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, spielartOptionen, null);
        }
        String wieHeisstEr = null;
        int spruchWahl = 0;
    	int farbspielWahl = -1;
    	String[] farbspielOptionen = { "Karo♦", "Herz♥", "Pik♠", "Kreuz♣" };
        switch (spielartWahl) {
        	case 0:
        	while (farbspielWahl < 0) {
        		farbspielWahl = JOptionPane.showOptionDialog(sc.getSkatGUI().getRootPane(), "Welche Farbe soll Trumpf sein?", "Farbe wählen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, farbspielOptionen, null);
        	}
        	wieHeisstEr = farbspielOptionen[farbspielWahl] + " ist angesagt!";
        	spruchWahl = farbspielWahl;
        	break;
        	
        	case 1:
        	wieHeisstEr = spielartOptionen[spielartWahl] + " ist angesagt!";
        	spruchWahl = 4;
        	break;
        	
        	case 2:
            wieHeisstEr = spielartOptionen[spielartWahl] + " ist angesagt!";
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
        		"Eine Null gibt immer Contra!"
        };
        JOptionPane.showMessageDialog(sc.getSkatGUI().getRootPane(), skatSprueche[spruchWahl], wieHeisstEr, JOptionPane.WARNING_MESSAGE);
        boolean handWahl = (JOptionPane.showOptionDialog(sc.getSkatGUI().getRootPane(), "Hand-Spiel?", "Spieloptionen...", 
        		JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Nein", "Klar" }, null) == 1);
        boolean ouvertWahl = false;
        if (handWahl) {
        	ouvertWahl = (JOptionPane.showOptionDialog(sc.getSkatGUI().getRootPane(), "Ouvert?", "Spieloptionen...", 
            		JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Nein", "Klar" }, null) == 1);
        }
        boolean schneiderAnsagen = false;
        boolean schwarzAnsagen = false;
        if (ouvertWahl && spielartWahl != Spielart.NULLSPIEL) {
        	schneiderAnsagen = true;
        	schwarzAnsagen = true;
        }
        else if (handWahl && spielartWahl != Spielart.NULLSPIEL) {
        	int ansagen = JOptionPane.showOptionDialog(sc.getSkatGUI().getRootPane(), "Ansagen?", "Spieloptionen...", 
            		JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Nichts", "Schneider", "Schwarz" }, null);
        	schneiderAnsagen = (ansagen > 0);
        	schwarzAnsagen = (ansagen == 2);
        }
        this.spieler[0].setIstAlleinspieler(true);
        // TODO: Kontra
        int kontra = 0;
        
        this.spielart = new Spielart(spielartWahl, farbspiel, gereiztBis, 
        		handWahl, schneiderAnsagen, schwarzAnsagen, ouvertWahl, kontra);
        Spieler alleinspieler = null;
        for (int i = 0; i < 3; i++) {
        	// sortiere Spieler-Hände
        	this.spieler[i].getHand().sortiereHand(this.spielart);
        	if (this.spieler[i].istAlleinspieler()) alleinspieler = this.spieler[i];
        }
        String spielBezeichnung = alleinspieler.getName() + " spielt ";
        if (spielartWahl == 0) spielBezeichnung += farbspielOptionen[farbspielWahl];
        else spielBezeichnung += spielartOptionen[spielartWahl];
        if (!handWahl) {
        	alleinspieler.getHand().fuegeHinzu(skat[0]);
        	alleinspieler.getHand().fuegeHinzu(skat[1]);
        	skat[0] = null;
        	skat[1] = null;
        	JOptionPane.showMessageDialog(sc.getSkatGUI().getRootPane(), "Wähle nun zwei Karten, die du ablegen möchtest.", "Karten drücken", JOptionPane.WARNING_MESSAGE);
        	this.skatDruecken = true;
        }
        else {
        	JOptionPane.showMessageDialog(sc.getSkatGUI().getRootPane(), "Im Handspiel darfst du den Skat nicht aufnehmen, aber die Augen sind deine.", "Skat aufgenommen", JOptionPane.WARNING_MESSAGE);
        	alleinspieler.drueckeSkat(skat[0]);
        	alleinspieler.drueckeSkat(skat[1]);
        	skat[0] = null;
        	skat[1] = null;
        }
        this.sc.getSkatGUI().setTitle("Skat-Spiel: " + spielBezeichnung);
        this.sc.getSkatGUI().updateUI();
	}
	
	public void setSkatController(SkatController sc) {
		this.sc = sc;
	}
	
	public SkatController getSkatController() {JOptionPane.showMessageDialog(sc.getSkatGUI().getRootPane(), "Pech! Du musst trotzdem spielen.", "KI not found", JOptionPane.WARNING_MESSAGE);
		return this.sc;
	}
	
	public boolean nimmSkatAuf(Spieler sp) {
		if (this.skat[0] == null) return false;
		if (this.skat[1] == null) return false;
		sp.getHand().fuegeHinzu(this.skat[0]);
		sp.getHand().fuegeHinzu(this.skat[1]);
		this.skat[0] = null;
		this.skat[1] = null;
		return true;
	}
	
	public void setSpielErgebnisMeldung(String in) {
		this.spielErgebnisMeldung = in;
	}
	
	public String getSpielErgebnisMeldung() {
		return this.spielErgebnisMeldung;
	}
	
	public int starteReizen() {
		// und nun die Lottozahlen:
		int[] reizWerte = { 
				18, 20, 22, 23, 24, 27, 30, 33, 35, 36,
				40, 44, 45, 46, 48, 50, 54, 55, 59, 60,
				63, 66, 70, 72, 77, 80, 81, 84, 88, 90,
				96, 99, 100, 108, 110, 117, 120, 121,
				126, 130, 132, 135, 140, 143, 144, 150,
				153, 154, 156, 160, 162, 165, 168, 170,
				176, 180, 187, 192, 198, 204, 216, 240,
				264
		};
		String[] reizenOptionen = { "18", "Nein" };
		int reizenAuswahl;
		reizenAuswahl = JOptionPane.showOptionDialog(sc.getSkatGUI().getRootPane(), "Reizen beginnen?", "Reizen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, reizenOptionen, null);
		if (reizenAuswahl == 1) return 0;
		for (int i = 1; i < reizWerte.length; i++) {
			reizenOptionen[0] = String.valueOf(reizWerte[i]);
			reizenAuswahl = JOptionPane.showOptionDialog(sc.getSkatGUI().getRootPane(), "Gereizt bis " + reizWerte[i - 1] + ". Weiter reizen?", "Reizen", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, reizenOptionen, null);
			if (reizenAuswahl == 1) return reizWerte[i - 1];
		}
		return reizWerte[reizWerte.length - 1];
	}
	
	public boolean getSkatDruecken() {
		return this.skatDruecken;
	}
	
	public void skatGedrueckt() {
		if (++this.skatKartenGedrueckt == 2) {
			this.skatDruecken = false;
			getAlleinspieler().getHand().sortiereHand(this.spielart);
		}
	}
	
	public Spieler getAlleinspieler() {
		for (int i = 0; i < 3; i++) {
			if (this.spieler[i].istAlleinspieler()) return this.spieler[i];
		}
		return null; // sollte nie passieren!
	}

}
