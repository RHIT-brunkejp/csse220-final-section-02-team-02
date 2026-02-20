package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Point;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/** Enemy that moves automatically and cannot pass through walls. */
public class Enemy {
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	private int x;
	private int y;
	private int hp;
	private int speed = 2;
	private ArrayList<Point> path = new ArrayList<>(); // pixel waypoints
	private int pathIndex = 0;
	private int pathDir = 1; // +1 forward, -1 backward
	private int arriveDist = 2;
	// match your draw size so collision is accurate
	private int enemyWidth = 20;
	private int enemyHeight = 28;

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

	public void updateEnemy(int[][] walls, int tileSize) {
		if (path.isEmpty())
			return;
		Point target = path.get(pathIndex);
		int tx = target.x;
		int ty = target.y;
		int dx = tx - x;
		int dy = ty - y;
		// Arrived at waypoint → advance index and reverse at ends
		if (Math.abs(dx) <= arriveDist && Math.abs(dy) <= arriveDist) {
			pathIndex += pathDir;
			if (pathIndex >= path.size()) {
				pathIndex = path.size() - 1;
				pathDir = -1;
				pathIndex += pathDir;
			} else if (pathIndex < 0) {
				pathIndex = 0;
				pathDir = 1;
				pathIndex += pathDir;
			}
			return;
		}
		int stepX = 0;
		int stepY = 0;
		if (dx > 0)
			stepX = speed;
		else if (dx < 0)
			stepX = -speed;
		if (dy > 0)
			stepY = speed;
		else if (dy < 0)
			stepY = -speed;
		boolean moved = false;
		// Move along dominant axis first (clean corridor movement)
		if (Math.abs(dx) >= Math.abs(dy)) {
			if (stepX != 0 && canMove(walls, tileSize, x + stepX, y)) {
				x += stepX;
				moved = true;
			} else if (stepY != 0 && canMove(walls, tileSize, x, y + stepY)) {
				y += stepY;
				moved = true;
			}
		} else {
			if (stepY != 0 && canMove(walls, tileSize, x, y + stepY)) {
				y += stepY;
				moved = true;
			} else if (stepX != 0 && canMove(walls, tileSize, x + stepX, y)) {
				x += stepX;
				moved = true;
			}
		}
		// If blocked at tight corner, skip to next waypoint
		if (!moved) {
			pathIndex += pathDir;
			if (pathIndex >= path.size()) {
				pathIndex = path.size() - 1;
				pathDir = -1;
			}
			if (pathIndex < 0) {
				pathIndex = 0;
				pathDir = 1;
			}
		}
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

	public void setPacePathTiles(int tileSize, int[] cols, int[] rows) {
		path.clear();
		// Keep your +1 alignment
		int xOff = (tileSize - enemyWidth) / 2 + 1;
		int yOff = (tileSize - enemyHeight) / 2 + 1;
		for (int i = 0; i < cols.length && i < rows.length; i++) {
			int px = cols[i] * tileSize + xOff;
			int py = rows[i] * tileSize + yOff;
			path.add(new Point(px, py));
		}
		pathIndex = 0;
		pathDir = 1;
	}
}
