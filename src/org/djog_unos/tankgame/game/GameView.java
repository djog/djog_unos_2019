package org.djog_unos.tankgame.game;

import java.util.ArrayList;
import java.util.Iterator;

import org.djog_unos.tankgame.engine.*;
import java.util.Random;

public class GameView extends View
{
    public Background background = new Background();
	public Player player =	new Player(0.0f, 0.0f);

	public ArrayList<Box> boxes = new ArrayList<>();
	public ArrayList<Bush> bushes = new ArrayList<>();
    public ArrayList<Tree> trees = new ArrayList<>();
	public ArrayList<Hedgehog> hedgehogs = new ArrayList<>();
	public ArrayList<Stone> stones = new ArrayList<>();
	
    @Override
    protected void setupView() {
		background.init();
		player.init();

		for(int i = 0; i < 8; i++)
		{
			boxes.add(new Box(getRandom(), getRandom()));
			bushes.add(new Bush(getRandom(), getRandom()));
			trees.add(new Tree(getRandom(), getRandom()));
			hedgehogs.add(new Hedgehog(getRandom(), getRandom()));
			stones.add(new Stone(getRandom(), getRandom()));
		}

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
		
		Iterator<Stone> stoneIterator = stones.iterator();
		while (stoneIterator.hasNext()) {
			Stone stone = stoneIterator.next();
			stone.init();
		}
	}
	
	@Override
    protected void updateView() {
    	player.update();
    	Camera.update(player.get_x(), player.get_y());
	}

	@Override
    protected void drawView() {
		GameRenderer.drawGame(this);
	}

	float getRandom()
	{
		return -1000 + (int)(Math.random() * ((1000 - -1000) + 1));
	}
}