package wbkskat;

public class Skatspiel {
	
	private Spieler[] spieler;
	private Spieler kommtRaus;
	private Spielart spielart;
	
	public Skatspiel(Spielart sp) {
		this.spielart = sp;
	}
	
	public Spieler[] getSpieler() {
		return this.spieler;
	}
	
	public Spieler werKommtRaus() {
		return this.kommtRaus;
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

}
