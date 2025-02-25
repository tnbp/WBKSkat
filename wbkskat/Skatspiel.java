package wbkskat;

public class Skatspiel {
	
	private Spieler[] spieler;
	private Spieler kommtRaus;
	private Spielart spielart;
	private Karte[] skat;
	
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

}
