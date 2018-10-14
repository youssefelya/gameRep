package brikBrak;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;

public class Gameplay extends JPanel implements KeyListener,ActionListener {
       private boolean play=false;
       private int score=0;
       private int totalBricks=21;
       private Timer time;
       private int delay=8;
       private int playerX=310;
       private int ballposX=120;
       private int ballpoY=350;
       private int ballXdir=-1;
       private int ballYdir=-2;
       private MapGenerator map;
       
       public Gameplay() {
    	   map=new MapGenerator(5,7);
    	   addKeyListener(this);
    	   setFocusable(true);
    	   setFocusTraversalKeysEnabled(false);
    	   time=new Timer(delay,this);
    	   time.start();
       }
       public void paint(Graphics g) {
    	 
    	   g.setColor(Color.BLACK);
    	   g.fillRect(1, 1, 692, 592);
    	   map.draw((Graphics2D)g);
    	   g.setColor(Color.yellow);
    	   g.fillRect(0, 0, 3, 592);
    	   g.fillRect(0, 0, 3692, 3);
    	   g.fillRect(691, 0, 3, 592);
    	   
    	   
    	   g.setColor(Color.green);
    	   g.fillRect(playerX, 550, 100, 8);
    	   
    	  //Ball
    	   g.setColor(Color.YELLOW);
    	   g.fillOval(ballposX, ballpoY,20 , 20);
    	   g.dispose();
    	   
       }
       
       
       
       
       public void keyReleased(KeyEvent e) {
    	   if(e.getKeyCode()==KeyEvent.VK_RIGHT){
    		  if(playerX>=600) {playerX=600; } 
    		  else {moveRight(); }
    	   }
    	   if(e.getKeyCode()==KeyEvent.VK_LEFT) {
    		   if(playerX<10) {playerX=10; }
    		   else {moveLeft(); }
    		     }
       }
       
       public void moveRight() {
    	   play=true;
    	   playerX+=20;
    	   
       }
       public void moveLeft() {
    	   play=true;
    	   playerX-=20;
    	   
       }
	
	public void actionPerformed(ActionEvent e) {
		time.start();
		if(play) {
			if(new Rectangle(ballposX,ballpoY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
			ballYdir=-ballYdir;	
			}
			A:for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickwidth+80;
						int brickY=i*map.brickHeight+50;
						int brickwidth=map.brickwidth;
						int brickHright=map.brickHeight;
						Rectangle rect=new Rectangle(brickX,brickY,brickwidth,brickHright);
						Rectangle ballRect=new Rectangle(ballposX,ballpoY,20,20);
						Rectangle brickRect=rect;
						if(ballRect.intersects(brickRect)) {
						map.setBrickvalue(0,i,j);
						totalBricks--;
						score+=5;
						if(ballposX+19<=brickRect.x|| ballposX+1>=brickRect.x) {
							ballXdir=-ballXdir;
						}else {
							ballYdir=-ballYdir;
						}
						break A;
						}
						
					}
				}
			}
			
			
			
			ballposX+=ballXdir;
			ballpoY+=ballYdir;
			if(ballposX<0) { ballXdir=-ballXdir; }
			if(ballpoY<0) { ballYdir=-ballYdir; }
			if(ballposX>670) {ballXdir=-ballXdir; }
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) { }
 
	

	@Override
	public void keyTyped(KeyEvent e) { }

}
