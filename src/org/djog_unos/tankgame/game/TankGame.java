package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;
import org.joml.*;

public class TankGame extends Game
{
	public final static int WINDOW_WIDTH = 840;
	public final static int WINDOW_HEIGHT = 640;
	public final static String WINDOW_TITLE = "Tank Game";
	public final static int MAX_FPS = 240;
	public final static Matrix4f PROJECTION = new Matrix4f().ortho2D(-TankGame.WINDOW_WIDTH/2, TankGame.WINDOW_WIDTH/2, -TankGame.WINDOW_HEIGHT/2, TankGame.WINDOW_HEIGHT/2); 

	public Background background = new Background();
	public Player player =	new Player(0.0f, 0.0f);
	public Box box1 = new Box(1.0f, 1.0f);
	public Box box2 = new Box(-2.0f, -3.0f);
	public Box box3 = new Box(-1.0f, 2.0f);

	public static void main(String[] args) {
		new TankGame().run(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, MAX_FPS);
	}

	@Override
	public void init()
	{
		background.init();
		player.init();
		box1.init();
		box2.init();
		box3.init();
	}	

	@Override
	public void update()
	{
		player.update();
	}

	@Override
	public void draw()
	{
		GameView.drawGame(this);
	}
}