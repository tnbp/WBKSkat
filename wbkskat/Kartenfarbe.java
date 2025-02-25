package src;

public enum Kartenfarbe {
    KARO(0), HERZ(1), PIK(2), KREUZ(3);

	private int farbrang;
	
	Kartenfarbe (int farbrang) {
		this.farbrang = farbrang;
	}
	
	public int getFarbrang () {
		return this.farbrang;
	}
}
	


