package wbkskat;

public class Spieler {
	
	private String name;
	private int punktestand;
	private boolean istAlleinspieler;
	private Karte[] stichStapel;
	private Hand spielerHand;
	private Position pos;
	private Karte letzteKarte;
	
	public Spieler (String name, Position pos) {
		this.name = name;
		this.stichStapel = new Karte[32];
		this.spielerHand = new Hand(this);
		this.pos = pos;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public int getPunktestand() {
		return this.punktestand;
	}
	
	public int aenderePunktestand(int punkteaenderung) {
		this.punktestand = this.punktestand + punkteaenderung;
		return this.punktestand;
	}
	
	
	public boolean istAlleinspieler() {
		return this.istAlleinspieler;
	}
	
	public void setIstAlleinspieler(boolean solo) {
		this.istAlleinspieler = solo;
	}
	
	public Karte[] getStichStapel() {
		return this.stichStapel;
	}
	
	public Hand getHand() {
		return this.spielerHand;
	}
	
	public void nimmStichZuDir(Karte[] stich) {
		int i = 0;
		for (; i < this.stichStapel.length; i++) {
			if (this.stichStapel[i] == null) break;
		}
		this.stichStapel[i] = stich[0];
		this.stichStapel[i+1] = stich[1];
		this.stichStapel[i+2] = stich[2];
	}
	
	public void drueckeSkat(Karte skatKarte) {
		if (this.stichStapel[0] == null) this.stichStapel[0] = skatKarte;
		else this.stichStapel[1] = skatKarte;
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public Karte getLetzteKarte() {
		return this.letzteKarte;
	}
	
	public void setLetzteKarte(Karte k) {
		this.letzteKarte = k;
	}
}
