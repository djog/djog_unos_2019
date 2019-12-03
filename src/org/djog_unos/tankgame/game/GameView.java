package org.djog_unos.tankgame.game;

import java.util.ArrayList;
import java.util.Iterator;
import org.djog_unos.tankgame.engine.audio.*;
import org.djog_unos.tankgame.engine.*;

public class GameView extends View {
	public Background background = new Background();
	public Player player = new Player(0.0f, 0.0f);

	public ArrayList<DrawableGameObject> objects = new ArrayList<>(); 
	
	public ArrayList<MachineGunNest> machineGunNests = new ArrayList<>();
	public ArrayList<Landmine> landmines = new ArrayList<>();
	public static ArrayList<Animation> animations = new ArrayList<>();
		
	@Override
	protected void setupView() {
		AudioManager.setMusic(new SoundBuffer("game_music.ogg"));
		background.init();
		player.init();

		for(int i = 0; i < 8; i++)
		{
			objects.add(new Box(getRandom(), getRandom()));
			objects.add(new Bush(getRandom(), getRandom()));
			objects.add(new Tree(getRandom(), getRandom()));
			objects.add(new Hedgehog(getRandom(), getRandom()));
			objects.add(new Stone(getRandom(), getRandom()));
            machineGunNests.add(new MachineGunNest(getRandom(), getRandom()));
			landmines.add(new Landmine(getRandom(), getRandom()));
		}

		for(var object : objects)
			object.init();

		Iterator<MachineGunNest> mgNestIterator = machineGunNests.iterator();
		while (mgNestIterator.hasNext()) {
            MachineGunNest nest = mgNestIterator.next();
            nest.init();
        }

		Iterator<Landmine> landMineIterator = landmines.iterator();
		while (landMineIterator.hasNext()) {
			Landmine landmine = landMineIterator.next();
			landmine.init();
		}
		
		PhysicsManager.generateStaticDebugColliders();
	}
	
	@Override
    protected void updateView() {
    	player.update();
    	Camera.update(player.get_x(), player.get_y());
		
		for (var nest : machineGunNests)
			nest.update(player);

		ProjectileManager.update();

        Iterator<Animation> explosionIterator = animations.iterator();
        while (explosionIterator.hasNext()) {
            Animation explosion = explosionIterator.next();
			explosion.update();
			if (explosion.destoyed)
				explosionIterator.remove();
		}
		
		Iterator<Landmine> landmineIterator = landmines.iterator();
		while (landmineIterator.hasNext()) {
			Landmine landmine = landmineIterator.next();
			landmine.update();
			if(landmine.destroyed)
				landmineIterator.remove();
		}

		if (InputManager.isKeyDown(org.lwjgl.glfw.GLFW.GLFW_KEY_P))
		{
			PhysicsManager.debugPhysics = true;
			PhysicsManager.generateDynamicDebugColliders();
		}
		else
			PhysicsManager.debugPhysics = false;
	}

	@Override
    protected void drawView() {
		GameRenderer.drawGame(this);
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
		PhysicsManager.cleanup();
	}
}