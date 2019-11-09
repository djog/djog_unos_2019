package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

import java.util.*;

public class TankGame extends Game
{
	// Window settings
	private final static int WINDOW_WIDTH = 1000;
	private final static int WINDOW_HEIGHT = 500;
	private final static boolean FULLSCREEN = false;
	private final static String WINDOW_TITLE = "Tank Game";
	private final static int MAX_FPS = 240;


	public Background background = new Background();
	public Player player =	new Player(0.0f, 0.0f);

	public ArrayList<Box> m_boxes = new ArrayList<>();
	public ArrayList<Bush> m_bushes = new ArrayList<>();
	public ArrayList<Tree> m_trees = new ArrayList<>();

	private float Time = 0;

	public static void main(String[] args) {
		new TankGame().run(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN, WINDOW_TITLE, MAX_FPS);
	}

	@Override
	public void init()
	{
		PhysicsManager.setGame(this);

		background.init();
		player.init();
		m_boxes.add(new Box(200.0f, 200.0f));
		m_bushes.add(new Bush(-200.0f, -300.0f));
		m_trees.add(new Tree(-100.0f, 200.0f));
		Iterator<Box> i = m_boxes.iterator();
		while (i.hasNext()) {
			Box box = i.next();
			box.init();
		}
		Iterator<Tree> j = m_trees.iterator();
		while (j.hasNext()) {
			Tree tree = j.next();
			tree.init();
		}
		Iterator<Bush> k = m_bushes.iterator();
		while (k.hasNext()) {
			Bush bush = k.next();
			bush.init();
		}
	}

	@Override
	public void update()
	{
		// Will add 60 to time every real life second
		// 86400 seconds in a real life day
		Time = (float) ((Time + (60f / (1f / getDeltaTime()))) % 86400);

		player.update();
	}

	@Override
	public void draw()
	{
		GameView.drawGame(this);
	}
}
