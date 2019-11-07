package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Tree {
    private Sprite m_sprite;
    private float m_x = 0.0f;
    private float m_y = 0.0f;

    public Tree(float x, float y)
    {
        m_x = x;
        m_y = y;
    }

    public void init()
    {
        m_sprite = new Sprite("tree.png", 128, 128, 0);
        m_sprite.setPosition(m_x, m_y);
    }
    public void draw(){ m_sprite.draw();}

    public float get_x() { return m_x; }
    public float get_y() { return m_y; }
    public float get_width() { return 95; /*return m_sprite.get_width();*/ }
    public float get_height() { return m_sprite.get_height(); }
}