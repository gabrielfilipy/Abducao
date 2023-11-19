package graficos;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private BufferedImage spritesheet;

	public SpriteSheet(String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
	
}
