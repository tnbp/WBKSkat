package wbkskat;

public class Stichrunde {
	private Skatspiel s;
	private Karte[] aktuellerStich;
	private Spieler[] karteGespieltVon;
	private Spieler amZug;
	
	public Stichrunde(Skatspiel s) {
		System.out.println("DEBUG: Stichrunde angefangen!");
		this.s = s;
		this.aktuellerStich = new Karte[3];
		this.karteGespieltVon = new Spieler[3];
		this.amZug = s.werKommtRaus();
		s.setStichrunde(this);
	}
	
	public Stichrunde(Skatspiel s, Spieler kommtRaus) {
		this(s);
		this.amZug = kommtRaus;
	}
	
	public boolean spieleKarte(Karte k, Spieler sp) {
		// Wenn der Spieler nicht am Zug ist, darf er keine Karte spielen.
		if (!sp.equals(amZug)) return false;
		
		if (this.aktuellerStich[0] == null) {
			// Wenn der aktuelle Stich noch leer ist, kommen wir raus
			// und dürfen jede Karte spielen.
			this.aktuellerStich[0] = k;
			this.karteGespieltVon[0] = sp;
			sp.getHand().legeAb(k);
			this.amZug = s.naechsterSpieler(sp);
			return true;
		}
		// hier könnte Ihr else stehen
		if (s.getSpielart().istTrumpf(this.aktuellerStich[0])) {
			// es muss Trumpf bekannt/zugegeben werden
			boolean hatTrumpf = false;
			for (int i = 0; i < sp.getHand().getKarten().size(); i++) {
				if (s.getSpielart().istTrumpf(sp.getHand().getKarten().get(i))) {
					// wir können bekennen!
					hatTrumpf = true;
					break;
				}
			}
			// wenn wir bekennen können, es aber nicht tun, ist der Zug ungültig
			if (hatTrumpf && !s.getSpielart().istTrumpf(k)) return false;
			// ansonsten ist unser Zug gültig, egal ob wir bekennen können oder nicht
			int idx = 1;
			if (this.aktuellerStich[1] != null) idx = 2; // es liegen schon 2 Karten
			this.aktuellerStich[idx] = k;
			this.karteGespieltVon[idx] = sp;
			sp.getHand().legeAb(k);
			this.amZug = s.naechsterSpieler(sp);
			return true;
		}
		// ab hier müssen wir keinen Trumpf bekennen--und dürfen auch keinen spielen,
		// wenn wir bekennen können
		if (this.aktuellerStich[0].getFarbe() != k.getFarbe() || s.getSpielart().istTrumpf(k)) {
			for (int i = 0; i < sp.getHand().getKarten().size(); i++) {
				if (sp.getHand().getKarten().get(i).getFarbe() == this.aktuellerStich[0].getFarbe()
						&& !s.getSpielart().istTrumpf(sp.getHand().getKarten().get(i))) {
					// wir können bekennen!
					return false;
				}
			}
		}
		// unser aktueller Zug ist gültig, egal ob wir bekennen können oder nicht
		int idx = 1;
		if (this.aktuellerStich[1] != null) idx = 2; // es liegen schon 2 Karten
		this.aktuellerStich[idx] = k;
		this.karteGespieltVon[idx] = sp;
		sp.getHand().legeAb(k);
		this.amZug = s.naechsterSpieler(sp);
		return true;
	}
	
	public Spieler amZug() {
		return this.amZug;
	}
	
	public void setAmZug(Spieler s) {
		this.amZug = s;
	}
	
	public Karte[] getKartenImStich() {
		return this.aktuellerStich;
	}
	
	public Spieler hatKarteGespielt(Karte k) {
		for (int i = 0; i < 3; i++) {
			if (this.aktuellerStich[i] == null) return null;
			if (this.aktuellerStich[i] == k) return this.karteGespieltVon[i];
		}
		return null; // sollte nie passieren!!
	}
}
