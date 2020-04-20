package Lab6;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel fieldPanel;
    private JButton startButton;
    private JButton stopButton;

    private int scrollPane;

    MainFrame() {
        super("Game of life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.SOUTH);

        fieldPanel = new FieldPanel(50, 50, 12);
        add(fieldPanel);
        startButton = new JButton("Start");
        toolBar.add(startButton);
        stopButton = new JButton("Stop");
        stopButton.setEnabled(true);
        toolBar.add(stopButton);

        final DefaultComboBoxModel<String> civilAmountModel = new DefaultComboBoxModel<>();
        civilAmountModel.addElement("1");
        civilAmountModel.addElement("2");
        civilAmountModel.addElement("3");
        final JComboBox<String> civilCombo = new JComboBox<>(civilAmountModel);
        civilCombo.setSelectedIndex(0);
        JScrollPane civilScrollPane = new JScrollPane(civilCombo);
        toolBar.add(civilScrollPane);

        startButton.addActionListener(e -> {
            if (civilCombo.getSelectedIndex() != -1)
                scrollPane = Integer.parseInt(civilCombo.getItemAt(civilCombo.getSelectedIndex()));
            ((FieldPanel) fieldPanel).startSimulation(scrollPane);
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            civilCombo.setEnabled(false);
        });

        stopButton.addActionListener(e -> {
            ((FieldPanel) fieldPanel).stopSimulation(startButton);
            stopButton.setEnabled(false);
            civilCombo.setEnabled(true);
        });
        pack();
        setVisible(true);
    }
}