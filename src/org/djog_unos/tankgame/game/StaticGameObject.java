package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;
import org.djog_unos.tankgame.engine.Sprite;

abstract public class StaticGameObject {
    private Sprite m_sprite;
    private float m_x = 0.0f;
    private float m_y = 0.0f;

    public StaticGameObject(float x, float y, float radius) {
        m_x = x;
        m_y = y;
        PhysicsManager.addStaticCircleCollider(x, y, radius);
    }

    public void init(String textureName, int width, int height)
    {
        m_sprite = new Sprite(textureName, width, height, 0);
        m_sprite.setPosition(m_x, m_y);
    }

    public void draw(){ m_sprite.draw();}

    public float getX() { return m_x; }
    public float getY() { return m_y; }
    public float getWidth() { return m_sprite.getWidth(); }
    public float getHeight() { return m_sprite.getHeight(); }
}
