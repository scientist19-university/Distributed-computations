import java.util.Random;

import static java.lang.Thread.sleep;

public class ChangeWeight implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                int start;
                int dest;
                Random random = new Random();

                while (true) {
                    Main.rwl.writeLock().lock();
                    System.out.println("Зміна ціни заблокувала записування");
                    start = random.nextInt(Main.cities.size());
                    dest = random.nextInt(Main.cities.size());
                    while (dest == start) dest = random.nextInt(Main.cities.size());
                    Main.cities.get(start).changeWeight(Main.cities.get(dest), random.nextInt(50));
                    System.out.println("Зміна ціни розблокувала записування");
                    Main.rwl.writeLock().unlock();
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
