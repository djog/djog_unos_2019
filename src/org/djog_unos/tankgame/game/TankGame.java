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

	private float Time = 0;

	private IGameObject[] gameObjects = new IGameObject[] {
		new Background(),
		new Player(0.0f, 0.0f),
		new Box(1.0f, 1.0f),
		new Box(-2.0f, -3.0f),
		new Box(-1.0f, 2.0f),
	};

	public static void main(String[] args) {
		new TankGame().run(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, MAX_FPS);
	}

	@Override
	public void init()
	{
		for (IGameObject gameObject : gameObjects) {
			gameObject.init();
		};
	}	

	@Override
	public void update()
	{
		// Will add 1 to time every 60 frames
		// 86400 seconds in a real life day
		Time = (float) ((Time + (60f / (1f / getDeltaTime()))) % 86400);

		for (IGameObject gameObject : gameObjects) {
			gameObject.update();
		}
	}

	@Override
	public void draw()
	{
		GameView.drawGame(gameObjects);
	}
}