import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

	private Queue<Item> itemsToLoad;
	private Queue<Item> itemsToEstimate;
	
	private AtomicInteger emptyCountConfiscator;
	private AtomicInteger emptyCountEstimator;
	private AtomicInteger fullCountConfiscator;
	private AtomicInteger fullCountEstimator;
	
	public Controller(int confiscatorBufferSize, int estimatorBufferSize) {
		
		itemsToLoad = new LinkedList<Item>();
		itemsToEstimate = new LinkedList<Item>();
		fullCountConfiscator = new AtomicInteger();
		fullCountEstimator = new AtomicInteger();
		emptyCountConfiscator = new AtomicInteger();
		emptyCountEstimator = new AtomicInteger();
		emptyCountConfiscator.set(confiscatorBufferSize);
		emptyCountEstimator.set(estimatorBufferSize);
	}
	
	public void start() {
		
		Thread Ivanov = new Thread(new Confiscator(emptyCountConfiscator,
												   fullCountConfiscator,
												   itemsToLoad), "Ivanov");
		
		Thread Petrov = new Thread(new Carrier(emptyCountConfiscator,
				   							   fullCountConfiscator,
				   							   emptyCountEstimator,
				   							   fullCountEstimator,
				   							   itemsToLoad,
				   							   itemsToEstimate), "Petrov");
		
		Thread Necheporuk = new Thread(new Estimator(emptyCountEstimator,
												     fullCountEstimator,
												     itemsToEstimate), "Necheporuk");
		
		Ivanov.setDaemon(true);
		Petrov.setDaemon(true);
		Necheporuk.setDaemon(true);
		
		Ivanov.start();
		Petrov.start();
		Necheporuk.start();
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception in start()");
		}
		
		System.out.println("Main thread finished");
		
	}
}
