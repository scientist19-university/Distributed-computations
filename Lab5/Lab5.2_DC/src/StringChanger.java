import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class StringChanger implements Runnable {

	private final CyclicBarrier barrier;
	private StringBuilder stringBuilder;
	private AtomicBoolean finish;
	
	public StringChanger(CyclicBarrier barrier, StringBuilder stringBuilder, AtomicBoolean finish) {
		
		this.barrier = barrier;
		this.stringBuilder = stringBuilder;
		this.finish = finish;
	}
	
	private void changeLetter() {
		
		Random rand = new Random();
	    int letterIndex = rand.nextInt(stringBuilder.length());
	    char letter = stringBuilder.charAt(letterIndex);
	        
	    switch (letter) {
	    
	    	case 'A':
	            stringBuilder.setCharAt(letterIndex,'C');
	            System.out.println(String.format("%s changed letter %c to %c",Thread.currentThread().getName(),'A', 'C'));
	            break;
	        case 'B':
	            stringBuilder.setCharAt(letterIndex,'D');
	            System.out.println(String.format("%s changed letter %c to %c",Thread.currentThread().getName(),'B', 'D'));
                break;
            case 'C':
	            stringBuilder.setCharAt(letterIndex,'A');
	            System.out.println(String.format("%s changed letter %c to %c",Thread.currentThread().getName(),'C', 'A'));
	            break;
	        case 'D':
	            stringBuilder.setCharAt(letterIndex,'B');
	            System.out.println(String.format("%s changed letter %c to %c",Thread.currentThread().getName(),'D', 'B'));
	            break;
	    }
	    
	    try {
	        barrier.await();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    } catch (BrokenBarrierException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void run() {

		while (!finish.get()) {
			
			changeLetter();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}
}
