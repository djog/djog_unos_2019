package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;
import org.djog_unos.tankgame.engine.Window;
public class GameRenderer
{
	private static final float VIEW_MARGIN = 200;

	public static void drawGame(GameView gameView)
	{
		gameView.background.draw();
		
		for(var object : gameView.objects)
			if (isVisible(object))
				object.draw();

		for(var nest : gameView.machineGunNests) {
			if (isVisible(nest))
			{
				nest.machineGun.draw();
				nest.draw();
			}
		}

		for(var landmine : gameView.landmines)
			if (isVisible(landmine))
				landmine.draw();

		gameView.player.draw();

		for(var animation : GameView.animations)
			animation.draw();
        
		ProjectileManager.draw();
		PhysicsManager.drawDebugColliders();
	}

	static boolean isVisible(DrawableGameObject gameObject)
	{
		return gameObject.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f -  VIEW_MARGIN
			&& gameObject.getX() + gameObject.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + VIEW_MARGIN
			&& gameObject.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - VIEW_MARGIN
			&& gameObject.getY() + gameObject.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + VIEW_MARGIN;
	}
}