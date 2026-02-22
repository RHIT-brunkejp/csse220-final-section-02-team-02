package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel{
	private JLabel label;
	 public JButton button;
	 //declares the aspects needed for the panel
	 
	 public GameOverPanel() {

		    label = new JLabel("Game Over");
		    label.setHorizontalAlignment(JLabel.CENTER);
		    label.setFont(new Font("Arial", Font.BOLD, 40));
		    //sets the text and location options for the text

		    button = new JButton("Restart");

		    this.setLayout(new BorderLayout());
		    this.add(label, BorderLayout.CENTER);
		    this.add(button, BorderLayout.SOUTH);
		    //orients the aspects and creates the restart button
		}
 /**
  * This method sets the label to display the score of the game
  * @param score the current score of the game
  */
	 public void scoredisplay(int score) {
		 this.label.setText("<html>Game OVER! Score: " + score);
		 
		 

	 
	 }
}
