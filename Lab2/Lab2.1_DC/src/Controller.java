import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

	private boolean[][] forest;
	private AtomicBoolean WinnieFound;
	private AtomicInteger currentTask;
	
	public Controller(int forestSize, int iWinnie, int jWinnie) {
		
		forest = new boolean[forestSize][forestSize];
		WinnieFound = new AtomicBoolean();
		currentTask = new AtomicInteger();
		forest[iWinnie][jWinnie] = true;
	}
	
	public void start() {
		
		int cores = Runtime.getRuntime().availableProcessors();
		
		for (int i = 0; i < cores; ++i) {
			
			new Thread(new BeePunisherPride(forest, currentTask, WinnieFound, i)).start();
		}
	}
}
