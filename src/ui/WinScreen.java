package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinScreen extends JPanel {

	private JLabel label;

	public WinScreen() {

		label = new JLabel("You Win!");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 40));
		//sets the font and the orientation of the text

		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.CENTER);
		//sets it as the center of the layout

	}

	public void scoredisplay(int score) {
		this.label.setText("You Win! Score: " + score);
		//displays the score on the label

	}

}