package analogeUhrPackage;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends JPanel {	
	ButtonsPanel() {  //the constructor sets the size and position of the panel, paintComponents gets called automatically
		this.setPreferredSize(new Dimension(456, 144));
		this.setBounds(0, 456, 456, 146);
	}
	
	public void paintComponent(Graphics g) {  //sets the color of the panel
		super.paintComponent(g);
		this.setBackground(new Color(255, 150, 32));
	}
}