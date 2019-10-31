package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Box {
    private Sprite m_sprite;
    private float m_x = 0.0f;
    private float m_y = 0.0f;

    public Box(float x, float y)
    {
        m_x = x;
        m_y = y;
    }

    public void init()
    {
        m_sprite = new Sprite("box.png", 96, 96, 0);
        m_sprite.setPosition(m_x, m_y);
    }

    public void draw() 
    {
        m_sprite.draw();
    }

    public float get_x() { return m_x; }
    public float get_y() { return m_y; }
}