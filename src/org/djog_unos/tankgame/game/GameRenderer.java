package org.djog_unos.tankgame.game;

import java.util.Iterator;

public class GameRenderer
{
	public static void drawGame(GameView gameView)
	{
		gameView.background.draw();
		
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
		
		Iterator<Stone> stoneIterator = gameView.stones.iterator();
		while (stoneIterator.hasNext()) {
			Stone stone = stoneIterator.next();
			stone.draw();
		}

		Iterator<MachineGunNest> MachineGunNestIterator = gameView.machineGunNests.iterator();
		while (MachineGunNestIterator.hasNext()) {
			MachineGunNest nest = MachineGunNestIterator.next();
			nest.machineGun.draw();
			nest.draw();
		}

		Iterator<Landmine> LandmineIterator = gameView.landmines.iterator();
		while (LandmineIterator.hasNext()) {
			Landmine landmine = LandmineIterator.next();
			landmine.draw();
		}

		Iterator<Explosion> explosionIterator = gameView.explosions.iterator();
		while (explosionIterator.hasNext()) {
			Explosion explosion = explosionIterator.next();
			explosion.draw();
		}

		gameView.player.draw();
        
		ProjectileManager.draw();
	}
}