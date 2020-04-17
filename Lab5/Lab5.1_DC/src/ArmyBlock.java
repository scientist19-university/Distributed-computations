import java.util.Random;

public class ArmyBlock implements Runnable {
	
	private final int[] army;
    private final int beg;
    private final int end;
    private final Barrier barrier;
	
	public ArmyBlock(int[] army, int beg, int end, Barrier barrier) {
		
		this.army = army;
        this.barrier = barrier;
        this.beg = beg;
        this.end = end;
	}
	
	@Override
	public void run() {

		turn();
		
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void turn() {
		
		Random rand = new Random(System.nanoTime());
		
        for(int i = beg; i < end; ++i) {
        	
        	boolean turnLeft = rand.nextBoolean();
        	
            if (turnLeft) {
            	
                army[i] = -1;
                
                if (i-1 >= beg && army[i-1] == 1) {
                	
                    army[i-1] = -1;
                    army[i] = 1;
                }
            }
            else {
            	
                army[i] = 1;
                
                if (i+1 < end && army[i+1] == -1) {
                	
                    army[i+1] = 1;
                    army[i] = -1;
                }
            }
        }
    }
	
	public int check() {
		
		int turn = army[beg];
		
		for (int i = beg; i < end; ++i) {
			
			if (army[i] != turn) {
				return 0;
			}
		}
		return turn;
	}
}
