import java.util.Random;

import static java.lang.Thread.sleep;

public class AddRemoveEdge implements Runnable {
    @Override
    public void run() {
        try {
            int start;
            int dest;
            Random random = new Random();

            while (true) {

                Main.rwl.writeLock().lock();
                System.out.println("Додавання зв'язків заблокувало записування");
                start = random.nextInt(Main.cities.size());
                dest = random.nextInt(Main.cities.size());
                while (dest == start) dest = random.nextInt(Main.cities.size());
                Main.cities.get(start).addEdge(Main.cities.get(dest), random.nextInt(50));
                System.out.println("Додавання зв'язків розблокувало записування");
                Main.rwl.writeLock().unlock();
                sleep(1000);

                Main.rwl.writeLock().lock();
                System.out.println("Видалення зв'язків заблокувало записування");
                start = random.nextInt(Main.cities.size());
                dest = random.nextInt(Main.cities.size());
                while (dest == start) dest = random.nextInt(Main.cities.size());
                Main.cities.get(start).deleteEdge(Main.cities.get(dest));
                System.out.println("Видалення зв'язків розблокувало записування");
                Main.rwl.writeLock().unlock();
                sleep(1000);
            }
        } catch (InterruptedException ignored) {
        }
    }
}