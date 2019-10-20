package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class TankGame extends Game
{
	// Window settings
	private final static int WINDOW_WIDTH = 1000;
	private final static int WINDOW_HEIGHT = 500;
	private final static boolean FULLSCREEN = true;
	private final static String WINDOW_TITLE = "Tank Game";
	private final static int MAX_FPS = 240;


	public Background background = new Background();
	public Player player =	new Player(0.0f, 0.0f);
	public Box box1 = new Box(100.0f, 100.0f);
	public Box box2 = new Box(-200.0f, -300.0f);
	public Box box3 = new Box(-100.0f, 200.0f);

	public static void main(String[] args) {
		new TankGame().run(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN, WINDOW_TITLE, MAX_FPS);
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