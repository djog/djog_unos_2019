package org.djog_unos.tankgame.engine;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.*;

class AABBCollider
{
	public float x1;
	public float x2;
	public float y1;
	public float y2;

	public AABBCollider(float x, float y, float width, float height)
	{
		x1 = x - (width / 2);
		x2 = x + (width / 2);
		y1 = y - (height / 2);
		y2 = y + (height / 2);
		System.out.println(x1 + " " + x2  + " " + y1 + " " + y2);
	}

	public Vector2f[] getPoints()
	{
		Vector2f[] coords = new Vector2f[4];
		coords[0] = new Vector2f(x1, y1);
		coords[1] = new Vector2f(x2, y1);
		coords[2] = new Vector2f(x1, y2);
		coords[3] = new Vector2f(x2, y2);

		return coords;
	}
}

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
    private static ArrayList<AABBCollider> m_aabbColliders = new ArrayList<>();
	
	public static void addStaticCircleCollider(float x, float y, float radius)
	{
		m_circleColliders.add(new CircleCollider(x, y, radius));
	}

	public static void addStaticAABBCollider(float x, float y, float width, float height)
	{
		m_aabbColliders.add(new AABBCollider(x, y, width, height));
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

		Iterator<AABBCollider> j = m_aabbColliders.iterator();
		while(j.hasNext()) {
			AABBCollider collider = j.next();

			if ((x >= collider.x1 && x <= collider.x2)
			&&	(y >= collider.y1 && y <= collider.y2)) return true;
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

		Iterator<AABBCollider> j = m_aabbColliders.iterator();
		while(j.hasNext()) {
			AABBCollider collider = j.next();

			Vector2f closest;
			for (Vector2f point : collider.getPoints()) {
				// FIND THE CLOSEST
			}

			// CALCULATE DISTANCE
		}

		return false;
	}
}