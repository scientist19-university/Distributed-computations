import java.util.Random;

import static java.lang.Thread.sleep;

public class PathSearch implements Runnable {
    @Override
    public void run() {
        try {
            Random random = new Random();
            int origin;
            int destination;

            while (true) {
                Main.rwl.readLock().lock();
                System.out.println("Пошук маршруту заблокував читання");
                origin = random.nextInt(Main.cities.size());
                destination = random.nextInt(Main.cities.size());
                while (destination == origin)
                    destination = random.nextInt(Main.cities.size());
                synchronized (System.out) {Main.cities.get(origin).findPath(Main.cities.get(destination));}
                System.out.println("Пошук маршруту розблокував читання");
                Main.rwl.readLock().unlock();
                sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
