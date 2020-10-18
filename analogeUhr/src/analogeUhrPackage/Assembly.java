package analogeUhrPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Assembly implements ActionListener {
	JButton[] buttons = {new JButton("Berlin"), new JButton("Moscow"), new JButton("Shanghai"), new JButton("Phoenix")};  //created here so that actionPerformed method can use them
	
	Assembly() {  //the constructor basically assembles all the parts together
		//creating the frame
		JFrame window = new JFrame("Analoge Uhr");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);	
		//creating the ClockPanel
		ClockPanel clock = new ClockPanel();	
		
		//configuring the buttons, adding them to the frame
		configureButton(buttons[0], 13, 503, 100, 50, this, false);
		configureButton(buttons[1], 123, 503, 100, 50, this, false);
		configureButton(buttons[2], 233, 503, 100, 50, this, false);
		configureButton(buttons[3], 343, 503, 100, 50, this, false);
		for(int i = 0; i < buttons.length; i++) {
			window.add(buttons[i]);
		}
		
		//adding the ClockPanel to the frame, glueing it to the top border
		window.add(clock, BorderLayout.NORTH);		
		//creating the buttonsPanel, adding it to the frame
		ButtonsPanel buttonsPanel = new ButtonsPanel();
		window.add(buttonsPanel);
		
		//packing the frame so that it fits around its components, centering it
		window.pack();
		window.setLocationRelativeTo(null);
	}
	
	void configureButton(JButton name, int x, int y, int width, int height, ActionListener a, boolean focusable) {  //allows easy configuration of created buttons
		name.setBounds(x, y, width, height);
		name.addActionListener(a);
		name.setFocusable(focusable);
		println("Testing Git")
	}

	@Override
	public void actionPerformed(ActionEvent e) {  //changes the time zone depending on which button was hit
		if(e.getSource() == buttons[0]) {
			ClockPanel.zoneI = 1;
		} else if(e.getSource() == buttons[1]) {
			ClockPanel.zoneI = 2;
		} else if(e.getSource() == buttons[2]) {
			ClockPanel.zoneI = 3;
		} else if(e.getSource() == buttons[3]) {
			ClockPanel.zoneI = 4;
		}
	}
}