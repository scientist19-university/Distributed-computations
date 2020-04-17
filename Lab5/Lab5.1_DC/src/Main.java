import java.util.Random;

public class Main {
	
    public static void main(String[] args) {
    	
    	final int size = 100;
    	final int discrete = 50;
    	int[] army = new int[size];
    	Random rand = new Random(System.nanoTime());
    	
    	for (int i = 0; i < size; ++i) {
    		
    		boolean turnedLeft = rand.nextBoolean();
    		if (turnedLeft) army[i] = -1;
    		else army[i] = 1;
    	}
    	
    	Barrier barrier = new Barrier(size/discrete);
    	
    	ArmyBlock[] blocks = new ArmyBlock[size/discrete];
    	
    	for (int i = 0; i < 2; ++i)
    		blocks[i] = new ArmyBlock(army, discrete * i, discrete * (i + 1), barrier);
    	
    	boolean finished = true;
    	
    	do {
    		finished = true;
    		
    		for (int i = 0; i < size/discrete; ++i)
    			new Thread(blocks[i]).start();
    		
    		int[] checks = new int[size/discrete];
    		
    		try {
    			// wait to perform correct checks
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    		for (int i = 0; i < checks.length; ++i) {
    			checks[i] = blocks[i].check();
    			if (checks[i] == 0) {
    				finished = false;
    				break;
    			}
    		}
    		
    		for (int i = 0; i < checks.length - 1; ++i) {
    		
    			if (checks[i] == 1 && checks[i+1] == -1) {
    				finished = false;
    				break;
    			}
    		}
    		
    	} while (!finished);
        
    	System.out.println("Строй стационарен: ");
    	for (int i = 0; i < size; ++i)
    		System.out.print(army[i] + " ");
    }
}