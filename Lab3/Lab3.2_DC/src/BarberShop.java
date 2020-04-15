import java.util.concurrent.*;

public class BarberShop {

  
  
    public Semaphore customers = new Semaphore(0);
    public Semaphore barber = new Semaphore(0);
    public Semaphore accessSeats = new Semaphore(1);
   
   
  

    public void start() {  
    	
		Thread barberThread = new Thread(new Barber(barber, customers, accessSeats));
		barberThread.start();
   
		for (int i=1; i <= 10; i++) {
			
		    Thread t = new Thread(new Customer(i, 1050, barber, customers, accessSeats));
		    t.start();
	        try {
	        	Thread.sleep(300);
	        } catch(InterruptedException ex) {};
		}
   
	    try {
	    	Thread.sleep(10000);
	    } catch (InterruptedException e) {}
   
	    for (int i=11; i <= 20; i++) {
		   
		    Thread t = new Thread(new Customer(i, 1050, barber, customers, accessSeats));
		    t.start();
	        try {
	        Thread.sleep(300);
	        } catch(InterruptedException ex) {};
	    }
    } 
}