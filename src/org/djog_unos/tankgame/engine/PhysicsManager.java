package org.djog_unos.tankgame.engine;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.*;

// TODO: Add BoxCollider in the same way 

class CircleCollider
{
	public float x;
	public float y;
	public float radius;

	public CircleCollider(float x, float y, float radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
}

public final class PhysicsManager
{
    private static ArrayList<CircleCollider> m_circleColliders = new ArrayList<>();
	public static void addStaticCircleCollider(float x, float y, float radius)
	{
		m_circleColliders.add(new CircleCollider(x, y, radius));
	}

	public static boolean checkPoint(float x, float y)
	{
		if (!Game.isInitialized()) // No collision when testing
			return false;

		Iterator<CircleCollider> i = m_circleColliders.iterator();
		while(i.hasNext()) {
			CircleCollider collider = i.next();

			Vector2f pointA = new Vector2f(x, y);
			Vector2f pointB = new Vector2f(collider.x, collider.y);
			float distance = pointA.distance(pointB);
			if (distance <= collider.radius)
				return true;
		}
		return false;
	}

	public static boolean checkCircle(float x, float y, float radius)
	{
		if (!Game.isInitialized()) // No collision when testing
			return false;

		Iterator<CircleCollider> i = m_circleColliders.iterator();
		while(i.hasNext()) {
			CircleCollider collider = i.next();

			Vector2f pointA = new Vector2f(x, y);
			Vector2f pointB = new Vector2f(collider.x, collider.y);
			float distance = pointA.distance(pointB);
			if (distance <= (collider.radius + radius))
				return true;
		}
		return false;
	}
}