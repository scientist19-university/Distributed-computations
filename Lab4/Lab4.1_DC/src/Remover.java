import java.io.*;

public class Remover implements Runnable {
	
    private String name;
    private String number;
    private ReadWriteLock lock;
    private File newFile;
    private File oldFile;

    Remover(String name, String number, ReadWriteLock lock, String fileName) {
    	
        this.name = name;
        this.number = number;
        this.lock = lock;
        
        newFile = new File(fileName + ".tmp");
        oldFile = new File(fileName);
    }

    public void run() {
    	
    	//for (int i = 0; i < 10; ++i) {
    		
	        try {
	            lock.lockWrite();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	
	        boolean flag = false;
	
	        try (PrintWriter printWriter = new PrintWriter(new FileWriter(newFile))) {
	
	            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(oldFile))) {
	            	
	                String line;
	
	                while ((line = bufferedReader.readLine()) != null) {
	                	
	                    String[] str = line.split(" - ");
	
	                    /*if (!str[0].equals(name) && !str[1].equals(number)) 
	                        printWriter.println(line);
	                    else 
	                        flag = true;
	                        */
	                    if (str[0].equals(name)) flag = true;
	                    else printWriter.println(line);
	                }
	            }
	            
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	
	        if (flag) {
	        	
	            oldFile.delete();
	            newFile.renameTo(oldFile);
	            System.out.println("Потік для видалення за ім'ям видалив наступний запис: " + name + " " + number);
	        } 
	        else {
	        	
	            newFile.delete();
	            System.out.println("Потік для видалення за ім'ям не знайшов такий запис: " + name + " " + number);
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
