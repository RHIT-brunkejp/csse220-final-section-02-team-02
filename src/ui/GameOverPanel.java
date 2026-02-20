package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel{
	private JLabel label;
	 public JButton button;
	 public GameOverPanel() {

		    label = new JLabel("Game Over");
		    label.setHorizontalAlignment(JLabel.CENTER);
		    label.setFont(new Font("Arial", Font.BOLD, 40));

		    button = new JButton("Restart");

		    this.setLayout(new BorderLayout());
		    this.add(label, BorderLayout.CENTER);
		    this.add(button, BorderLayout.SOUTH);
		}

	 public void scoredisplay(int score) {
		 this.label.setText("<html>Game OVER! Score: " + score);
		 
		 

	 
	 }
}
