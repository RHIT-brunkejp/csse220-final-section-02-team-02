package app;

import java.awt.CardLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import app.MainApp;
import ui.GameComponent;
import ui.GameOverPanel;
import ui.WinScreen;

public class MainApp {
	private JFrame frame;
	GameOverPanel gameOver = new GameOverPanel();
	GameComponent game = new GameComponent();
	WinScreen winScreen = new WinScreen();
	JPanel cards = new JPanel(new CardLayout());

	public MainApp() {
		// Minimal model instance (empty for now, by design)
		frame = new JFrame("CSSE220 Final Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards.add(game, "GAME");
		cards.add(gameOver, "GAME OVER");
		cards.add(winScreen, "WIN");

		frame.setContentPane(cards);
		CardLayout cl = (CardLayout) cards.getLayout();
		gameOver.button.addActionListener(e -> {
			game.restart();
			cl.show(cards, "GAME");
			game.requestFocusInWindow();
		});
		frame.setSize(614, 630);
		frame.setLocationRelativeTo(null); // center on screen (nice UX, still minimal)
		Timer timer = new Timer(50, e -> {
			if (game.p.getHp() == 0) {
				this.setToOver();
			}
			if (game.gem.size() == 0 && game.walls[(game.p.getY() + 10) / 30][(game.p.getX() + 10) / 30] == 5) {
				this.setToWin();
			}
		});
		timer.start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainApp().run());
	}

	public void run() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void setToOver() {
		CardLayout cl = (CardLayout) cards.getLayout();
		gameOver.scoredisplay(game.score);
		cl.show(cards, "GAME OVER");
	}
	
	public void setToWin() {
	    CardLayout cl = (CardLayout) cards.getLayout();
	    winScreen.scoredisplay(game.score);
	    cl.show(cards, "WIN");
	}


}