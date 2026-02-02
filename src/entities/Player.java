package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;

public class Player {
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
		 y=530;
		 hp=3;
		 speed = 2;
		 loadSpriteOnce();
	 }
	 public void update(int worldWidth, int worldHeight) {
		 if(x < 0) {
			 x = 0;
		 }
		 else if(x + playerWidth > worldWidth) {
			 x = worldWidth - playerWidth;
		 }
		 if(y < 0) {
			 y = 0;
		 }
		 else if(y + playerHeight > worldHeight) {
			 y= worldHeight-playerHeight;
		 }
	 }
	 private static void loadSpriteOnce() {
			if (triedLoad) return;
			triedLoad = true;

			try {
			// tennis.png must be in the SAME package as Ball.java
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
}
