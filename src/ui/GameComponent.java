package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Enemy;
import entities.Gem;
import entities.Player;
import model.GameModel;

public class GameComponent extends JPanel {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int TILESIZE = 30;
	boolean firstload = true;
	private Timer timer;
	public ArrayList<Gem> gem = new ArrayList<Gem>();
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public Player p = new Player();
	Timer restartTimer;

	
	
	public int score = 0;
	private int hitCooldown = 0; // counts down in timer ticks
	private static final int HIT_DELAY = 25; // 25 ticks * 20ms = 500ms

	// USED A 2D MATRIX TO PORTRAY THE MAZE WITH EACH INDEX REPRESENTING A 30 x 30
	// PIXEL AREA
	int[][] walls = { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, },
			{ 0, 1, 1, 1, 1, 0, 4, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 5, 1, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, },
			{ 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 4, 1, 1, 1, 1, 0, 0, },
			{ 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, },
			{ 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 5, 0, 0, 0, 0, 1, 1, 0, 0, 0, },
			{ 0, 4, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 3, 2, 0, 1, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, } };
//0=wall, 1=path, 2=gem, 3=player,4=enemy,5=door
	int[][] level2 = { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 4, 1, 0, 1, 1, 0, 1, 1, 1, 0, },
			{ 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 2, 1, 0, 0, 1, 1, 1, 0, 1, 0, },
			{ 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 2, 0, 1, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, },
			{ 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 4, 1, 1, 0, 1, 1, 0, },
			{ 0, 1, 1, 0, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, },
			{ 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 4, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, },
			{ 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 2, 0, 1, 1, 0, },
			{ 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, },
			{ 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, },
			{ 0, 1, 1, 1, 0, 4, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, },
			{ 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, },
			{ 0, 1, 0, 1, 0, 5, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 3, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, } };
//0=wall, 1=path, 2=gem, 3=player,4=enemy,5=door
	public GameComponent() {

		// TIMER TO UPDATE ENEMY POSITIONS ALONG WITH REPAINTING THE SPRITES
		timer = new Timer(20, e -> {
			p.update(WIDTH, HEIGHT);
			for(Enemy i:enemies) {
				i.updateEnemy(walls, TILESIZE);
			}

			// cooldown countdown
			if (hitCooldown > 0)
				hitCooldown--;

			// collision check (only if not in cooldown)
			if (hitCooldown == 0) {
				for(Enemy z:enemies) {
				if (p.getBounds().intersects(z.getBounds())) {
					p.loseLife();
					hitCooldown = HIT_DELAY;
					System.out.println("Player hit! HP now: " + p.getHp());
					break;
				}
				}
			}

			repaint();
		});
		timer.start();

		// ACTION LISTENER FOR PLAYER MOVEMENT
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					if (p.canMove(walls, TILESIZE, p.getX(), p.getY() - 2)) {
						p.up();
						checkWin();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					if (p.canMove(walls, TILESIZE, p.getX(), p.getY() + 2)) {
						p.down();
						checkWin();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					if (p.canMove(walls, TILESIZE, p.getX() - 2, p.getY())) {
						p.left();
						checkWin();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					if (p.canMove(walls, TILESIZE, p.getX() + 2, p.getY())) {
						p.right();
						checkWin();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					for (int i = 0; i < gem.size(); i++) {
						if ((p.getX() + 15) / TILESIZE == gem.get(i).getxtile()
								&& (p.getY() + 17) / TILESIZE == gem.get(i).getytile()) {
							gem.remove(i);
							score +=10;
							System.out.println("REMOVED GEM");
							break;
						}
					}
				}
				repaint();
			}
		});
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Minimal placeholder to test it’s running
		loadLevel(walls,g2);
		firstload = false;
		for (Gem z : gem) {
			z.draw(g2);
		}
		for(Enemy z:enemies) {
			z.draw(g2);
		}
		p.draw(g2);
		g2.setColor(Color.WHITE);
		Font fon = new Font("Arial", Font.BOLD, 15);
		g2.setFont(fon);
		
		//score and lives 
		g2.drawString("Score: " + score , 10, 30);
		g2.drawString("Lives: " + p.getHp(), 10, 50);
		g2.drawString("W A S D to move,   ↓ to pick up gems", 180, 585);
		
		//game over
		
			

			
		}
		
	
	private void loadLevel(int[][] walls, Graphics2D g2) {
		Rectangle f = new Rectangle(30, 30);
        Color path = new Color(130, 245, 134);
		// TODO: draw based on model state
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (walls[i][j] == 1) {
					g2.setColor(path);
				} else if (walls[i][j] == 0) {
					g2.setColor(Color.BLACK);
				} else if (walls[i][j] == 2 && firstload) {
					gem.add(new Gem(j, i));
				}else if (walls[i][j] == 3 && firstload) {
					p.setPosition(j*30, i*30);
				}else if (walls[i][j] == 4 && firstload) {
					enemies.add(new Enemy(j*30,i*30));
				}else if (walls[i][j] == 5) {
					g2.setColor(new Color(150,75,0));
				}
				f.x = j * 30;
				f.y = i * 30;
				g2.draw(f);
				g2.fill(f);
				g2.setColor(path);
			}
		}
		
	}
	public void restart() {
		
		score = 0;
		p.setHp(3);
		enemies.clear();
		gem.clear();
		firstload=true;
        repaint();
		
	}
	public void checkWin() {
		if(gem.size()==0&&walls[p.getY()/30][p.getX()/30]==5) {
		 walls=level2;
		 restart();
		}
	}
	

}
