package org.djog_unos.tankgame.game;

import java.util.ArrayList;
import java.util.Iterator;

import org.djog_unos.tankgame.engine.*;

public class GameView extends View
{
    public Background background = new Background();
	public Player player =	new Player(0.0f, 0.0f);

	public ArrayList<Box> m_boxes = new ArrayList<>();
	public ArrayList<Bush> m_bushes = new ArrayList<>();
    public ArrayList<Tree> m_trees = new ArrayList<>();
    
    @Override
    protected void setupView() {
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
    protected void updateView() {
		player.update();
	}

	@Override
    protected void drawView() {
		GameRenderer.drawGame(this);
	}
}