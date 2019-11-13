package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class TankGame extends Game
{
	// Window settings
	private final static int WINDOW_WIDTH = 1200;
	private final static int WINDOW_HEIGHT = 900;
	private final static boolean FULLSCREEN = true;
	private final static String WINDOW_TITLE = "Tank Game";
	private final static int MAX_FPS = 240;

	private float m_time = 0;

	private ViewManager m_viewManager = new ViewManager();

	public static void main(String[] args) {
		new TankGame().run(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN, WINDOW_TITLE, MAX_FPS);
	}

	@Override
	public void init()
	{
		m_viewManager.startDefaultView();
	}

	@Override
	public void update()
	{
		// Will add 60 to time every real life second
		// 86400 seconds in a real life day
		m_time = (float) ((m_time + (60f / (1f / getDeltaTime()))) % 86400);

		m_viewManager.update();
	}

	@Override
	public void draw()
	{
		m_viewManager.draw();
	}
}
