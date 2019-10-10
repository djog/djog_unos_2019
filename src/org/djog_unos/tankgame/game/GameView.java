package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class GameView
{
	public static void drawGame(IGameObject[] gameObjects)
	{
		for (IGameObject gameObject : gameObjects) {
			gameObject.draw();
		};
	}
}