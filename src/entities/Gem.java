package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gem {
	private int xloc;
	private int yloc;
	final int TILESIZE=30;
	int sidel = 20;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	//constructor that sets thye (x,y) position of the gem and tries to load sprite
	public Gem(int tilex, int tiley) {
		xloc = tilex;
		yloc=tiley;
		loadSpriteOnce();
	}
	//returns x value
	public int getxtile() {
		return xloc;
	}
	//returns y value
	public int getytile() {
		return yloc;
	}
	//function to try and load the gem sprite
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;

		try {
		
		sprite = ImageIO.read(Enemy.class.getResource("gem.png"));
		} catch (IOException | IllegalArgumentException ex) {
		sprite = null; 
		}
		}
	//function to draw the gem in
	public void draw(Graphics2D g2) {


		if (sprite != null ) {
		// sprite replaces the circle
		g2.drawImage(sprite, xloc*TILESIZE+10, yloc*TILESIZE+10, sidel, sidel, null);
		} else {
		// fallback if sprite failed to load
		g2.setColor(Color.YELLOW);
		g2.fillRect(xloc*TILESIZE+10, yloc*TILESIZE+10, sidel, sidel);
		}
	}
}
