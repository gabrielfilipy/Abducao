package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity {
	
	public boolean right, left;
	public int dir = 1;
	private double gravity = 2;
	public boolean jump = false;
	public boolean isJumping = false;
	public int jumpHeight = 48;
	public int jumpFrames = 0;
	
	private int frames =0, maxFrames = 5, index = 0, maxInex = 1;
	private Boolean mover = false;
	
	private int framesAnimation = 0;
	
	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void update() {
		mover = false;
		depth = 2;
		if(World.varificaColisao((int)x,(int)(y+gravity)) && isJumping == false) {
			y+=gravity;
			for(int i = 0; i < Game.entites.size(); i++) {
			Entity e = Game.entites.get(i);
//			if(e instanceof Enemy) {
//				if(Entity.isColidding(this, e)) {
//					//Aplicar o pulo!
//					isJumping = true;
//					jumpHeight = 32;
//					//Remover vida do inimigo!
//					((Enemy) e).vida--;
//					if(((Enemy) e).vida == 0) {
//						//Destruir inimigo!
//						Game.entities.remove(i);
//						break;
//					}
//				}
//			}
			
			}
		}
		
		if(right && World.varificaColisao((int)(x+speed), (int)y)) {
			mover = true;
			x+=speed;
			dir = 1;
		}
		else if(left && World.varificaColisao((int)(x-speed), (int)y)) {
			mover = true;
			x-=speed;
			dir = -1;
		}
		
		if(jump) {
			if(!World.varificaColisao(this.getX(),this.getY()+1)) {
				isJumping = true;
			}else {
				jump = false;
			}
		}
		
		if(isJumping) {
			if(World.varificaColisao(this.getX(), this.getY()-2)) {
				y-=3;
				jumpFrames+=2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		
		if(mover) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxInex) {
					index = 0;
				}
			}
		}
		
	}
	
	@Override
	public void render(Graphics g) {
		System.out.println(dir);
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[index];
		}else if(dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[index];
		}
		super.render(g);
	}

}
