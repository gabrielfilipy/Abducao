package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;

import main.Game;
import world.Camera;

public class Entity {
	
	public static BufferedImage[] PLAYER_SPRITE_LEFT = {
			Game.sprite.getSprite(32,0,16,16),
			Game.sprite.getSprite(48,0,16,16),
			Game.sprite.getSprite(64,0,16,16),
			Game.sprite.getSprite(80,0,16,16),
			Game.sprite.getSprite(96,0,16,16),
			};
	
	public static BufferedImage[] PLAYER_SPRITE_RIGHT = {
			Game.sprite.getSprite(32,15,16,16),
			Game.sprite.getSprite(48,15,16,16)
			};
	
	protected double x, y;
	protected int width, height;
	protected double speed;
	public int depth;
	protected BufferedImage sprite;
	public int maskx, masky, mwidth, mheight;

	public Entity(int x, int y, int width, int height, double speed, 
			BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx,int masky,int mwidth,int mheight){
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public int getX() {
		return (int)x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
	}
	
}
