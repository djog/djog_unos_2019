package org.djog_unos.tankgame.engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Transformable
{
    protected Transform transform;

    public void setPosition(float x, float y)
    {
        transform.position.x = x;
        transform.position.y = y;
    }
    
    public void setRotation(float z)
    {
        transform.rotation = z;
    }
    
    public void setScale(float width, float height)
    {
    	transform.scale = new Vector3f(width, height, 0);
    }
    
    public Vector2f getPosition() { return new Vector2f(transform.position.x, transform.position.y); }
    public float getRotation() { return transform.rotation; }
    public float getWidth() { return transform.scale.x; }
    public float getHeight() { return transform.scale.y; }
}