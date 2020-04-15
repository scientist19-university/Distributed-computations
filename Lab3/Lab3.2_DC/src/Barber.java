import java.util.concurrent.Semaphore;

public class Barber implements Runnable {
  
	Semaphore barber;
	Semaphore customers;
	Semaphore accessSeats;
	
	public Barber(Semaphore barber,
				  Semaphore customers, 
				  Semaphore accessSeats) {
	  
		this.barber = barber;
		this.customers = customers;
		this.accessSeats = accessSeats;
	}
  
	@Override
  	public void run() {
		
		while(true) {  

			try {
				
				customers.acquire(); // tries to acquire a customer - if none is available he goes to sleep
			    accessSeats.acquire(); // at this time he has been awaken -> want to modify the number of available seats
			    barber.release();  // the barber is ready to cut
			    accessSeats.release(); // we don't need the lock on the chairs anymore
			    this.cutHair();  //cutting...
			}
			catch (InterruptedException e) {}
		}
	}
   
    public void cutHair(){
    	
    	System.out.println("The barber is cutting hair");
    	
    	try {
    		Thread.sleep(1000);
    	} catch (InterruptedException ex){ }
    }
}       