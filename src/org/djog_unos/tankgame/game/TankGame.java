package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class TankGame extends Game
{
	public final static int WINDOW_WIDTH = 840;
	public final static int WINDOW_HEIGHT = 640;
	public final static String WINDOW_TITLE = "Tank Game";
	public final static int MAX_FPS = 240;
	
	private IGameObject[] gameObjects = new IGameObject[] { new Player() };

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
	public void input()
	{
		for (IGameObject gameObject : gameObjects) {
			gameObject.input();
		};
	}

	@Override
	public void update()
	{
		for (IGameObject gameObject : gameObjects) {
			gameObject.update();
		};
	}

	@Override
	public void draw()
	{
		for (IGameObject gameObject : gameObjects) {
			gameObject.draw();
		};
	}
}