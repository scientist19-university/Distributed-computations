import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter forest size, please: ");		
		int forestSize = s.nextInt();
		int i = 0, j = 0;
		do {
			System.out.println("Enter Winnie the Pooh's coordinates (integers from 0 to " 
								+ (forestSize - 1) + ")" + "\nin the forest, too: ");	
			i = s.nextInt();
			j = s.nextInt();
			
		} while (i >= forestSize || i < 0 || j >= forestSize || j < 0);
		
		s.close();
		
		Controller c = new Controller(forestSize, i, j);
		c.start();
	}
}
