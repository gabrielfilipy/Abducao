package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import entities.Entity;
import entities.Player;
import entities.Vaca;
import graficos.SpriteSheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener {
 
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 240, HEIGHT = 160, SCALE =3;
	public boolean isRunnig = true;
	private Thread thread;
	public static JFrame frame;
	
	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static SpriteSheet sprite;
	public static List<Entity> entites;
	public static Player player;
	public static World world;
	public UI ui;

	public Game() {
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrames();
		entites = new ArrayList<>();
		sprite = new SpriteSheet("/sprites.png");
		player = new Player(WIDTH/2 - 30,HEIGHT/2,16,16,1.4, Entity.PLAYER_SPRITE_RIGHT[1]);
		entites.add(player);
		world = new World("/map.png");
		ui = new UI();
	}
	
	public static void restartGame() {
		Game.entites.clear();
		Game.player = new Player(WIDTH/2 - 30,HEIGHT/2 , 16, 16, 1.4, Game.sprite.getSprite(0, 0, 16, 16));
		return;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	@Override
	public void run() { 
		long lastTime = System.nanoTime();
		double amountOfUpdate = 60.0;
		double ns = 1000000000 / amountOfUpdate;
		float d = 0; 
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunnig) {
			try {
				long now = System.nanoTime();
				d += (now - lastTime) / ns;
				lastTime = now;
				
				if(d >= 1) {
					update();
					render();
					frames++;
					d--;
				}
				
				if((System.currentTimeMillis() - timer) >= 100) {
					System.out.println(">> FPS: " + frames);
					frames = 0;
					timer += 1000;
				}
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update() { 
		for(int i = 0; i < entites.size(); i++) {
			Entity entity = entites.get(i);
			entity.update();
		}
	}
	
	public void render() { 
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = layer.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		world.render(g);
		
		for(int i = 0; i < entites.size(); i++) {
			Entity entity = entites.get(i);
			entity.render(g);
		}
		
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		bs.show();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunnig = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunnig = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void initFrames() {
		frame = new JFrame("#OVNI-ATACK");
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
	}
	
}
