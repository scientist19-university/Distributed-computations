import java.util.concurrent.RecursiveTask;

public class Fight extends RecursiveTask {

    private final int fighters = 2;
    private Monk[] participants;
    private int beg, end;

    Fight(Monk[] participants, int beg, int end) {
    	
        this.participants = participants;
        this.beg = beg;
        this.end = end;
    }

    protected Object compute() {
    	
        if ((end - beg) < fighters) {
        	
            return (participants[beg].getZi() > participants[end].getZi() ? participants[beg] : participants[end]);
        } 
        else {
        	
            int mid = (beg + end) / 2;

            Fight left = new Fight(participants, beg, mid);
            Fight right = new Fight(participants, mid + 1, end);

            left.fork();
            right.fork();

            Monk fighter1 = (Monk)left.join();
            Monk fighter2 = (Monk)right.join();
            
            return (fighter1.getZi() > fighter2.getZi() ? fighter1 : fighter2);
        }
    }

}
