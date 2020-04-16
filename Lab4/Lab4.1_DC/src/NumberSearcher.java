import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NumberSearcher implements Runnable {
	
    private String name;
    private ReadWriteLock lock;
    private final File database;

    NumberSearcher(String name, ReadWriteLock lock, String fileName) {
    	
        this.name = name;
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
	
	                if (name.equals(str[0])) {
	                	System.out.println("Потік для пошуку за ім'ям знайшов наступний запис: " + name + ": " + str[1]);
	                    lock.unlockRead();
	                    return;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	        System.out.println("Потік для пошуку за ім'ям не знайшов такий запис: " + name);
	        lock.unlockRead();
	        
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	//}
    }
}
