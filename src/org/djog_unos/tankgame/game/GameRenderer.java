package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

import java.util.Iterator;

public class GameRenderer
{
	public static void drawGame(GameView gameView)
	{
		gameView.background.draw();
		gameView.player.draw();
		Iterator<Box> i = gameView.m_boxes.iterator();
		while (i.hasNext()) {
			Box box = i.next();
			box.draw();
		}
		Iterator<Tree> j = gameView.m_trees.iterator();
		while (j.hasNext()) {
			Tree tree = j.next();
			tree.draw();
		}
		Iterator<Bush> k = gameView.m_bushes.iterator();
		while (k.hasNext()) {
			Bush bush = k.next();
			bush.draw();
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