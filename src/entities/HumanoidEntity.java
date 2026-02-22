package entities;

import java.awt.Graphics2D;

public interface HumanoidEntity {
 public int getX();//returns x value
 public int getY();//returns y value
 public boolean canMove(int[][] walls, int tileSize, int x2, int y2); //checks if the entity can move
 public void draw(Graphics2D g2);//draws on the entity
 
}
