package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Game;
import org.djog_unos.tankgame.engine.PhysicsManager;
import org.joml.Vector2f;

public class Shell 
{
    public boolean destroyed = false;

    private float m_x, m_y = 0.0f;
    private float m_angle = 0.0f;
    private Vector2f m_direction;
    private float m_lifetime = 0.0f;
    
    private static final float SPEED = 400f;
    private static final float MAX_LIFETIME = 5f;
    private static final float SHELL_RADIUS = 32/2;

    public Shell(float x, float y, float angle, Vector2f direction)
    {
        m_x = x;
        m_y = y;
        m_angle = angle;
        m_direction = direction;
    }

    public void update()
    {
        m_x += m_direction.x * SPEED * Game.getDeltaTime();
        m_y += m_direction.y * SPEED * Game.getDeltaTime();
        m_lifetime += Game.getDeltaTime();
        if (m_lifetime >= MAX_LIFETIME || PhysicsManager.checkCircle(m_x, m_y, SHELL_RADIUS))
            destroyed = true;
    }

    // GETTERS
    public float getX() { return m_x; }
    public float getY() { return m_y; }
    public float getAngle() { return m_angle; }
}
