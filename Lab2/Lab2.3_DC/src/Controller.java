import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Controller {

	private ForkJoinPool forkJoinPool;
	private Monk[] fighters;
	private Random generator;
	
	public Controller(int size, int ziBound) {

		forkJoinPool = new ForkJoinPool();
		fighters = new Monk[size];
		generator = new Random();
		
	    for (int i = 0; i < fighters.length; ++i) {
	    	
	    	fighters[i] = new Monk((generator.nextBoolean() ? Monk.Monastery.GUAN_YANG : Monk.Monastery.GUAN_YING),
	    							generator.nextInt(ziBound) + 1);
	    }
	}
	
	public void start() {
		
	    System.out.println("Fight!");
	    Fight tournament = new Fight(fighters, 0, fighters.length - 1);
	    System.out.println("The winner is a " + (Monk)forkJoinPool.invoke(tournament));	    
	}
}
