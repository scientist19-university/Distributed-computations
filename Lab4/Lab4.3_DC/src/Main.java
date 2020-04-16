import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
	
    static ArrayList<Vertex> cities = new ArrayList<>();
    static ArrayList<Vertex> availableCities = new ArrayList<>();

    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);

    public static void main(String[] args) {

        cities.add(new Vertex(0, "���"));
        cities.add(new Vertex(1, "����"));
        cities.add(new Vertex(2, "������"));
        cities.add(new Vertex(3, "�������"));
        cities.add(new Vertex(4, "�����-���������"));
        cities.add(new Vertex(5, "������"));
        cities.add(new Vertex(6, "�������"));
        cities.add(new Vertex(7, "�������"));
        cities.add(new Vertex(8, "�������"));
        cities.add(new Vertex(9, "��������"));
        cities.add(new Vertex(10, "��������"));
        cities.add(new Vertex(11, "³�����"));
        cities.add(new Vertex(12, "�������"));

        availableCities.add(new Vertex(13, "�����"));
        availableCities.add(new Vertex(14, "�����"));
        availableCities.add(new Vertex(15, "�������"));
        availableCities.add(new Vertex(16, "����"));
        availableCities.add(new Vertex(17, "�������������"));
        availableCities.add(new Vertex(18, "�������"));
        availableCities.add(new Vertex(19, "��������"));
        availableCities.add(new Vertex(20, "�����"));
        availableCities.add(new Vertex(21, "г���"));
        availableCities.add(new Vertex(22, "�����"));
        availableCities.add(new Vertex(23, "������������"));
        availableCities.add(new Vertex(24, "�����������"));
        availableCities.add(new Vertex(25, "ѳ���������"));

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int origin = random.nextInt(cities.size());
            int destination = random.nextInt(cities.size());
            while (destination == origin)
                destination = random.nextInt(cities.size());
            cities.get(origin).addEdge(cities.get(destination), random.nextInt(50));
        }
        for (Vertex vertex : cities) {
            System.out.println(vertex);
        }

        Thread priceChanger = new Thread(new ChangeWeight());
        Thread connectionsChanger = new Thread(new AddRemoveEdge());
        Thread citiesChanger = new Thread(new AddRemoveVertex());
        Thread PathSearch1 = new Thread(new PathSearch());
        Thread PathSearch2 = new Thread(new PathSearch());
        Thread PathSearch3 = new Thread(new PathSearch());
        Thread PathSearch4 = new Thread(new PathSearch());
        Thread PathSearch5 = new Thread(new PathSearch());

        priceChanger.start();
        connectionsChanger.start();
        citiesChanger.start();
        PathSearch1.start();
        PathSearch2.start();
        PathSearch3.start();
        PathSearch4.start();
        PathSearch5.start();
    }}