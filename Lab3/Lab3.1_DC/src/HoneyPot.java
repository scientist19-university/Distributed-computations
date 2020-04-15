public class HoneyPot {

	private int gulps;
	private final int capacity;
	
	public HoneyPot(int capacity) {
		
		this.capacity = capacity;
		gulps = 0;
	}
	
	synchronized public void fill(Bee b) {
		
		while (isFull()) {

	          try {
			      Thread.sleep(200);
	              System.out.println("! The honey pot is full !");
	              notify();
	              wait();
	          } catch (InterruptedException e) {
	              System.out.println(e);
	          }
		}
		
        System.out.println(b.getName() + " bring gulp of honey");
		gulps++;
	}
	
	synchronized public void clear() {
		
		while (!isFull()) {
			 
	         try {
	        	 wait();
	         } catch (InterruptedException e) {
	             System.out.println(e);
	         }
		}
	 
	    System.out.println("! The bear cleared the pot and fall asleep!");
		gulps = 0;
	    notifyAll();
	}
	
	public boolean isFull() {
		
		return gulps == capacity;
	}
}
