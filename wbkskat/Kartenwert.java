package src;

public enum Kartenwert {
    SIEBEN(0,0), ACHT(1,1), NEUN(2,2), ZEHN(3,6), BUBE(4,3), DAME(5,4), KOENIG(6,5), ASS(7,7);

    private final int rang;
    private final int nullrang;
    
    Kartenwert(int nullrang, int rang) {
        this.nullrang = nullrang;
        this.rang = rang;
    }

    public boolean istHoeherAls(Kartenwert andere) {
        return this.rang > andere.rang;
    }
    public boolean istHoeherAlsNull(Kartenwert andere) {
    	return this.nullrang > andere.nullrang;
    }
   public int getRang () {
	   return this.rang;
   }
   public int getNullrang () {
	   return this.nullrang;
   }
}



