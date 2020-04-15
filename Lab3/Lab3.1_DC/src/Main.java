public class Main {

	public static void main(String[] args) {

		HoneyPot honey = new HoneyPot(20);
		
		Bear Winnie = new Bear(honey);
		Thread bearThread = new Thread(Winnie);		
		bearThread.start();		
		
		for (int i = 0; i < 10; i++) {
			Bee bee = new Bee(honey, "Bee " + i);
			Thread beeThread = new Thread(bee);
			beeThread.start();
		}
	}

}
