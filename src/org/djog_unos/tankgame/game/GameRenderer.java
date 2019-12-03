package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;

public class GameRenderer
{
	public static void drawGame(GameView gameView)
	{
		gameView.background.draw();
		
		for(var object : gameView.objects)
			object.draw();

		for(var nest : gameView.machineGunNests) {
			nest.machineGun.draw();
			nest.draw();
		}

		for(var landmine : gameView.landmines)
			landmine.draw();

		gameView.player.draw();

		for(var animation : GameView.animations)
			animation.draw();
        
		ProjectileManager.draw();
		PhysicsManager.drawDebugColliders();
	}
}