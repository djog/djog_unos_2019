package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

import java.util.Iterator;

public class GameRenderer
{
	public static void drawGame(GameView gameView)
	{
		gameView.background.draw();
		gameView.player.draw();
		
		Iterator<Box> boxIterator = gameView.boxes.iterator();
		while (boxIterator.hasNext()) {
			Box box = boxIterator.next();
			box.draw();
		}

		Iterator<Tree> treeIterator = gameView.trees.iterator();
		while (treeIterator.hasNext()) {
			Tree tree = treeIterator.next();
			tree.draw();
		}

		Iterator<Bush> bushIterator = gameView.bushes.iterator();
		while (bushIterator.hasNext()) {
			Bush bush = bushIterator.next();
			bush.draw();
		}

		Iterator<Hedgehog> hedgehogIterator = gameView.hedgehogs.iterator();
		while (hedgehogIterator.hasNext()) {
			Hedgehog hedgehog = hedgehogIterator.next();
			hedgehog.draw();
		}

        Sprite shellSprite = new Sprite("shell.png", 32, 32, 0);
        for(Shell shell : gameView.player.getShells())
        {
            shellSprite.setPosition(shell.getX(), shell.getY());
            shellSprite.setRotation(shell.getAngle());
            shellSprite.draw();
		}
	}
}