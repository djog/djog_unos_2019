package org.djog_unos.tankgame.engine;

import java.util.*;

import org.joml.Vector2f;
import org.joml.Vector4f;

class AABBCollider
{
	public float x1;
	public float x2;
	public float y1;
	public float y2;
	public PhysicsManager.Layer layer;

	public AABBCollider(float x, float y, float width, float height)
	{
		x1 = x - (width / 2);
		x2 = x + (width / 2);
		y1 = y - (height / 2);
		y2 = y + (height / 2);
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

	public float getWidth() { return x2 - x1; }
	public float getHeight() { return y2 - y1; }

	public Vector2f getCenter()
	{
		return new Vector2f(
			x1 + getWidth() / 2,
			y1 + getHeight() / 2
		);
	}
}

class CircleCollider
{
	public PhysicsManager.Layer layer;
	public float x;
	public float y;
	public float radius;

	public CircleCollider(float x, float y, float radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public CircleCollider(PhysicsManager.Layer layer, float x, float y, float radius)
	{
		this(x, y, radius);
		this.layer = layer;
	}
}



public final class PhysicsManager
{
	public enum Layer { Player, MachineGunNest }

	public static boolean debugPhysics = false;
	public static Vector4f DEBUG_COLOR = new Vector4f(1, 0, 1, .4f);
	
	private static ArrayList<CircleCollider> m_circleColliders = new ArrayList<>();
    private static ArrayList<AABBCollider> m_aabbColliders = new ArrayList<>();
	
	private static HashMap<String, CircleCollider> m_dynamicCircleColliders = new HashMap<>();

	private static ArrayList<Shape> m_shapes = new ArrayList<>();
	private static ArrayList<Shape> m_dynamicShapes = new ArrayList<>();
	
	public static void addStaticCircleCollider(float x, float y, float radius)
	{
		m_circleColliders.add(new CircleCollider(x, y, radius));
	}

	public static void addStaticAABBCollider(float x, float y, float width, float height)
	{
		m_aabbColliders.add(new AABBCollider(x, y, width, height));
	}

	public static void addDynamicCircleCollider(String key, Layer layer, float x, float y, float radius)
	{
		m_dynamicCircleColliders.put(key, new CircleCollider(layer, x, y, radius));
	}

	public static void updateDynamicCircleCollider(String key, float x, float y)
	{
		CircleCollider newValue = m_dynamicCircleColliders.get(key);
		newValue.x = x;
		newValue.y = y;
		m_dynamicCircleColliders.put(key, newValue);
	}

	public static void removeDynamicCircleCollider(String key)
	{
		m_dynamicCircleColliders.remove(key);
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

	public static boolean checkPoint(Layer layer, float x, float y)
	{
		if (!Game.isInitialized()) // No collision when testing
			return false;

		Iterator<CircleCollider> i = m_dynamicCircleColliders.values().iterator();
		while(i.hasNext()) {
			CircleCollider collider = i.next();
			if (collider.layer == layer)
			{
				Vector2f pointA = new Vector2f(x, y);
				Vector2f pointB = new Vector2f(collider.x, collider.y);
				float distance = pointA.distance(pointB);
				if (distance <= collider.radius)
					return true;
			}
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
			Vector2f closestPoint = new Vector2f(x, y);
			// Get the closest point by clamping it to the min/max x/y
			if (closestPoint.x < collider.x1)
				closestPoint.x = collider.x1;
			else if (closestPoint.x > collider.x2)
				closestPoint.x = collider.x2;
			if (closestPoint.y < collider.y1)
				closestPoint.y = collider.y1;
			else if (closestPoint.y > collider.y2)
				closestPoint.y = collider.y2;

			float distance = closestPoint.distance(new Vector2f(x, y));
			if (distance <= radius)
				return true;
		}

		return false;
	}

	public static boolean checkCircle(Layer layer, float x, float y, float radius)
	{
		if (!Game.isInitialized()) // No collision when testing
			return false;

		Iterator<CircleCollider> i = m_dynamicCircleColliders.values().iterator();
		while(i.hasNext()) {
			CircleCollider collider = i.next();

			if (collider.layer == layer)
			{
				Vector2f pointA = new Vector2f(x, y);
				Vector2f pointB = new Vector2f(collider.x, collider.y);
				float distance = pointA.distance(pointB);
				if (distance <= (collider.radius + radius))
					return true;
			}
		}
		return false;
	}

	public static void cleanup()
	{
		m_aabbColliders.clear();
		m_circleColliders.clear();
		m_dynamicCircleColliders.clear();
	}

	public static void generateStaticDebugColliders()
	{
		for(var circleCollider : m_circleColliders)
		{
			Shape shape = new Shape(Shape.ShapeType.Circle, circleCollider.radius * 2, circleCollider.radius  * 2, DEBUG_COLOR);
			shape.setPosition(circleCollider.x, circleCollider.y);
			m_shapes.add(shape);
		}

		for(var aabbCollider : m_aabbColliders)
		{
			Shape shape = new Shape(Shape.ShapeType.Square, aabbCollider.getWidth(), aabbCollider.getHeight(), DEBUG_COLOR);
			shape.setPosition(aabbCollider.getCenter());
			m_shapes.add(shape);
		}
	}

	public static void generateDynamicDebugColliders()
	{
		m_dynamicShapes.clear();
		for(var circleCollider : m_dynamicCircleColliders.values())
		{
			Shape shape = new Shape(Shape.ShapeType.Circle, circleCollider.radius * 2, circleCollider.radius  * 2, DEBUG_COLOR);
			shape.setPosition(circleCollider.x, circleCollider.y);
			m_dynamicShapes.add(shape);
		}
	}

	public static void drawDebugColliders()
	{
		if (!debugPhysics)
			return;

		for(var shape : m_shapes)
			shape.draw();
		for(var shape : m_dynamicShapes)
			shape.draw();
	}
}