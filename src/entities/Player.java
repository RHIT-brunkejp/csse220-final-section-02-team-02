package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
int x;
int y;
int hp;
private static BufferedImage sprite = null;
private static boolean triedLoad = false;
	 public Player() {
		 x = 290;
		 y=530;
		 hp=3;
		 loadSpriteOnce();
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
}
