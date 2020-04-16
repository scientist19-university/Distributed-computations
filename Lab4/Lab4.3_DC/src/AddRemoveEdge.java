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
                System.out.println("��������� ��'���� ����������� �����������");
                start = random.nextInt(Main.cities.size());
                dest = random.nextInt(Main.cities.size());
                while (dest == start) dest = random.nextInt(Main.cities.size());
                Main.cities.get(start).addEdge(Main.cities.get(dest), random.nextInt(50));
                System.out.println("��������� ��'���� ������������ �����������");
                Main.rwl.writeLock().unlock();
                sleep(1000);

                Main.rwl.writeLock().lock();
                System.out.println("��������� ��'���� ����������� �����������");
                start = random.nextInt(Main.cities.size());
                dest = random.nextInt(Main.cities.size());
                while (dest == start) dest = random.nextInt(Main.cities.size());
                Main.cities.get(start).deleteEdge(Main.cities.get(dest));
                System.out.println("��������� ��'���� ������������ �����������");
                Main.rwl.writeLock().unlock();
                sleep(1000);
            }
        } catch (InterruptedException ignored) {
        }
    }
}