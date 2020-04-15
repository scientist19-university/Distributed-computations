public class Bear implements Runnable {

	private HoneyPot honey;
	
	public Bear(HoneyPot honey) {
		
		this.honey = honey;
	}

	@Override
	public void run() {

		while (true) {
			
			honey.clear();
		}
	}
}
