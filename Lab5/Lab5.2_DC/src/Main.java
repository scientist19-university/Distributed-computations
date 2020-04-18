import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    
    public static void main(String[]args) {
        
        ArrayList<StringBuilder> strings = new ArrayList<>();
        strings.add(new StringBuilder("ABCD"));
        strings.add(new StringBuilder("CBAA"));
        strings.add(new StringBuilder("CDBC"));
        strings.add(new StringBuilder("ACBA"));
        
        AtomicBoolean finish = new AtomicBoolean(false);
        
        CyclicBarrier barrier = new CyclicBarrier(4, new CheckAction(strings, finish));

        for (StringBuilder s : strings) 
        	new Thread(new StringChanger(barrier, s, finish)).start();
    }
}