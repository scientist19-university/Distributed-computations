import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BeePunisherPride implements Runnable {

	private boolean[][] forest;
	private AtomicInteger strNumber;
	private AtomicBoolean villainFound;
	private int i;
	private int prideID;
	
	public void run() {

		while (!villainFound.get() && 
			   ((i = strNumber.getAndIncrement()) != forest[0].length)) {
		
			for (int j = 0; j < forest[i].length; ++j) {
					
				if (forest[i][j]) {
						
					villainFound.set(true);
					logSuccess(j);
					return;
				}
			}
				
			logFailure();
		}
	}

	public BeePunisherPride(boolean[][] forest, AtomicInteger strNumber, AtomicBoolean villainFound, int prideID) {
		
		this.forest = forest;
		this.strNumber = strNumber;
		this.villainFound = villainFound;
		this.prideID = prideID;
	}
	
	public void logSuccess(int position) {
		
		System.out.println("Pride " + prideID + ": Winnie the Pooh found and examplary punished at the forest segment (" 
							+ i + "," + position + ")");
	}
	
	public void logFailure() {

		System.out.println("Pride " + prideID + ": Forest segment(line) " + i + " is free from the bear-villain");		
	}
}
