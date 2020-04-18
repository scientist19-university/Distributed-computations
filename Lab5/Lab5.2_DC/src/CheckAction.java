import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CheckAction implements Runnable {

	private ArrayList<StringBuilder> strings;
    private AtomicBoolean condition;
    
    public CheckAction(ArrayList<StringBuilder> strings, AtomicBoolean condition) {
    	
        this.strings = strings;
        this.condition = condition;
    }
    
	@Override
	public void run() {

		int[] sums = new int[strings.size()];
        int i = 0;
        
        for (StringBuilder s : strings) {
        	
            for (int j = 0; j < s.length(); ++j) {
                if(s.charAt(j) == 'A' || s.charAt(j) == 'B'){
                    sums[i]++;
                }
            }
            ++i;
        }

        if ((sums[0]==sums[1] &&sums[0]==sums[2]) ||
        	(sums[0]==sums[1] &&sums[0]==sums[3]) ||
        	(sums[0]==sums[2] &&sums[0]==sums[3]) ||
        	(sums[1]==sums[2] &&sums[2]==sums[3])) {
        	
            System.out.println("Done! Current strings:");
            for (StringBuilder s : strings) 
            	System.out.println(s);
            
            condition.set(true);
        }
	}

}
