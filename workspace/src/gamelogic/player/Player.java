package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.PhysicsObject;
import gameengine.graphics.MyGraphics;
import gameengine.hitbox.RectHitbox;
import gamelogic.Main;
import gamelogic.level.Level;
import gamelogic.tiles.SolidTile;
import gamelogic.tiles.Tile;
import java.time.Clock;
import java.util.Date;
//Anura
//Caudata

//
public class Player extends PhysicsObject{
	//Clock clock = Clock.systemDefaultZone();
	//System.out.println(clock.millis());
	public float walkSpeed = 350;
	public float jumpPower = 1350;

	private boolean isJumping = false;

	public boolean isOnWall = false;

	public Player(float x, float y, Level level) {
	
		super(x, y, level.getLevelData().getTileSize(), level.getLevelData().getTileSize(), level);
		int offset =(int)(level.getLevelData().getTileSize()*0.1); //hitbox is offset by 10% of the player size.
		this.hitbox = new RectHitbox(this, offset,offset, width -offset, height - offset);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		movementVector.x = 0;
		if ((getCollisionMatrix()[PhysicsObject.RIG] instanceof SolidTile) || (getCollisionMatrix()[PhysicsObject.LEF] instanceof SolidTile))
		{
			if(PlayerInput.isUpKeyDown()) {
				movementVector.y -= (Level.GRAVITY * Level.GRAVITY) * tslf;
			
			}else if(PlayerInput.isDownKeyDown()) {
				movementVector.y += (Level.GRAVITY * Level.GRAVITY) * tslf;
			
			}

			else {
				movementVector.y = 0;
			}
		}
		else
		{
			movementVector.y += (Level.GRAVITY * Level.GRAVITY) * tslf;

		}
		if(PlayerInput.isLeftKeyDown()) {
			movementVector.x = -walkSpeed;
			if (!isJumping)
			{
				movementVector.y = -jumpPower/2;
				isJumping = true;
			}
		}
		if(PlayerInput.isRightKeyDown()) {
			movementVector.x = +walkSpeed;
			if (!isJumping)
			{
				movementVector.y = -jumpPower/2;
				isJumping = true;
			}
		}
		if(PlayerInput.isJumpKeyDown() && !isJumping) {
			//long secs = (new Date().getTime())/1000;
			movementVector.y = -jumpPower;
			isJumping = true;
			//movementVector.y -= (Level.GRAVITY * Level.GRAVITY) * tslf;
		}
		if(PlayerInput.isOneKeyDown()) {
			//long secs = (new Date().getTime())/1000;
			movementVector.x = -200;
		}
		if(PlayerInput.isTwoKeyDown()) {
			//long secs = (new Date().getTime())/1000;
			movementVector.x = 200;
		}
		if(PlayerInput.isThreeKeyDown() && !isJumping) {
			//long secs = (new Date().getTime())/1000;
			movementVector.y = -2000;
			isJumping = true;
		}
		
		isJumping = true;
		if(collisionMatrix[BOT] != null) isJumping = false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
		
		if(Main.DEBUGGING) {
			for (int i = 0; i < closestMatrix.length; i++) {
				Tile t = closestMatrix[i];
				if(t != null) {
					g.setColor(Color.RED);
					g.drawRect((int)t.getX(), (int)t.getY(), t.getSize(), t.getSize());
				}
			}
		}
		
		hitbox.draw(g);
	}
}
