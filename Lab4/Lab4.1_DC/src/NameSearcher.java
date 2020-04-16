import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NameSearcher implements Runnable {
	
    private String number;
    private ReadWriteLock lock;
    private final File database;

    NameSearcher(String number, ReadWriteLock lock, String fileName) {
    	
        this.number = number;
        this.lock = lock;
        
        database = new File(fileName);
    }

    public void run() {
    	
    	//for (int i = 0; i < 10; ++i) {
    		
	        try {
	            lock.lockRead();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	
	        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(database))) {
	        	
	            String line;
	
	            while ((line = bufferedReader.readLine()) != null) {
	                String[] str = line.split(" - ");
	
	                if (number.equals(str[1])) {
	                	System.out.println("Потік для пошуку за номером знайшов наступний запис: " + str[0] + ": " + str[1]);
	                    lock.unlockRead();
	                    return;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	        System.out.println("Потік для пошуку за номером не знайшов такий запис: " + number);
	        lock.unlockRead();
	        
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	//}
    }
}
