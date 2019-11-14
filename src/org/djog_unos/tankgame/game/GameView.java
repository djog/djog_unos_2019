package org.djog_unos.tankgame.game;

import java.util.ArrayList;
import java.util.Iterator;

import org.djog_unos.tankgame.engine.*;

public class GameView extends View
{
    public Background background = new Background();
	public Player player =	new Player(0.0f, 0.0f);

	public ArrayList<Box> boxes = new ArrayList<>();
	public ArrayList<Bush> bushes = new ArrayList<>();
    public ArrayList<Tree> trees = new ArrayList<>();
	public ArrayList<Hedgehog> hedgehogs = new ArrayList<>();
	
    @Override
    protected void setupView() {
		background.init();
		player.init();
		boxes.add(new Box(200.0f, 200.0f));
		bushes.add(new Bush(-200.0f, -300.0f));
		trees.add(new Tree(-100.0f, 200.0f));
		hedgehogs.add(new Hedgehog(200, -300));

		Iterator<Box> boxIterator = boxes.iterator();
		while (boxIterator.hasNext()) {
			Box box = boxIterator.next();
			box.init();
		}

		Iterator<Hedgehog> hedgehogIterator = hedgehogs.iterator();
		while (hedgehogIterator.hasNext()) {
			Hedgehog hedgehog = hedgehogIterator.next();
			hedgehog.init();
		}

		Iterator<Tree> treeIterator = trees.iterator();
		while (treeIterator.hasNext()) {
			Tree tree = treeIterator.next();
			tree.init();
		}
		
		Iterator<Bush> bushIterator = bushes.iterator();
		while (bushIterator.hasNext()) {
			Bush bush = bushIterator.next();
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