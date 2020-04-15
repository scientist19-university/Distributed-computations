public class Bee implements Runnable {

	private HoneyPot honey;
	private String name;
	
	public Bee(HoneyPot honey, String name) {
		
		this.honey = honey;
		this.name = name;
	}

	@Override
	public void run() {
		
		while (true) {

		    try {
		        Thread.sleep(500);
		    } catch (InterruptedException e) { }
		    
			honey.fill(this);
		}
	}
	
	public String getName() {
		
		return name;
	}
}
