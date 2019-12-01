package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;
import org.joml.*;

public class Projectile
{

    public boolean destroyed = false;

    private float m_x, m_y = 0.0f;
    private float m_angle = 0.0f;
    private Vector2f m_direction = new Vector2f();
    private float m_lifetime = 0.0f;
    private float m_speed = 400f;
    private ProjectileType m_type;

    private static final float MAX_LIFETIME = 5f;
    private static final float COLLIDER_RADIUS = 32/4;

    public Projectile(ProjectileType type, float x, float y, float angle, Vector2f direction, float speed)
    {
        m_type = type;
        m_x = x;
        m_y = y;
        m_angle = angle;
        m_speed = speed;
        direction.normalize(m_direction); // Normalize & store result in m_direction
    }

    public void update()
    {
        m_x += m_direction.x * m_speed * Game.getDeltaTime();
        m_y += m_direction.y * m_speed * Game.getDeltaTime();
        m_lifetime += Game.getDeltaTime();
        if (PhysicsManager.checkCircle(PhysicsManager.Layer.Player, m_x, m_y, COLLIDER_RADIUS))
        {
            destroyed = true;
            // TODO: Deal damage to player
        }
        if (PhysicsManager.checkCircle(PhysicsManager.Layer.MachineGunNest, m_x, m_y, COLLIDER_RADIUS))
        {
            destroyed = true;
            // TODO: Deal damage to MachineGunNest
        }
        if (m_lifetime >= MAX_LIFETIME || PhysicsManager.checkCircle(m_x, m_y, COLLIDER_RADIUS))
            destroyed = true;
    }

    // Getters
    public float getX() { return m_x; }
    public float getY() { return m_y; }
    public float getAngle() { return m_angle; }
    public ProjectileType getType() { return m_type; }
}