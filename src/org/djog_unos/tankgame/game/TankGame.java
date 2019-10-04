package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class TankGame extends Game
{
	public final static int WINDOW_WIDTH = 840;
	public final static int WINDOW_HEIGHT = 640;
	public final static String WINDOW_TITLE = "Tank Game";
	public final static int MAX_FPS = 240;
	
	private Player player;

	@Override
	public void init()
	{
		player = new Player();
		player.init();
	}	

	@Override
	public void input()
	{
		// Handle input here
	}

	@Override
	public void update()
	{
		player.update();
	}

	@Override
	public void draw()
	{
		player.draw();
	}

	public static void main(String[] args) {
		new TankGame().run(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, MAX_FPS);
	}
}