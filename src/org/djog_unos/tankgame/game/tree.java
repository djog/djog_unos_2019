package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class tree {
    private Sprite m_sprite;
    private float m_x = 0.0f;
    private float m_y = 0.0f;

    public tree(float x, float y)
    {
        m_x = x;
        m_y = y;
    }

    public void init()
    {
        m_sprite = new Sprite("tree.png", 91, 128, 0);
        m_sprite.setPosition(m_x, m_y);
    }
    public void draw(){ m_sprite.draw();}
}
