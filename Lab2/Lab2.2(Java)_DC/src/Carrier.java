import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Carrier implements Runnable {

	private Queue<Item> confiscatedItems;
	private Queue<Item> itemsToEstimate;
	private AtomicInteger emptyConfiscatedCount;
	private AtomicInteger fullConfiscatedCount;
	private AtomicInteger emptyEstimatedCount;
	private AtomicInteger fullEstimatedCount;
	
	@Override
	public void run() {

		while (true) {
			
			// Consumer behavior
			while (fullConfiscatedCount.get() == 0) /*busy wait*/ ;
			fullConfiscatedCount.decrementAndGet();
			Item item;
			synchronized (confiscatedItems) {
				item = confiscatedItems.poll();
			}
			emptyConfiscatedCount.incrementAndGet();
			loadItem(item);
			
			// Producer behavior
			while (emptyEstimatedCount.get() == 0) /*busy wait*/ ;
			sendToEstimator(item);
			emptyEstimatedCount.decrementAndGet();
			synchronized (itemsToEstimate) {
				itemsToEstimate.add(item);		
			}
			fullEstimatedCount.incrementAndGet();
		}
	}
	
	public Carrier(AtomicInteger emptyConfiscatedCount,
				   AtomicInteger fullConfiscatedCount,
				   AtomicInteger emptyEstimatedCount,
				   AtomicInteger fullEstimatedCount,
				   Queue<Item> confiscatedItems,
				   Queue<Item> itemsToEstimate) {
		
		this.emptyConfiscatedCount = emptyConfiscatedCount;
		this.fullConfiscatedCount = fullConfiscatedCount;
		this.emptyEstimatedCount = emptyEstimatedCount;
		this.fullEstimatedCount = fullEstimatedCount;
		this.confiscatedItems = confiscatedItems;
		this.itemsToEstimate = itemsToEstimate;
	}

	private void loadItem(Item item) {
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		
		System.out.println(item.name + " loaded into the carriage successfully");
	}
	
	private void sendToEstimator(Item item) {

		System.out.println(item.name + " sent to be estimated");
	}
}
