package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy {
	private static BufferedImage sprite = null;
	int x;
	int y;
	int hp;
	public Enemy(int sx, int sy){
	x=sx;
	y=sy;
	hp=3;
	}
	public void draw(Graphics2D g2) {
		
		if (sprite != null) {
		// sprite replaces the circle
		g2.drawImage(sprite, x, y, 20, 20, null);
		} else {
		// fallback if sprite failed to load
		g2.setColor(Color.RED);
		g2.fillRect(x, y, 20, 20);
		}
	}
}
