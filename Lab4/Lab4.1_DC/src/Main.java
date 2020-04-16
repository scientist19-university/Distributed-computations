import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
    	
        ReadWriteLock databaseLock = new ReadWriteLock();
        Random rand = new Random(System.nanoTime());
        String fileName = "database.txt";

        ArrayList<String> NAMES = new ArrayList<>(Arrays.asList("name1", "name2", "name3", "name4", "name5", "name6", "name7"));
        ArrayList<String> NUMBERS = new ArrayList<>(Arrays.asList("111111", "222222", "333333", "444444", "555555", "666666", "777777"));
        
        
        
        for (int i = 0; i <= NAMES.size() - 1; i++) {
            new Thread(new Inserter(NAMES.get(i), NUMBERS.get(i), databaseLock, fileName)).start();
            int numberIdx = Math.abs(rand.nextInt()) % NUMBERS.size();
            new Thread(new NameSearcher(NUMBERS.get(numberIdx), databaseLock, fileName)).start();
            int nameIdx = Math.abs(rand.nextInt()) % NAMES.size();
            new Thread(new NumberSearcher(NAMES.get(nameIdx), databaseLock, fileName)).start();
            int r = rand.nextInt(NAMES.size());
            new Thread(new Remover(NAMES.get(r), NUMBERS.get(r), databaseLock, fileName)).start();
        }
    }
}