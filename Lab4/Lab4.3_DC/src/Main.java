import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
	
    static ArrayList<Vertex> cities = new ArrayList<>();
    static ArrayList<Vertex> availableCities = new ArrayList<>();

    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);

    public static void main(String[] args) {

        cities.add(new Vertex(0, "Київ"));
        cities.add(new Vertex(1, "Львів"));
        cities.add(new Vertex(2, "Чернігів"));
        cities.add(new Vertex(3, "Житомир"));
        cities.add(new Vertex(4, "Івано-Франківськ"));
        cities.add(new Vertex(5, "Херсон"));
        cities.add(new Vertex(6, "Полтава"));
        cities.add(new Vertex(7, "Черкаси"));
        cities.add(new Vertex(8, "Чернівці"));
        cities.add(new Vertex(9, "Тернопіль"));
        cities.add(new Vertex(10, "Запоріжжя"));
        cities.add(new Vertex(11, "Вінниця"));
        cities.add(new Vertex(12, "Миколаїв"));

        availableCities.add(new Vertex(13, "Дніпро"));
        availableCities.add(new Vertex(14, "Луцьк"));
        availableCities.add(new Vertex(15, "Ужгород"));
        availableCities.add(new Vertex(16, "Суми"));
        availableCities.add(new Vertex(17, "Кропивницький"));
        availableCities.add(new Vertex(18, "Донецьк"));
        availableCities.add(new Vertex(19, "Луганськ"));
        availableCities.add(new Vertex(20, "Одеса"));
        availableCities.add(new Vertex(21, "Рівне"));
        availableCities.add(new Vertex(22, "Харків"));
        availableCities.add(new Vertex(23, "Хмельницький"));
        availableCities.add(new Vertex(24, "Севастополь"));
        availableCities.add(new Vertex(25, "Сімферополь"));

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