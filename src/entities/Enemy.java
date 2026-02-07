package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

/** Enemy that moves automatically and cannot pass through walls. */
public class Enemy {
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	private int x;
	private int y;
	private int hp;

	private int speed = 2;

	// match your draw size so collision is accurate
	private int enemyWidth = 20;
	private int enemyHeight = 30;

	// direction: 0=up, 1=right, 2=down, 3=left
	private int dir = 1;

	public Enemy(int sx, int sy) {
		x = sx;
		y = sy;
		hp = 3;
		loadSpriteOnce();
	}

	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {
			sprite = ImageIO.read(Enemy.class.getResource("zombie2.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/** Same idea as Player.canMove, but uses enemy sprite size. */
	public boolean canMove(int[][] walls, int tileSize, int x2, int y2) {
		int leftTile = x2 / tileSize;
		int rightTile = (x2 + enemyWidth - 1) / tileSize;
		int topTile = y2 / tileSize;
		int bottomTile = (y2 + enemyHeight - 1) / tileSize;

		// bounds check
		if (topTile < 0 || bottomTile >= walls.length || leftTile < 0 || rightTile >= walls[0].length) {
			return false;
		}

		if (walls[topTile][leftTile] == 0 || walls[topTile][rightTile] == 0 || walls[bottomTile][leftTile] == 0
				|| walls[bottomTile][rightTile] == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Automatic movement: - tries to keep going forward - if blocked, turns right -
	 * if still blocked, turns left - if still blocked, turns around
	 */
	public void updateEnemy(int[][] walls, int tileSize) {
		int nx = x;
		int ny = y;

		// try forward
		int[] step = stepForDir(dir);
		nx = x + step[0] * speed;
		ny = y + step[1] * speed;

		if (canMove(walls, tileSize, nx, ny)) {
			x = nx;
			y = ny;
			return;
		}

		// try right
		int right = (dir + 1) % 4;
		step = stepForDir(right);
		nx = x + step[0] * speed;
		ny = y + step[1] * speed;

		if (canMove(walls, tileSize, nx, ny)) {
			dir = right;
			x = nx;
			y = ny;
			return;
		}

		// try left
		int left = (dir + 3) % 4;
		step = stepForDir(left);
		nx = x + step[0] * speed;
		ny = y + step[1] * speed;

		if (canMove(walls, tileSize, nx, ny)) {
			dir = left;
			x = nx;
			y = ny;
			return;
		}

		// try back
		dir = (dir + 2) % 4;
	}

	private int[] stepForDir(int d) {
		if (d == 0)
			return new int[] { 0, -1 }; // up
		if (d == 1)
			return new int[] { 1, 0 }; // right
		if (d == 2)
			return new int[] { 0, 1 }; // down
		return new int[] { -1, 0 }; // left
	}

	public void draw(Graphics2D g2) {
		if (sprite != null) {
			g2.drawImage(sprite, x, y, enemyWidth, enemyHeight, null);
		} else {
			g2.setColor(Color.RED);
			g2.fillRect(x, y, enemyWidth, enemyHeight);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, enemyWidth, enemyHeight);
	}
}
