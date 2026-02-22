package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Player implements HumanoidEntity {
	int x;
	int y;
	int hp;
	int speed;
	int playerWidth = 20;
	int playerHeight = 20;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	public Player() {
		x = 290;
		y = 530;
		hp = 3;
		speed = 2;
		loadSpriteOnce();
	}
	/**
	 * checks to make sure that the player is not escaping the frame of play
	 * @param worldWidth the width of the world
	 * @param worldHeight the height of the world
	 */
 public void update(int worldWidth, int worldHeight) {
		if (x < 0) {
			x = 0;
		} else if (x + playerWidth > worldWidth) {
			x = worldWidth - playerWidth;
		}
		if (y < 0) {
			y = 0;
		} else if (y + playerHeight > worldHeight) {
			y = worldHeight - playerHeight;
		}
	}

	private static void loadSpriteOnce() {
		//tries to load sprite
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite = ImageIO.read(Enemy.class.getResource("player.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	public void draw(Graphics2D g2) {

		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, x, y, 30, 35, null);
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.BLUE);
			g2.fillRect(x, y, 20, 20);
		}
	}

	// PLAYER MOVEMENT COMMANDS
	public void left() {
		x -= speed;
	}

	public void right() {
		x += speed;
	}

	public void up() {
		y -= speed;
	}

	public void down() {
		y += speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

/**
 * this method checks if the player is able to move in the inputed direction
 * @param walls the current map layout
 * @param tileSize the size of the tiles
 * @param x2 the projected position x
 * @param y2 the projected position y
 * @return a true if he can move, false if not
 */
	public boolean canMove(int[][] walls, int tileSize, int x2, int y2) {
		int leftTile = x2 / tileSize;
		int rightTile = (x2 + tileSize - 1) / tileSize;
		int topTile = y2 / tileSize;
		int bottomTile = (y2 + tileSize - 1) / tileSize;
		if (walls[topTile][leftTile] == 0 || walls[topTile][rightTile] == 0 || walls[bottomTile][leftTile] == 0
				|| walls[bottomTile][rightTile] == 0) {
			return false;
		}
		return true;
	}
    //returns hp
	public int getHp() {
		return hp;
	}
    //decreases the life by 1
	public void loseLife() {
		if (hp > 0)
			hp--;
	}
	//checks if the player has no hp
	public boolean noHp() {
		return hp == 0;
	}
	//sets the player's hp to the parameter's number
	public void setHp(int hp) {
		this.hp = hp;
	}
	//sets the player's position to the parameters x and y
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	// Get Rectangle Area Coordinates
	public Rectangle getBounds() {
		return new Rectangle(x, y, playerWidth, playerHeight);
	}

}
