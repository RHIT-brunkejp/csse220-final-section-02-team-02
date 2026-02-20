package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	private BufferedImage wallimage;
	private BufferedImage doorimage;

	private GameModel model;
	private Image im;

	private Timer timer;
	private Timer movement;
	int flag = 0;
	public ArrayList<Gem> gem = new ArrayList<Gem>();
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public Player p = new Player();
	Timer restartTimer;

	public int score = 0;
	private int hitCooldown = 0; // counts down in timer ticks
	private static final int HIT_DELAY = 25; // 25 ticks * 20ms = 500ms

	// USED A 2D MATRIX TO PORTRAY THE MAZE WITH EACH INDEX REPRESENTING A 30 x 30
	// PIXEL AREA
	public int[][] walls = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 2, 0, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, },
			{ 0, 1, 1, 1, 1, 0, 4, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 2, 0, 0, 0, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 5, 1, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, },
			{ 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, },
			{ 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, },
			{ 0, 0, 1, 1, 2, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, },
			{ 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, },
			{ 0, 4, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 4, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 3, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, } };
//0=wall, 1=path, 2=gem, 3=player,4=enemy,5=door
	int[][] level2 = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
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

		try {
			wallimage = ImageIO.read(getClass().getResource("/ui/wall.png"));
		} catch (IOException e) {
			System.out.println("Could not load wall image.");
			e.printStackTrace();
		}

		try {
			doorimage = ImageIO.read(getClass().getResource("/ui/door1.png"));
		} catch (IOException e) {
			System.out.println("Could not load door image.");
			e.printStackTrace();
		}

		// TIMER TO UPDATE ENEMY POSITIONS ALONG WITH REPAINTING THE SPRITES
		movement = new Timer(20, e -> {
			if (flag == 1) {
				if (p.canMove(walls, TILESIZE, p.getX(), p.getY() - 2)) {
					p.up();
					checkWin();
				}
			}
			if (flag == 2) {
				if (p.canMove(walls, TILESIZE, p.getX(), p.getY() + 2)) {
					p.down();
					checkWin();
				}
			}
			if (flag == 3) {
				if (p.canMove(walls, TILESIZE, p.getX() - 2, p.getY())) {
					p.left();
					checkWin();
				}
			}
			if (flag == 4) {
				if (p.canMove(walls, TILESIZE, p.getX() + 2, p.getY())) {
					p.right();
					checkWin();
				}

			}
		});
		timer = new Timer(20, e -> {
			p.update(WIDTH, HEIGHT);
			for (Enemy i : enemies) {
				i.updateEnemy(walls, TILESIZE);
			}

			// cooldown countdown
			if (hitCooldown > 0)
				hitCooldown--;

			// collision check (only if not in cooldown)
			if (hitCooldown == 0) {
				for (Enemy z : enemies) {
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
		movement.start();

		// ACTION LISTENER FOR PLAYER MOVEMENT
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W && flag == 1) {
					flag = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_S && flag == 2) {
					flag = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_A && flag == 3) {
					flag = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_D && flag == 4) {
					flag = 0;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					flag = 1;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					flag = 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					flag = 3;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					flag = 4;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					for (int i = 0; i < gem.size(); i++) {
						if ((p.getX() + 15) / TILESIZE == gem.get(i).getxtile()
								&& (p.getY() + 17) / TILESIZE == gem.get(i).getytile()) {
							gem.remove(i);
							score += 10;
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
		loadLevel(walls, g2);
		firstload = false;
		for (Gem z : gem) {
			z.draw(g2);
		}
		for (Enemy z : enemies) {
			z.draw(g2);
		}
		p.draw(g2);
		g2.setColor(Color.WHITE);
		Font fon = new Font("Arial", Font.BOLD, 15);
		g2.setFont(fon);

		// score and lives
		g2.drawString("Score: " + score, 10, 22);
		g2.drawString("Lives: " + p.getHp(), 100, 22);
		g2.drawString("W A S D to move,   ↓ to pick up gems", 180, 585);

		// game over

	}

	private void loadLevel(int[][] walls, Graphics2D g2) {
		Rectangle f = new Rectangle(30, 30);
		Color path = new Color(130, 245, 134);
		// TODO: draw based on model state
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {

				f.x = j * TILESIZE;
				f.y = i * TILESIZE;

				if (walls[i][j] == 0) {
					if (wallimage != null) {
						g2.drawImage(wallimage, f.x, f.y, TILESIZE, TILESIZE, null);

					}
				} else if (walls[i][j] == 1) {
					g2.setColor(path);
//					
					g2.fill(f);

				} else if (walls[i][j] == 2) {
					g2.setColor(path);

					g2.fill(f);
					if (firstload) {
						gem.add(new Gem(j, i));
					}

				}

				else if (walls[i][j] == 3) {
					g2.setColor(path);

					g2.fill(f);
					if (firstload) {
						p.setPosition(j * 30, i * 30);
					}

				}

				else if (walls[i][j] == 4) {
					g2.setColor(path);

					g2.fill(f);

					if (firstload) {
						Enemy z = new Enemy(j * TILESIZE, i * TILESIZE);

						int id = enemies.size(); // 0,1,2,... as they spawn

						if (walls == this.walls) { // level 1 active
							assignLevel1Route(z, id);
						} else { // level 2 active
							assignLevel2Route(z, id);
						}

						enemies.add(z);
					}

				} else if (walls[i][j] == 5) {

					if (doorimage != null) {
						g2.drawImage(doorimage, f.x, f.y, TILESIZE, TILESIZE, null);

					}

				}

			}
		}

	}

	public void restart() {
		if (p.getHp() == 0) {
			score = 0;
		}
		p.setHp(3);
		enemies.clear();
		gem.clear();
		firstload = true;
		repaint();

	}

	public void checkWin() {
		if (gem.size() == 0 && walls[(p.getY() + 10) / 30][(p.getX() + 10) / 30] == 5&& level2!=walls) {
			walls = level2;
			restart();
		}
	}

	private void assignLevel1Route(Enemy z, int id) {
		if (id % 3 == 0) {
			// Z0 start (6,7) -> end (11,5) is a WALL, so choose ONE of these:
			// Option A: end at (10,5)
			z.setPacePathTiles(TILESIZE,
					new int[] { 6, 6, 6, 5, 4, 3, 3, 3, 2, 2, 2, 2, 3, 4, 4, 4, 5, 6, 7, 7, 7, 8, 9, 10, 10 },
					new int[] { 7, 8, 9, 9, 9, 9, 8, 7, 7, 6, 5, 4, 4, 4, 3, 2, 2, 2, 2, 3, 4, 4, 4, 4, 5 });

			// Option B: end at (12,5) (comment out Option A and use this instead)
			/*
			 * z.setPacePathTiles(TILESIZE, new int[]
			 * {6,6,6,5,4,3,3,3,2,2,2,2,3,4,4,4,5,6,7,8,9,10,11,12,12,12,12}, new int[]
			 * {7,8,9,9,9,9,8,7,7,6,5,4,4,4,3,2,2,2,2,2,2,2,2,2,3,4,5} );
			 */
		} else if (id % 3 == 1) {
			// Z1: start (1,17) -> end (14,11)
			z.setPacePathTiles(TILESIZE, new int[] { 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7, 7, 8, 9, 10, 11, 12, 13, 14, 14 },
					new int[] { 17, 17, 17, 17, 17, 17, 17, 16, 15, 14, 13, 12, 12, 12, 12, 12, 12, 12, 12, 11 });
		} else {
			// Z2: start (12,18) -> end (11,9)
			z.setPacePathTiles(TILESIZE,
					new int[] { 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 15, 14, 13, 12, 11, 11 },
					new int[] { 18, 18, 18, 18, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 8, 8, 8, 8, 8, 9 });
		}
	}

	private void assignLevel2Route(Enemy z, int id) {
		if (id % 4 == 0) {
			// Z0 spawn (10,1)
			z.setPacePathTiles(TILESIZE,
					new int[] { 10, 11, 10, 10, 10, 10, 10, 11, 11, 10, 9, 8, 7, 6, 6, 6, 7, 8, 9, 10, 10, 10, 10, 10,
							10 },
					new int[] { 1, 1, 1, 2, 3, 4, 5, 5, 4, 4, 4, 4, 4, 5, 4, 3, 3, 3, 3, 3, 4, 5, 4, 3, 2 });

		} else if (id % 4 == 1) {
			// Z1 spawn (13,5)
			z.setPacePathTiles(TILESIZE,
					new int[] { 13, 14, 14, 14, 15, 16, 17, 18, 18, 18, 18, 18, 17, 16, 15, 14, 14, 14, 13, 12, 11, 10,
							9, 9, 9, 10, 11, 12, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 15, 16, 17, 18, 18, 17, 16,
							15, 14, 13, 13, 13, 13, 13, 13, 13, 14, 15, 16, 17, 18, 18, 17, 16, 15, 14, 13, 13, 13, 13,
							13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13 },
					new int[] { 5, 5, 6, 7, 7, 7, 7, 7, 8, 9, 10, 9, 9, 9, 9, 9, 8, 7, 7, 7, 7, 7, 7, 8, 9, 9, 9, 9, 9,
							8, 7, 6, 5, 4, 3, 3, 2, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 8, 8,
							8, 8, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5 });

		} else if (id % 4 == 2) {
			// Z2 spawn (18,9)
			z.setPacePathTiles(TILESIZE,
					new int[] { 18, 18, 17, 17, 17, 17, 18, 18, 18, 18, 17, 17, 16, 15, 14, 13, 13, 13, 13, 13, 13, 13,
							14, 15, 16, 17, 18, 17, 16, 15, 14, 13, 14, 15, 16, 17, 18, 18, 18 },
					new int[] { 9, 10, 10, 11, 12, 13, 13, 14, 15, 14, 14, 15, 15, 15, 15, 15, 14, 13, 12, 11, 10, 9, 9,
							9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 11 });

		} else {
			// Z3 spawn (5,16)
			z.setPacePathTiles(TILESIZE,
					new int[] { 5, 6, 7, 8, 9, 9, 9, 10, 11, 11, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 2, 3, 4, 5, 6,
							7, 8, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 8, 7, 6, 5 },
					new int[] { 16, 16, 16, 16, 16, 17, 18, 18, 18, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17,
							18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 18, 18, 18, 18,
							18, 18, 18, 18, 17, 16, 16, 16, 16, 16 });
		}
	}

}
