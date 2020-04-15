import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.*;

public class Controller {

	private JFrame mainFrame;
	private String title = "Lab 1 Task 2";
	private int width = 500;
	private int height = 300;
	private JSlider slider;
	
	private Thread thread1;
	private Thread thread2;
	private int sleepTime = 30;
	private int sliderStep = 5;
	
	private AtomicInteger semaphore;
	private final int LOCKED = 1;
	private final int UNLOCKED = 0;
	
	private class LeftPuller implements Runnable {

		@Override
		public void run() {

			while (!Thread.currentThread().isInterrupted()) {
				
				try {
					
					Thread.sleep(sleepTime);
				} 
				catch (InterruptedException e) {
					
					return;
				}
				
				int diff = slider.getValue() - sliderStep;
				int valueToSet = diff < 10 ? 10 : diff;
				slider.setValue(valueToSet);
			}
		}	
	}
	
	private class RightPuller implements Runnable {

		@Override
		public void run() {
			
			while (!Thread.currentThread().isInterrupted()) {

				try {
					
					Thread.sleep(sleepTime);
				} 
				catch (InterruptedException e) {
					
					return;
				}
				
				int sum = slider.getValue() + sliderStep;
				int valueToSet = sum > 90 ? 90 : sum;
				slider.setValue(valueToSet);
			}
		}		
	}
	
	public Controller() {
		
		semaphore = new AtomicInteger();
		semaphore.set(UNLOCKED); 
		
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
		mainPanel.setLayout(new GridLayout(5,1)); 
		JButton t1StartBtn = new JButton("Start 1");
		JButton t2StartBtn = new JButton("Start 2");
		JButton t1StopBtn = new JButton("Stop 1");
		JButton t2StopBtn = new JButton("Stop 2");
		

		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		slider.setBounds(100, 100, 100, 40);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setForeground(Color.RED);
		
		t1StartBtn.addActionListener(e -> {
				
			if (semaphore.getAndSet(LOCKED) != LOCKED) {
				
				thread1 = new Thread(new LeftPuller());
				thread1.start();
				thread1.setPriority(Thread.MIN_PRIORITY);
				
				t1StopBtn.setEnabled(true);
				t2StopBtn.setEnabled(false);
				
				return;
			}
				
			showMessageDialog(null, "Locked by thread");
		});		
		
		t2StartBtn.addActionListener(e -> {
				
			if (semaphore.getAndSet(LOCKED) != LOCKED) {
				
				thread2 = new Thread(new RightPuller());
				thread2.start();
				thread2.setPriority(Thread.MAX_PRIORITY);

				t2StopBtn.setEnabled(true);
				t1StopBtn.setEnabled(false);
				
				return;
			}
				
			showMessageDialog(null, "Locked by thread");
		});

		t1StopBtn.addActionListener(e -> {
		
			thread1.interrupt();
			semaphore.getAndSet(UNLOCKED);
			t1StopBtn.setEnabled(false);
		});
		
		t2StopBtn.addActionListener(e -> {

			thread2.interrupt();
			semaphore.getAndSet(UNLOCKED);
			t2StopBtn.setEnabled(false);
		});
		
		mainPanel.add(t1StartBtn);
		mainPanel.add(t2StartBtn);
		mainPanel.add(t1StopBtn);
		mainPanel.add(t2StopBtn);
		mainPanel.add(slider);	
	}
}
