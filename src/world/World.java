package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

public class World {
 
	public static int WIDTH;
	public static int HEIGHT;
	public static final int SIZE = 16;
	
	public static Tiles[] tiles;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tiles[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int x = 0; x < map.getWidth(); x++){
				for(int y = 0; y < map.getHeight(); y++){
					int pixelAtual = pixels[x + (y * map.getWidth())]; 
					tiles[x + (y * WIDTH)] = new FloorTile(x*16,y*16,Tiles.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						tiles[x + (y * WIDTH)] = new FloorTile(x*16,y*16,Tiles.TILE_FLOOR);
					} else if(pixelAtual == 0xFFffffff) {
						tiles[x + (y * WIDTH)] = new WallTile(x*16,y*16,Tiles.TILE_WALL);
					} else if(pixelAtual == 0xFF0026FF) {
						//Renderiza o Player.
						Game.player.setX(x*16);
						Game.player.setY(y*16);
					}
				}
			}
		} catch (IOException e) { 
			e.printStackTrace();
		}
	} 
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;//Equivalente a dividir por 16.
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)//Caso a camera fiquei negativo então...
					continue;
				Tiles tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	/*
	 * Método responsável para tratar colisões. 
	 * Verifica se está livre para o Player prosseguir.
	 */
	public static boolean varificaColisao(int xnext, int ynext) {
		int x1 = xnext / SIZE;
		int y1 = ynext / SIZE;
		
		int x2 = (xnext+SIZE-1) / SIZE;
		int y2 = ynext / SIZE;
		
		int x3 = xnext / SIZE;
		int y3 = (ynext+SIZE-1) / SIZE;
		
		int x4 = (xnext+SIZE-1) / SIZE;
		int y4 = (ynext+SIZE-1) / SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	} 
	
}
