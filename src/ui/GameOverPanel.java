package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel{
	private JLabel label;
	 public JButton button;
	 public GameOverPanel() {
		 label = new JLabel("Game Over");
		 button = new JButton("Restart");
		 this.setLayout(new BorderLayout());
		 this.add(label, BorderLayout.CENTER);
		 this.add(button, BorderLayout.SOUTH);
		 
	 }
	 public void scoredisplay(int score) {
		 this.label.setText("<html>Game OVER! Score: " + score);
		 
		 

	 
	 }
}
