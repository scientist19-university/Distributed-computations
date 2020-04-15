import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Estimator implements Runnable {

	private Queue<Item> itemsToEstimate;
	private AtomicInteger emptyCount;
	private AtomicInteger fullCount;
	
	@Override
	public void run() {

		while (true) {
			
			while (fullCount.get() == 0) /*busy wait*/ ;
			fullCount.decrementAndGet();
			Item item;
			synchronized (itemsToEstimate) {
				item = itemsToEstimate.poll();
			}
			emptyCount.incrementAndGet();
			estimate(item);
		}
	}
	
	public Estimator(AtomicInteger emptyCount,
			         AtomicInteger fullCount,
			         Queue<Item> itemsToEstimate) {
		
		this.itemsToEstimate = itemsToEstimate;
		this.emptyCount = emptyCount;
		this.fullCount = fullCount;
	}

	private void estimate(Item item) {
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		
		System.out.println(item.name + " estimated successfully. Approximate price is " + item.price);
	}
}
