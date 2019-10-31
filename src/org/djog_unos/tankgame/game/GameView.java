package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Sprite;

import java.util.Iterator;

public class GameView  
{
	public static void drawGame(TankGame game)
	{
		game.background.draw();
		game.player.draw();
		Iterator<Box> i = game.m_boxes.iterator();
		while (i.hasNext()) {
			Box box = i.next();
			box.draw();
		}

		// Draw shells
        Sprite shellSprite = new Sprite("shell.png", 32, 32, 0);
        for(Shell shell : game.player.getShells())
        {
            shellSprite.setPosition(shell.getX(), shell.getY());
            shellSprite.setRotation(shell.getAngle());
            shellSprite.draw();
        }
	}
}