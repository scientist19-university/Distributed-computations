public class Barrier {

	private final int threads;
	private int waitingThreadsNumber;
	
	public Barrier(int threads) {
		
		this.threads = threads;
		waitingThreadsNumber = 0;
	}
	
	public synchronized void await() throws InterruptedException {
		
		if (++waitingThreadsNumber == threads) {

			waitingThreadsNumber = 0;
			notifyAll();
		}
		else 
			wait();
	}
}
