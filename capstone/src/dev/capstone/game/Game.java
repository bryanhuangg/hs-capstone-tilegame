package dev.capstone.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import dev.capstone.game.display.Display;
import dev.capstone.game.gfx.FontLoader;
import dev.capstone.game.gfx.ImageLoader;

public class Game implements Runnable
{
	
	//Preemptive Variables
	private Display display;
	public int width, height;
	public String title;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public static Font font28;
	public static Font font30;
	
	//Game Variables
	public static BufferedImage baseImage, gameOver, youWin, player, player1, player2, fplayer, fplayer1, fplayer2, crate, coin1, coin2, coin3;
	boolean turn = false;
	
	int[][] grid = new int[6][6];
	int moves;
	
	Object p1 = new Object(1,6);
	Object[] crates = new Object[14];
	Object win = new Object(6,2);
	
	private int aCount = 0;
	private boolean rightFacing = true; 
	
	private KeyManager keyManager;

	//Methods
	public Game(String title, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
	}

	
	private void init()
	{
		moves = 18;
		crates[0] = new Object(3,1);
		crates[1] = new Object(5,1);
		crates[2] = new Object(2,2);
		crates[3] = new Object(3,2);
		crates[4] = new Object(5,2);
		crates[5] = new Object(2,3);
		crates[6] = new Object(4,3);
		crates[7] = new Object(6,3);
		crates[8] = new Object(3,4);
		crates[9] = new Object(5,4);
		crates[10] = new Object(1,5);
		crates[11] = new Object(2,5);
		crates[12] = new Object(4,5);
		crates[13] = new Object(5,6);

		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		font28 = FontLoader.loadFont("res/fonts/redo.ttf", 28);
		font30 = FontLoader.loadFont("res/fonts/redo.ttf", 30);
		
		player = ImageLoader.loadImage("res/textures/player.png");
		player1 = ImageLoader.loadImage("res/textures/player1.png");
		player2 = ImageLoader.loadImage("res/textures/player2.png");
		fplayer = ImageLoader.loadImage("res/textures/fplayer.png");
		fplayer1 = ImageLoader.loadImage("res/textures/fplayer1.png");
		fplayer2 = ImageLoader.loadImage("res/textures/fplayer2.png");
		crate = ImageLoader.loadImage("res/textures/crate.png");
		coin1 = ImageLoader.loadImage("res/textures/coin1.png");
		coin2 = ImageLoader.loadImage("res/textures/coin2.png");
		coin3 = ImageLoader.loadImage("res/textures/coin3.png");
		baseImage = ImageLoader.loadImage("res/textures/base.png");
		gameOver = ImageLoader.loadImage("res/textures/gameover.png");
		youWin = ImageLoader.loadImage("res/textures/win.png");
	}

	
	private void tick() {
		
		keyManager.tick();
		
		//Movement Mechanics
		
		if(getKeyManager().up && !turn)
		{
			if(p1.getY()>1) 
			{
				if(grid[p1.getY()-2][p1.getX()-1] < 9)
				{
					p1.setY(p1.getY()-1);
				}
				else if (p1.getY()>2 && grid[p1.getY()-3][p1.getX()-1] < 5)
				{
					int num = grid[p1.getY()-2][p1.getX()-1] / 10 - 1;
					crates[num].setY(crates[num].getY() - 1);
				}
				else
				{
					
				}
				moves--;
			}
				
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(getKeyManager().down)
		{
			if(p1.getY()<6) 
			{
				if(grid[p1.getY()][p1.getX()-1] < 9)
				{
					p1.setY(p1.getY()+1);
				}
				
				else if (p1.getY()<5 && grid[p1.getY()+1][p1.getX()-1] < 5)
				{
					int num = grid[p1.getY()][p1.getX()-1] / 10 - 1;
					crates[num].setY(crates[num].getY() + 1);
				}
				else
				{
					
				}
				moves--;
			}
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(getKeyManager().left)
		{
			if(p1.getX()>1) 
			{	
				if(grid[p1.getY()-1][p1.getX()-2] < 9)
				{
					p1.setX(p1.getX()-1);
				}
				else if (p1.getX()>2 && grid[p1.getY()-1][p1.getX()-3] < 5)
				{
					int num = grid[p1.getY()-1][p1.getX()-2] / 10 - 1;
					crates[num].setX(crates[num].getX() - 1);
				}
				else
				{
					
				}
				rightFacing = false;
				moves--;
			}
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {	
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(getKeyManager().right)
		{
			if(p1.getX()<6) 
			{
				
				if(grid[p1.getY()-1][p1.getX()] < 9)
				{
					p1.setX(p1.getX() + 1);
				}
				else if (p1.getX()<5 && grid[p1.getY()-1][p1.getX()+1] < 5)
				{
					int num = grid[p1.getY()-1][p1.getX()] / 10 - 1;
					crates[num].setX(crates[num].getX() + 1);
				}
				else
				{
					
				}
				rightFacing = true;
				moves--; 
			}
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear
		g.clearRect(0,0,width,height);
		//Render Start
		g.drawImage(baseImage,0,0,null);
		//crates
		g.drawImage(crate,crates[0].getX() * 100 + 15 ,crates[0].getY()*100 + 140,null);
		g.drawImage(crate,crates[1].getX() * 100 + 15 ,crates[1].getY()*100 + 140,null);
		g.drawImage(crate,crates[2].getX() * 100 + 15 ,crates[2].getY()*100 + 140,null);
		g.drawImage(crate,crates[3].getX() * 100 + 15 ,crates[3].getY()*100 + 140,null);
		g.drawImage(crate,crates[4].getX() * 100 + 15 ,crates[4].getY()*100 + 140,null);
		g.drawImage(crate,crates[5].getX() * 100 + 15 ,crates[5].getY()*100 + 140,null);
		g.drawImage(crate,crates[6].getX() * 100 + 15 ,crates[6].getY()*100 + 140,null);
		g.drawImage(crate,crates[7].getX() * 100 + 15 ,crates[7].getY()*100 + 140,null);
		g.drawImage(crate,crates[8].getX() * 100 + 15 ,crates[8].getY()*100 + 140,null);
		g.drawImage(crate,crates[9].getX() * 100 + 15 ,crates[9].getY()*100 + 140,null);
		g.drawImage(crate,crates[10].getX() * 100 + 15 ,crates[10].getY()*100 + 140,null);
		g.drawImage(crate,crates[11].getX() * 100 + 15 ,crates[11].getY()*100 + 140,null);
		g.drawImage(crate,crates[12].getX() * 100 + 15 ,crates[12].getY()*100 + 140,null);
		g.drawImage(crate,crates[13].getX() * 100 + 15 ,crates[13].getY()*100 + 140,null);
		
		if(aCount == 0) 
		{
			g.drawImage(coin1,win.getX() * 100 + 10 ,win.getY()*100 + 150,null);
			if(rightFacing)
			{
				g.drawImage(player,p1.getX() * 100 + 10 ,p1.getY()*100 + 140,null);
			}
			else
			{
				g.drawImage(fplayer,p1.getX() * 100 + 10 ,p1.getY()*100 + 140,null);
			}
			
			g.setFont(font30);
		} 
		else if (aCount == 1)
		{
			g.drawImage(coin1,win.getX() * 100 + 10 ,win.getY()*100 + 150,null);
			if(rightFacing)
			{
				g.drawImage(player1,p1.getX() * 100 + 10 ,p1.getY()*100 + 140,null);
			}
			else
			{
				g.drawImage(fplayer1,p1.getX() * 100 + 10 ,p1.getY()*100 + 140,null);
			}
			g.setFont(font28);
		}
		else
		{
			g.drawImage(coin3,win.getX() * 100 + 10 ,win.getY()*100 + 150,null);
			if(rightFacing)
			{
				g.drawImage(player2,p1.getX() * 100 + 10 ,p1.getY()*100 + 140,null);
			}
			else
			{
				g.drawImage(fplayer2,p1.getX() * 100 + 10 ,p1.getY()*100 + 140,null);
			}
			g.setFont(font28);
		}
				
		if(moves>5)
		{
			g.setColor(Color.ORANGE);
		}
		else
		{
			g.setColor(new Color(207, 93, 93));
		}
		FontMetrics fm = g.getFontMetrics();
		String counter = String.valueOf(moves);
		g.drawString(counter, 425 - fm.stringWidth(counter)/2, (75-fm.getHeight()/2)+fm.getAscent());
		
		if(moves <= 0)
		{
			g.drawImage(gameOver,0,0,null);
		}
		
		if(win.getX()==p1.getX() && win.getY()==p1.getY())
		{
			g.drawImage(youWin,0,0,null);
		}
		
		//Render End
		bs.show();
		g.dispose();
		
	}
	
	
	public void run() 
	{
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime)/timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if (delta >= 1)
			{
				tick();
				
				for (int row = 0; row < grid.length; row++) 
				{
					for (int col = 0; col < grid.length; col++) 
					{
						grid[row][col] = 0;
					}
				}
				
				grid[p1.getY()-1][p1.getX()-1]= 1;
				grid[crates[0].getY()-1][crates[0].getX()-1]= 10;
				grid[crates[1].getY()-1][crates[1].getX()-1]= 20;
				grid[crates[2].getY()-1][crates[2].getX()-1]= 30;
				grid[crates[3].getY()-1][crates[3].getX()-1]= 40;
				grid[crates[4].getY()-1][crates[4].getX()-1]= 50;
				grid[crates[5].getY()-1][crates[5].getX()-1]= 60;
				grid[crates[6].getY()-1][crates[6].getX()-1]= 70;
				grid[crates[7].getY()-1][crates[7].getX()-1]= 80;
				grid[crates[8].getY()-1][crates[8].getX()-1]= 90;
				grid[crates[9].getY()-1][crates[9].getX()-1]= 100;
				grid[crates[10].getY()-1][crates[10].getX()-1]= 110;
				grid[crates[11].getY()-1][crates[11].getX()-1]= 120;
				grid[crates[12].getY()-1][crates[12].getX()-1]= 130;
				grid[crates[13].getY()-1][crates[13].getX()-1]= 140;
				grid[win.getY()-1][win.getX()-1]= 8;
				
			
				render();
				
				//win and loss code stopper
				if(moves <= 0 || (win.getX()==p1.getX() && win.getY()==p1.getY()))
				{
					stop();
				}
				
				delta--;
			}
			
			if (timer>= 400000000)
			{
				for (int i = 0; i < grid.length; i++)
				System.out.println(Arrays.toString(grid[i]));
				System.out.println("");
				
				timer = 0;
				++aCount;
				if(aCount>=3)
					aCount = 0;

			}
		}
		stop();
	}
	
	
	public KeyManager getKeyManager()
	{
		return keyManager;
	}

	
	//Start--Stop
	public synchronized void start()
	{
		if(running) 
		{
			return;
		}
			
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop()
	{
		if(!running)
		{
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
