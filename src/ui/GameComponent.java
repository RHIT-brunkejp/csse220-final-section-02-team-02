package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.Timer;

import entities.Enemy;
import entities.Player;
import model.GameModel;

public class GameComponent extends JComponent {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	private GameModel model;
	private Timer timer;
	
	private Player p = new Player();
	
	private Enemy e1 = new Enemy(250, 230);
	private Enemy e2 = new Enemy(30, 530);
	
	// USED A 2D MATRIX TO PORTRAY THE MAZE WITH EACH INDEX REPRESENTING A 30 x 30 PIXEL AREA
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
	
	public GameComponent(GameModel model) {
	this.model = model;
	
	
	// TIMER TO UPDATE ENEMY POSITIONS ALONG WITH REPAINTING THE SPRITES
    timer = new Timer(20, e -> {
    	p.update(WIDTH, HEIGHT);
    	e1.updateEnemy(walls, 30);
    	e2.updateEnemy(walls, 30);
    	repaint();
  	});
  	timer.start();
  	
  	
  	// ACTION LISTENER FOR PLAYER MOVEMENT
  	setFocusable(true);
  	  addKeyListener(new KeyAdapter() {
  		  @Override
  		  public void keyPressed(KeyEvent e) {
  		    if (e.getKeyCode() == KeyEvent.VK_W) {
  		    	if(p.canMove(walls, 30, p.getX(), p.getY()-2)) {
  		    		p.up();
  		    	}
  		    }
  		    if(e.getKeyCode() == KeyEvent.VK_S) {
  		    	if(p.canMove(walls, 30, p.getX(), p.getY()+2)) {
  		    		p.down();
  		    	}
  		    }
  		    if(e.getKeyCode() == KeyEvent.VK_A) {
  		    	if(p.canMove(walls, 30, p.getX()-2, p.getY())) {
  		    		p.left();
  		    	}
  		    }
  		    if(e.getKeyCode() == KeyEvent.VK_D) {
  		    	if(	p.canMove(walls, 30, p.getX()+2, p.getY())) {
  		    		p.right();	
  		    	}
  		    }
  		    repaint();
  		  }});
	}
	
	


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;

	// Minimal placeholder to test  it’s running
	g2.drawString("Final Project Starter: UI is running ✅", 20, 30);
	g2.draw(new Rectangle(30,30));
	Rectangle f = new Rectangle(30,30);
	
	


	// TODO: draw based on model state

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
	
		
	

