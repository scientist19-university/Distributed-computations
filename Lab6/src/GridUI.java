package Lab6;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GridUI extends JPanel {
    private final static int AMOUNT_OF_WORKERS = 4;
    private ReentrantReadWriteLock lock;
    private Thread[] workers;

    private volatile FieldModel field;

    private int cellSize;
    private int cellGap = 1;

    private final Color[] civilColors = {Color.WHITE, Color.GREEN, Color.BLUE, Color.RED, Color.BLUE};

    GridUI(int width, int height, int size) {
        setBackground(Color.BLACK);
        cellSize = size;
        lock = new ReentrantReadWriteLock();
        field = new FieldModel(width, height);
        workers = null;
    }

    void startSimulation(int civAmount) {
        GridUpd updated = new GridUpd(this, field, lock);

        CyclicBarrier barrier = new CyclicBarrier(AMOUNT_OF_WORKERS, updated);
        field.clear();
        field.generate(civAmount);

        int quarterSize = field.getHeight() / 4;
        workers = new WorkerThread[AMOUNT_OF_WORKERS];
        for (byte i = 0; i < AMOUNT_OF_WORKERS; i++){
            if(i == AMOUNT_OF_WORKERS - 1)
                workers[i] = new WorkerThread(field, barrier, lock, quarterSize * i, field.getHeight()-1, civAmount);
            else
                workers[i] = new WorkerThread(field, barrier, lock, quarterSize * i, quarterSize * (i + 1), civAmount);
        }
        for (int i = 0; i < AMOUNT_OF_WORKERS; i++)
            workers[i].start();
    }

    void stopSimulation(JButton button) {
        button.setEnabled(false);
        if (workers != null)
            for (Thread worker : workers)
                worker.interrupt();
        workers = null;
        button.setEnabled(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (field != null) {
            lock.readLock().lock();
            super.paintComponent(g);

            Insets b = getInsets();
            for (int y = 0; y < field.getHeight(); y++)
                for (int x = 0; x < field.getWidth(); x++) {
                    byte cell = field.getCell(x, y).value;
                    g.setColor(civilColors[cell]);
                    g.fillRect(b.left + cellGap + x * (cellSize + cellGap), b.top + cellGap + y
                            * (cellSize + cellGap), cellSize, cellSize);
                }
            lock.readLock().unlock();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (field != null) {
            Insets b = getInsets();
            return new Dimension((cellSize + cellGap) * field.getWidth() + cellGap,
                    (cellSize + cellGap) * field.getHeight() + cellGap);
        } else return new Dimension(500, 500);
    }
}