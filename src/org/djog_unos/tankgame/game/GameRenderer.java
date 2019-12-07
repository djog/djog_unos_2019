package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Game;
import org.djog_unos.tankgame.engine.Window;

import java.util.Iterator;

public class GameRenderer
{
	public static void drawGame(GameView gameView)
	{
		gameView.background.draw();

		//Frame time
		//System.out.println(1f / Game.getDeltaTime());

		boolean insideView;
		int Margin = 200;

		Iterator<Box> boxIterator = gameView.boxes.iterator();
		while (boxIterator.hasNext()) {
			Box box = boxIterator.next();
			insideView = box.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					  && box.getX() + box.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					  && box.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					  && box.getY() + box.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			box.draw();
		}

		Iterator<Tree> treeIterator = gameView.trees.iterator();
		while (treeIterator.hasNext()) {
			Tree tree = treeIterator.next();
			insideView = tree.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					&& tree.getX() + tree.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					&& tree.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					&& tree.getY() + tree.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			tree.draw();
		}

		Iterator<Bush> bushIterator = gameView.bushes.iterator();
		while (bushIterator.hasNext()) {
			Bush bush = bushIterator.next();
			insideView = bush.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					&& bush.getX() + bush.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					&& bush.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					&& bush.getY() + bush.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			bush.draw();
		}

		Iterator<Hedgehog> hedgehogIterator = gameView.hedgehogs.iterator();
		while (hedgehogIterator.hasNext()) {
			Hedgehog hedgehog = hedgehogIterator.next();
			insideView = hedgehog.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					&& hedgehog.getX() + hedgehog.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					&& hedgehog.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					&& hedgehog.getY() + hedgehog.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			hedgehog.draw();
		}
		
		Iterator<Stone> stoneIterator = gameView.stones.iterator();
		while (stoneIterator.hasNext()) {
			Stone stone = stoneIterator.next();
			insideView = stone.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					&& stone.getX() + stone.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					&& stone.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					&& stone.getY() + stone.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			stone.draw();
		}

		Iterator<MachineGunNest> MachineGunNestIterator = gameView.machineGunNests.iterator();
		while (MachineGunNestIterator.hasNext()) {
			MachineGunNest nest = MachineGunNestIterator.next();
			insideView = nest.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					&& nest.getX() + nest.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					&& nest.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					&& nest.getY() + nest.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			nest.machineGun.draw();
			nest.draw();
		}

		Iterator<Landmine> LandmineIterator = gameView.landmines.iterator();
		while (LandmineIterator.hasNext()) {
			Landmine landmine = LandmineIterator.next();
			insideView = landmine.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					&& landmine.getX() + landmine.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					&& landmine.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					&& landmine.getY() + landmine.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			landmine.draw();
		}

		gameView.player.draw();

		Iterator<Explosion> explosionIterator = gameView.explosions.iterator();
		while (explosionIterator.hasNext()) {
			Explosion explosion = explosionIterator.next();
			insideView = explosion.getX() >= -Camera.getPositon().x - Window.getWidth() / 2f - Margin
					&& explosion.getX() + explosion.getWidth() <= -Camera.getPositon().x + Window.getWidth() / 2f + Margin
					&& explosion.getY() >= -Camera.getPositon().y - Window.getHeight() / 2f - Margin
					&& explosion.getY() + explosion.getHeight() <= -Camera.getPositon().y + Window.getHeight() / 2f + Margin;
			if(!insideView) continue;
			explosion.draw();
		}
        
		ProjectileManager.draw();
	}
}