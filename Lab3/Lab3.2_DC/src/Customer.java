import java.util.concurrent.Semaphore;

public class Customer implements Runnable {
  
	int id;
	boolean notYetCut = true;
	int timeOfHaircut;
	Semaphore barber;
	Semaphore customers;
	Semaphore accessSeats;

	public Customer(int id, int timeOfHaircut, Semaphore barber,
					Semaphore customers, Semaphore accessSeats) {
		
		this.id = id;
		this.timeOfHaircut = timeOfHaircut;
		this.barber = barber;
		this.customers = customers;
		this.accessSeats = accessSeats;
	}
	
	@Override
	public void run() {   
		
	    while (notYetCut) {  
	    	
	    	try {
	    		
	    		accessSeats.acquire();  //tries to get access to the chairs
		        System.out.println("Customer " + id + " has just sat down in the waiting room.");
		        customers.release();  //notify the barber that there is a customer
		        accessSeats.release();  // don't need to lock the chairs anymore  
		        
		        try {
		
		        	barber.acquire();  // now it's this customers turn but we have to wait if the barber is busy
		    		notYetCut = false;  // this customer will now leave after the procedure
		    	    this.getHaircut();  //cutting...
		    	    
		        } catch (InterruptedException ex) {}
	    	} catch (InterruptedException e) { }
	    }
		        
	  }
	  
	public void getHaircut() {
	    
		System.out.println("Customer " + id + " is getting his hair cut");
		
	    try {
	    	Thread.sleep(timeOfHaircut);
	    } catch (InterruptedException ex) {}
	}
}