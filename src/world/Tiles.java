package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

/*
 * CLASS PAI
 */

public class Tiles {

	public static BufferedImage TILE_FLOOR = Game.sprite.getSprite(0,0,16,16);
	public static BufferedImage TILE_WALL = Game.sprite.getSprite(16,0,16,16);

	private BufferedImage sprite;
	private int x,y;
	
	public Tiles(int x,int y,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g){
		g.drawImage(sprite, x-Camera.x, y-Camera.y, null);
	}
	
}
