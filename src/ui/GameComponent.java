package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

import entities.Enemy;
import entities.Player;
import model.GameModel;

public class GameComponent extends JComponent {

	
	
	private GameModel model;


	public GameComponent(GameModel model) {
	this.model = model;
	}
	
	


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	Enemy e1 =new Enemy(250,230);
	Enemy e2 =new Enemy(30,530);
	Player p = new Player();

	// Minimal placeholder to test  it’s running
	g2.drawString("Final Project Starter: UI is running ✅", 20, 30);
	g2.draw(new Rectangle(30,30));
	Rectangle f = new Rectangle(30,30);
	
	


	// TODO: draw based on model state
	int[][] walls = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
			{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,},
			{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,},
			{0,1,1,1,1,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,},
			{0,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,},
			{0,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,},
			{0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,},
			{0,1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,0,},
			{0,0,0,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,0,},
			{0,1,1,1,1,1,1,1,0,0,1,1,0,0,0,0,1,1,0,0,},
			{0,1,1,1,1,1,1,1,0,0,0,0,0,1,1,0,1,1,0,0,},
			{0,1,1,0,0,0,1,1,1,1,1,1,1,1,1,0,1,1,0,0,},
			{0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,0,0,},
			{0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,},
			{0,0,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,0,0,},
			{0,0,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,0,0,},
			{0,0,1,1,0,0,1,1,0,1,1,0,0,0,0,1,1,0,0,0,},
			{0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,0,0,},
			{0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,0,0,},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,}
			};
	for(int i=0;i<20;i++) {
		for(int j=0;j<20;j++) {
			if(walls[i][j]==1) {
				g2.setColor(new Color(130,245,134));
			}else {
			   g2.setColor(Color.BLACK);	
			}
			f.x=j*30;
			f.y=i*30;
			g2.draw(f);
			g2.fill(f);
		}
	}
	e1.draw(g2);
	e2.draw(g2);
	p.draw(g2);

	
	}
	public void addComponent(Rectangle r) {
	 
	}
	}
	
		
	


