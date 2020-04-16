import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Inserter implements Runnable {
	
    private String name;
    private String number;
    private ReadWriteLock lock;
    private final String fileName;

    Inserter(String name, String number, ReadWriteLock lock, String fileName) {
    	
        this.name = name;
        this.number = number;
        this.lock = lock;
        this.fileName = fileName;
    }

    @Override
    public void run() {
    	
    	//for (int i = 0; i < 10; ++i) {
    		
	        try {
	            lock.lockWrite();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	
	        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
	        	
	            printWriter.append(name).append(" - ").append(number);
	            printWriter.println();
	            System.out.println("Потік для записування додав наступні дані: " + name + " " + number);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            return;
	        }
	        
	        try {
				lock.unlockWrite();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	//}
    }
}
