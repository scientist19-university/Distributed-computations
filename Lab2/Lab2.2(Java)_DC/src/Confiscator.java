import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Confiscator implements Runnable {

	private Queue<Item> confiscatedItems;
	private AtomicInteger emptyCount;
	private AtomicInteger fullCount;
	
	private int currentID;
	private Random generator;
	private final int priceBound = 1000;
	private final int priceDiscrete = 10;
	
	@Override
	public void run() {

		while (true) {
			
			while (emptyCount.get() == 0) /*busy wait*/ ;
			emptyCount.decrementAndGet();
			Item item = confiscateItem();
			synchronized (confiscatedItems) {
				confiscatedItems.add(item);
			}
			fullCount.incrementAndGet();			
		}		
	}

	public Confiscator(AtomicInteger emptyCount, 
					   AtomicInteger fullCount,
					   Queue<Item> confiscatedItems) {
		
		this.confiscatedItems = confiscatedItems;
		this.emptyCount = emptyCount;
		this.fullCount = fullCount;
		
		currentID = 0;
		generator = new Random(System.nanoTime());
	}
	
	private Item confiscateItem() {
		
		String name = "Item #" + currentID++;
		System.out.println(name + " confiscated from the warehouse successfully");
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {}
		
		return new Item(name, generator.nextInt(priceBound/priceDiscrete) * priceDiscrete);
	}
}
