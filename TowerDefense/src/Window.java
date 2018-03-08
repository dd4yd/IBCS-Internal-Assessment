import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Window extends JPanel implements Runnable {
	public Thread thread = new Thread(this);

	public static Image[] tileset_ground = new Image[100];
	public static Image[] tileset_air = new Image[100];
	public static Image[] tileset_winblock = new Image[100];
	public static Image[] cell = new Image[100];
	public static Image[] enemy = new Image[100];

	public static boolean isFirst = true;
	public static boolean debug = false;
	//public static boolean winning = false;

	public static int myWidth, myHeight;
	
	public static int killed = 0, killsToWin = 10, lvl =1, maxLvl = 3;

	public static int coins = 10, health = 15;

	public static Point mse = new Point(0, 0);

	public static Room room;

	public static Save save;

	public static Store store;

	public static Enemy[] enemies = new Enemy[50];

	public Window(Frame frame) {
		frame.addMouseListener(new KeyHandle());
		frame.addMouseMotionListener(new KeyHandle());
		
		thread.start();
		enemySpawner();
	}
	
//	public static void hasWon(){
//		if(killed == killsToWin){
//			winning = true;
//			killed = 0;
//		}
//	}

	public void define() {
		room = new Room();
		save = new Save();
		store = new Store();

		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy();
		}

		for (int i = 0; i < tileset_ground.length; i++) {
			tileset_ground[i] = new ImageIcon("res/tileset_ground.png")
					.getImage();
			tileset_ground[i] = createImage(new FilteredImageSource(
					tileset_ground[i].getSource(), new CropImageFilter(0,
							26 * i, 26, 26)));
		}
		for (int i = 0; i < tileset_air.length; i++) {
			tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(
					tileset_air[i].getSource(), new CropImageFilter(0, 26 * i,
							26, 26)));
		}
		for (int i = 0; i < tileset_winblock.length; i++) {
			tileset_winblock[i] = new ImageIcon("res/tileset_winblock.png")
					.getImage();
			tileset_winblock[i] = createImage(new FilteredImageSource(
					tileset_winblock[i].getSource(), new CropImageFilter(0,
							26 * i, 26, 26)));
		}

		cell[0] = new ImageIcon("res/cell.png").getImage(); // reads in shop
															// button image
		cell[1] = new ImageIcon("res/heart.png").getImage();
		cell[2] = new ImageIcon("res/coin.png").getImage();
		for(int i =0; i < 100; i++){
			enemy[i] = new ImageIcon("res/enemy.png").getImage();
		}

		save.loadSave(new File("save/mission1.david"));
		//save.loadSave(new File("save/mission1" + lvl +".david"));
	}

	public void paintComponent(Graphics g) {
		if (isFirst) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();
			isFirst = false;

		}
		g.setColor(new Color(172, 197, 250));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0, 0, 0));
		g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 2,
				room.block[room.worldHeight - 1][0].y + room.blockSize); // draw
																			// left
																			// line
		g.drawLine(room.block[0][0].x - 1, 0,
				room.block[0][room.worldWidth - 1].x + room.blockSize,
				room.block[room.worldHeight - 1][0].y + room.blockSize); // draw
																			// right
																			// line

		room.draw(g); // Drawing the room

		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i].inGame) {
				enemies[i].draw(g);
			}
		}
		
		
		store.draw(g); // drawing the store
		if (health < 1) {
			g.setColor(new Color(0, 255, 238));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Century Gothic", Font.BOLD, 30));
			g.drawString("GAME OVER", 250, 250);
			g.setFont(new Font("Century Gothic", Font.BOLD, 15));
			g.drawString("sorry ur bad lol",280,280);
		}
		if(killed==killsToWin){
		    g.setColor(new Color(0,255,238));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color(255, 255, 255));
		    g.setFont(new Font("Century Gothic", Font.BOLD, 20));
		    g.drawString("You won!, window will now exit", 200, 250);
		   /* try{
		    	BufferedImage tmp = ImageIO.read(new File("res/wolfard.png"));
		    	g.drawImage(tmp, 200, 90, 90, 90,null);
		    }
		    catch(Exception e){
		    	
		    }*/
		    try {
				Timer t = new Timer();
				t.schedule(new EndGame(),4000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class EndGame extends TimerTask{
		public void run() {
			System.exit(0);
		}
	}
	
	/*public void endgame(long timeout, TimeUnit unit) throws InterruptedException{
		timeout  = 2000;
		unit.timedWait(this, timeout);
		System.exit(0);
	}*/
	
	public int spawnCount = 0;

	public void enemySpawner() {
		Timer timer = new Timer();
		TimerListener tl = new TimerListener();
		timer.schedule(tl, 500, 2400);
	}

	private class TimerListener extends TimerTask {
		public void run() {
			if(spawnCount<enemies.length){
				enemies[spawnCount] = new Enemy();
				enemies[spawnCount].spawnEnemies(spawnCount);

				spawnCount++;
			}
		}
	}

	public void run() {
		while (true) {
			if (!isFirst && health > 0) {
				room.physic();
				for (int i = 0; i < enemies.length; i++) {
					if (enemies[i].inGame) {
						enemies[i].physic();
					}
				}
			}
			else{
/*				if(winning){
					if(winFrame > winTime){
						if(lvl> maxLvl){
							System.exit(0);
						}
						else{
							lvl+=1;
							save.loadSave(new File("save/mission1" + lvl +".david"));
							winning = false;
						}
						winFrame = 0;
					}
					else winFrame+= 1;
				}
*/			}

			repaint();

			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
	}
}
