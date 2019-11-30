package org.djog_unos.tankgame.engine;

import java.util.ArrayList;
import java.util.Iterator;

import org.djog_unos.tankgame.game.Player;
import org.joml.*;

class AABBCollider
{
	public float x1;
	public float x2;
	public float y1;
	public float y2;
	public boolean collidable;

	public AABBCollider(float x, float y, float width, float height, boolean collidable)
	{
		x1 = x - (width / 2);
		x2 = x + (width / 2);
		y1 = y - (height / 2);
		y2 = y + (height / 2);
		this.collidable = collidable;
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
	public boolean collidable;

	public CircleCollider(float x, float y, float radius, boolean collidable)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.collidable = collidable;
	}
}

class PlayerCollider
{
	public float x;
	public float y;
	public float radius;
	public boolean collidable;

	public PlayerCollider(float x, float y, float radius, boolean collidable)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.collidable = collidable;
	}
}

public final class PhysicsManager
{
	private static ArrayList<CircleCollider> m_circleColliders = new ArrayList<>();
    private static ArrayList<AABBCollider> m_aabbColliders = new ArrayList<>();
    private static PlayerCollider playerCollider;
	
	public static void addStaticCircleCollider(float x, float y, float radius, boolean collidable)
	{
		m_circleColliders.add(new CircleCollider(x, y, radius, collidable));
	}

	public static void addStaticAABBCollider(float x, float y, float width, float height, boolean collidable)
	{
		m_aabbColliders.add(new AABBCollider(x, y, width, height, collidable));
	}

	public static void addPlayerCollider(float x, float y, float radius, boolean collidable){
		playerCollider = new PlayerCollider(x, y, radius, collidable);
	}

	public static void updatePlayerCollider(float x, float y){
		playerCollider.x = x;
		playerCollider.y = y;
	}

	public static boolean checkPoint(float x, float y)
	{
		if (!Game.isInitialized()) // No collision when testing
			return false;

		Iterator<CircleCollider> i = m_circleColliders.iterator();
		while(i.hasNext()) {
			CircleCollider collider = i.next();

			if(!collider.collidable) continue;

			Vector2f pointA = new Vector2f(x, y);
			Vector2f pointB = new Vector2f(collider.x, collider.y);
			float distance = pointA.distance(pointB);
			if (distance <= collider.radius)
				return true;
		}

		Iterator<AABBCollider> j = m_aabbColliders.iterator();
		while(j.hasNext()) {
			AABBCollider collider = j.next();

			if(!collider.collidable) continue;

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

			if(!collider.collidable) continue;

			Vector2f pointA = new Vector2f(x, y);
			Vector2f pointB = new Vector2f(collider.x, collider.y);
			float distance = pointA.distance(pointB);
			if (distance <= (collider.radius + radius))
				return true;
		}

		Iterator<AABBCollider> j = m_aabbColliders.iterator();
		while(j.hasNext()) {
			AABBCollider collider = j.next();

			if(!collider.collidable) continue;

			if ((x >= collider.x1 && x <= collider.x2)
			&&	(y >= collider.y1 && y <= collider.y2)) return true;

			Vector2f ciclePoint = new Vector2f(x, y);
			for (Vector2f point : collider.getPoints()) {
				float distance = point.distance(ciclePoint);
				if (distance <= radius)
					return true;
			}
		}

		return false;
	}

	public static boolean checkNonCollidingCircle(float x, float y, float radius) {
		if (!Game.isInitialized()) // No collision when testing
			return false;

		Iterator<CircleCollider> i = m_circleColliders.iterator();
		while (i.hasNext()) {
			CircleCollider collider = i.next();

			if (collider.collidable) continue;

			Vector2f pointA = new Vector2f(x, y);
			Vector2f pointB = new Vector2f(collider.x, collider.y);
			float distance = pointA.distance(pointB);
			if (distance <= (collider.radius + radius))
				return true;
		}

		return false;
	}

	public static boolean checkPlayer(float x, float y, float radius){
		if (!Game.isInitialized()) // No collision when testing
			return false;

		Vector2f pointA = new Vector2f(x, y);
		Vector2f pointB = new Vector2f(playerCollider.x, playerCollider.y);
		float distance = pointA.distance(pointB);
		if (distance <= (playerCollider.radius + radius))
			return true;
		else return false;
	}
}