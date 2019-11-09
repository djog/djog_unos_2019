package org.djog_unos.tankgame.engine;

import org.djog_unos.tankgame.game.*;

import java.util.Iterator;

public final class PhysicsManager
{
	private static TankGame game;
	
	private static final float BOX_RADIUS = 32;
	private static final float BUSH_RADIUS = 32;
	private static final float TREE_RADIUS = 64;

	private static final float BOX_CENTER_DIST = (float) Math.sqrt((BOX_RADIUS * BOX_RADIUS) / 2);
	private static final float BUSH_CENTER_DIST = (float) Math.sqrt((BUSH_RADIUS * BUSH_RADIUS) / 2);
	private static final float TREE_CENTER_DIST = (float) Math.sqrt((TREE_RADIUS * TREE_RADIUS) / 2);

	public static void setGame(TankGame gameRef) 
	{
		game = gameRef;
	}

	public static boolean checkPoint(float x, float y)
	{
		if (!Game.isInitialized()) // No collision when testing
			return false;

        // Boxes
		Iterator<Box> i = game.m_boxes.iterator();
		while (i.hasNext()) {
			
		    Box box = i.next();
            float closestX = Math.max(box.get_x(), Math.min(box.get_x() + box.get_width(), x + BOX_CENTER_DIST));
            float closestY = Math.max(box.get_y(), Math.min(box.get_y() + box.get_height(), y + BOX_CENTER_DIST));

            float distanceX = x + 45 - closestX;
            float distanceY = y + 45 - closestY;

            float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
            if(distanceSquared < (BOX_RADIUS * BOX_RADIUS)) return true;
		}

		// Trees
		Iterator<Tree> j = game.m_trees.iterator();
		while (j.hasNext()) {
			Tree tree = j.next();
			float closestX = Math.max(tree.get_x(), Math.min(tree.get_x() + tree.get_width(), x + TREE_CENTER_DIST));
			float closestY = Math.max(tree.get_y(), Math.min(tree.get_y() + tree.get_height(), y + TREE_CENTER_DIST));

			float distanceX = x + TREE_CENTER_DIST - closestX;
			float distanceY = y + TREE_CENTER_DIST - closestY;

			float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
			if(distanceSquared < (TREE_RADIUS * TREE_RADIUS)) return true;
		}

		// Bush
		Iterator<Bush> k = game.m_bushes.iterator();
		while (k.hasNext()) {
			Bush bush = k.next();
			float closestX = Math.max(bush.get_x(), Math.min(bush.get_x() + bush.get_width(), x + BUSH_CENTER_DIST));
			float closestY = Math.max(bush.get_y(), Math.min(bush.get_y() + bush.get_height(), y + BUSH_CENTER_DIST));

			float distanceX = x + BUSH_CENTER_DIST - closestX;
			float distanceY = y + BUSH_CENTER_DIST - closestY;

			float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
			if(distanceSquared < (BUSH_RADIUS * BUSH_RADIUS)) return true;
		}
		return false;
	}
}
    