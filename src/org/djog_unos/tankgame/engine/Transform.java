package org.djog_unos.tankgame.engine;

import org.joml.*;

public class Transform
{
    public Vector3f position;
    public Vector3f scale;

    public Transform()
    {
        position = new Vector3f(0,0,1);
        scale = new Vector3f(1,1,1);
    }

    public Transform(Vector3f position, Vector3f scale)
    {
        this.position = position;
        this.scale = scale;
    }

    public Matrix4f getProjection(Matrix4f target)
    {
        target.scale(scale);
        target.translate(position);
        return target;
    }
}