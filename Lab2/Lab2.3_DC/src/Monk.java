public class Monk {
	
    public enum Monastery {
    	
        GUAN_YING, 
        GUAN_YANG
    }

    private Monastery monastery;
    private int zi;

    Monk(Monastery monastery, int zi) {
    	
        this.monastery = monastery;
        this.zi = zi;
    }

    public int getZi(){
    	
        return zi;
    }

    public String toString() {
        return "monk of the wonderful monastery "
                + (monastery == Monastery.GUAN_YING ? "Guan Ying" : "Guan Yang")
                + " having INCREDIBLE ZI ENERGY of value " + zi;
    }
}
