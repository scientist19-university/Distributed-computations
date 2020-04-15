import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;

public class Controller {

	private JFrame mainFrame;
	private String title = "Task a)";
	private int width = 500;
	private int height = 300;
	private JSlider slider;
	
	private Thread thread1;
	private Thread thread2;
	private int sleepTime = 30;
	private int sliderStep = 5;
	
	public Controller() {
		
		thread1 = new Thread(()-> {
			
			while (!Thread.currentThread().isInterrupted()) {
				
				try {
					
					Thread.sleep(sleepTime);
				} 
				catch (InterruptedException e) {
					
					return;
				}
				
				synchronized (slider) {

					int diff = slider.getValue() - sliderStep;
					int valueToSet = diff < 10 ? 10 : diff;
					slider.setValue(valueToSet);
				}
			}
		});
		
		thread2 = new Thread(()-> {
			
			while (!Thread.currentThread().isInterrupted()) {

				try {
					
					Thread.sleep(sleepTime);
				} 
				catch (InterruptedException e) {
					
					return;
				}
				
				synchronized (slider) {

					int sum = slider.getValue() + sliderStep;
					int valueToSet = sum > 90 ? 90 : sum;
					slider.setValue(valueToSet);
				}
			}
		});
		
		thread1.setDaemon(true);
		thread2.setDaemon(true);
		
		initMainFrame();
	}

	private void initMainFrame() {
		
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		mainFrame.setBounds((dim.width - width)/2, (dim.height - height)/2, width, height);
		mainFrame.setTitle(title);
		
		JPanel mainPanel = new JPanel();
		mainFrame.add(mainPanel);
		mainPanel.setLayout(new GridLayout(6,1)); 
		JButton startBtn = new JButton("Start");
		JButton t1IncreaseBtn = new JButton("Increase thread 1 priority");
		JButton t1DecreaseBtn = new JButton("Decrease thread 1 priority");
		JButton t2IncreaseBtn = new JButton("Increase thread 2 priority");
		JButton t2DecreaseBtn = new JButton("Decrease thread 2 priority");		

		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		slider.setBounds(100, 100, 100, 40);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setForeground(Color.RED);
		
		startBtn.addActionListener(e -> {

				if (!thread1.isAlive())
					thread1.start();
				if (!thread2.isAlive())
					thread2.start();
		});
		
		t1IncreaseBtn.addActionListener(e -> {
			
			// Alternative version:
			// if (t1.getPriority() < Thread.MAX_PRIORITY)
			//     t1.setPriority(t1.getPriority() + 1);
			thread1.setPriority(Thread.MAX_PRIORITY);
		});
		
		t1DecreaseBtn.addActionListener(e -> {

			// Alternative version:
			// t1.setPriority(t1.getPriority() - 1);
			thread1.setPriority(Thread.MIN_PRIORITY);
		});

		t2IncreaseBtn.addActionListener(e -> {

			// Alternative version:
			// t2.setPriority(t2.getPriority() + 1);
			thread2.setPriority(Thread.MAX_PRIORITY);
		});
		
		t2DecreaseBtn.addActionListener(e -> {

			// Alternative version:
			// t2.setPriority(t2.getPriority() - 1);
			thread2.setPriority(Thread.MIN_PRIORITY);
		});
		
		mainPanel.add(startBtn);
		mainPanel.add(slider);
		mainPanel.add(t1IncreaseBtn);
		mainPanel.add(t1DecreaseBtn);
		mainPanel.add(t2IncreaseBtn);
		mainPanel.add(t2DecreaseBtn);
		
	}
}
