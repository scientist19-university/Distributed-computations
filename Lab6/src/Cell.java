package Lab6;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cell {
    ReentrantReadWriteLock lock;
    byte value;

    Cell() {
        lock = new ReentrantReadWriteLock();
    }
}