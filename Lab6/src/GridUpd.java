package Lab6;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GridUpd implements Runnable {
    private GridUI fieldPanel;
    private volatile GridLogic fieldModel;
    private ReentrantReadWriteLock lock;

    GridUpd(GridUI fieldPanel, GridLogic fieldModel, ReentrantReadWriteLock lock) {
        this.fieldPanel = fieldPanel;
        this.fieldModel = fieldModel;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.writeLock().lock();
        fieldModel.swapField();
        lock.writeLock().unlock();
        fieldPanel.repaint();

        try {
            int timeSleep = 300;
            Thread.sleep(timeSleep);
        } catch (InterruptedException ignored) {
        }
    }
}