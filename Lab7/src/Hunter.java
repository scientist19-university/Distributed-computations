package Lab7.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Hunter extends Thread {
    private JLabel hunterLabel;
    private GamePanel panel;
    private int x;
    private int y;
    private int labelWidth = 118;
    private int labelHeight = 215;

    private ImageIcon hunterRight = new ImageIcon("src/Lab7/resources/hunterLeft.png");
    private ImageIcon hunterLeft = new ImageIcon("src/Lab7/resources/hunterRight.png");

    private int side = 1;

    private int dx = 20;

    private int panelWidth;
    private volatile int countBullet = 0;
    private Hunter hunter = this;

    private boolean keyLeft = false;
    private boolean keyRight = false;

    Hunter(MainFrame main, GamePanel panel) {
        this.panelWidth = panel.width;
        this.panel = panel;
        x = panel.width / 2;
        y = panel.height - labelHeight;
        hunterLabel = new JLabel(new ImageIcon("./resources/hunterRight.png"));
        hunterLabel.setSize(new Dimension(labelWidth, labelHeight));
        hunterLabel.setLocation(x, y);
        hunterLabel.setVisible(true);
        panel.add(hunterLabel);
        HunterKeyListener keyListener = new HunterKeyListener();
        main.addKeyListener(keyListener);
    }

    synchronized void addBullet(int num) {
        countBullet += num;
    }

    public class HunterKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (panel != null)
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        if (countBullet < 10) {
                            Bullet bullet;
                            if (side == 1)
                                bullet = new Bullet(panel, hunter, x, y + 50);
                            else bullet = new Bullet(panel, hunter, x + labelWidth, y + 50);
                            bullet.start();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        keyLeft = true;
                        if (x > 0)
                            if (side != 1) {
                                hunterLabel.setIcon(hunterLeft);
                                side = 1;
                            }
                        break;
                    case KeyEvent.VK_RIGHT:
                        keyRight = true;
                        if (x < panelWidth - labelWidth)
                            if (side != 2) {
                                hunterLabel.setIcon(hunterRight);
                                side = 2;
                            }
                        break;
                    default:
                        break;
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                keyRight = false;
            else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                keyLeft = false;
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (keyLeft && x - dx >= 0)
                x -= dx;
            else if (keyRight && x + dx + labelWidth <= panelWidth)
                x += dx;

            hunterLabel.setLocation(x, y);
            try {
                sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
        panel.remove(hunterLabel);
    }
}