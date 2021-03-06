package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Sprite;

abstract public class DrawableGameObject {
    private Sprite m_sprite;
    
    protected float m_x = 0.0f;
    protected float m_y = 0.0f;

    public DrawableGameObject(float x, float y) {
        m_x = x;
        m_y = y;
    }

    public void init(String textureName, int width, int height)
    {
        m_sprite = new Sprite(textureName, width, height, 0);
        m_sprite.setPosition(m_x, m_y);
    }

    public abstract void init();
    
    public void draw(){ m_sprite.draw();}

    public float getWidth() { return m_sprite.getWidth(); }
    public float getHeight() { return m_sprite.getHeight(); }

    public float getX() { return m_x; }
    public float getY() { return m_y; }
}
