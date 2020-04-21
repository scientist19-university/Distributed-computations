package Lab7.src;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    MainFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        this.setSize(new Dimension(1400, 800));

        Dimension dim = getToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - 500, dim.height / 2 - 350);
        GamePanel panel = new GamePanel(this, 1366, 768);
        add(panel);
        setVisible(true);
    }
}