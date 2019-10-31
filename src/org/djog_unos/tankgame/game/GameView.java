package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Sprite;

public class GameView  
{
	public static void drawGame(TankGame game)
	{
		game.background.draw();
		game.player.draw();
		game.box1.draw();
		game.box2.draw();
		game.box3.draw(); 

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