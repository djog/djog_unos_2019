package org.djog_unos.tankgame.game;

import java.util.ArrayList;
import java.util.Iterator;
import org.djog_unos.tankgame.engine.audio.*;
import org.djog_unos.tankgame.engine.*;

public class GameView extends View {
	public Background background = new Background();
	public Player player = new Player(0.0f, 0.0f);

	public ArrayList<Box> boxes = new ArrayList<>();
	public ArrayList<Bush> bushes = new ArrayList<>();
	public ArrayList<Tree> trees = new ArrayList<>();
	public ArrayList<Hedgehog> hedgehogs = new ArrayList<>();
	public ArrayList<MachineGunNest> machineGunNests = new ArrayList<>();
	public ArrayList<Stone> stones = new ArrayList<>();

	public ArrayList<Landmine> landmines = new ArrayList<>();
	public ArrayList<Explosion> explosions = new ArrayList<>();
		
	@Override
	protected void setupView() {
		AudioManager.setMusic(new SoundBuffer("game_music.ogg"));
		background.init();
		player.init();

		for(int i = 0; i < 8; i++)
		{
			boxes.add(new Box(getRandom(), getRandom()));
			bushes.add(new Bush(getRandom(), getRandom()));
			trees.add(new Tree(getRandom(), getRandom()));
			hedgehogs.add(new Hedgehog(getRandom(), getRandom()));
			stones.add(new Stone(getRandom(), getRandom()));
            machineGunNests.add(new MachineGunNest(getRandom(), getRandom()));
			landmines.add(new Landmine(getRandom(), getRandom()));
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

		Iterator<MachineGunNest> MachineGunNestIterator = machineGunNests.iterator();
		while (MachineGunNestIterator.hasNext()) {
            MachineGunNest nest = MachineGunNestIterator.next();
            nest.init();
        }
		
		Iterator<Stone> stoneIterator = stones.iterator();
		while (stoneIterator.hasNext()) {
			Stone stone = stoneIterator.next();
			stone.init();
		}

		Iterator<Landmine> landMineIterator = landmines.iterator();
		while (landMineIterator.hasNext()) {
			Landmine landmine = landMineIterator.next();
			landmine.init();
		}


		PhysicsManager.generateDebugColliders();
	}
	
	@Override
    protected void updateView() {
    	player.update();
    	Camera.update(player.get_x(), player.get_y());
		Iterator<MachineGunNest> MachineGunNestIterator = machineGunNests.iterator();
		while (MachineGunNestIterator.hasNext()) {
			MachineGunNest nest = MachineGunNestIterator.next();
			nest.update(player);
		}
		ProjectileManager.update();
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.update();
            if(explosion.current_frame > 11) explosionIterator.remove();
        }
		Iterator<Landmine> landmineIterator = landmines.iterator();
		while (landmineIterator.hasNext()) {
			Landmine landmine = landmineIterator.next();
			landmine.update(this);
			if(landmine.getToBeDestroyed()) landmineIterator.remove();
		}
	}

	@Override
    protected void drawView() {
		GameRenderer.drawGame(this);
		PhysicsManager.drawDebugColliders();
	}

	float getRandom()
	{ // Generate a random number between -1000 - -100 & 100 - 1000
		float random = 100 + (int)(Math.random() * ((900) + 1));
		if (Math.random() > 0.5)
			return random;
		else
			return -random;
	}

	@Override
	protected void endView() {

	}
}