import java.util.Random;

import static java.lang.Thread.sleep;

public class AddRemoveVertex implements Runnable {

    @Override
    public void run() {
        try {
            Random random = new Random();
            while (true) {

                Main.rwl.writeLock().lock();
                System.out.println("Додавання міст заблокувало записування");
                Vertex toDelete = Main.cities.get(random.nextInt(Main.cities.size()));
                Main.availableCities.add(toDelete);
                Main.cities.remove(toDelete);
                for (int i = 0; i < Main.cities.size(); i++)
                    Main.cities.get(i).deleteEdge(toDelete);
                System.out.println("Додавання міст розблокувало записування");
                Main.rwl.writeLock().unlock();
                sleep(1000);

                Main.rwl.writeLock().lock();
                System.out.println("Видалення міст заблокувало записування");
                Vertex toAdd = Main.availableCities.get(random.nextInt(Main.availableCities.size()));
                Main.cities.add(toAdd);
                Main.availableCities.remove(toAdd);
                System.out.println("Видалення міст розблокувало записування");
                Main.rwl.writeLock().unlock();
                sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
