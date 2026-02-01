package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player {
int x;
int y;
int hp;
private static BufferedImage sprite = null;
	 public Player() {
		 x = 290;
		 y=540;
		 hp=3;
	 }
	 public void draw(Graphics2D g2) {
			
			if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, x, y, 20, 20, null);
			} else {
			// fallback if sprite failed to load
			g2.setColor(Color.BLUE);
			g2.fillRect(x, y, 20, 20);
			}
		}
}
