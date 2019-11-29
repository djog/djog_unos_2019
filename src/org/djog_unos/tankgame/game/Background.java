package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Background
{
    private Sprite m_sprite;

    private int BG_COUNT = 4;

    public void init()
    {
        int random = (int)(Math.random() * BG_COUNT);
        switch(random)
        {
            case 0:
                m_sprite = new Sprite("snow_mountains.png", 4096, 4096, 0);
                break;
            case 1:
                m_sprite = new Sprite("sand.png", 4096, 4096, 0);
                break;
            case 2:
                m_sprite = new Sprite("toendra.png", 4096, 4096, 0);
                break;
            case 3:
                m_sprite = new Sprite("grassland.png", 4096, 4096, 0);
                break;
        }
    }

    public void draw() 
    {
        m_sprite.draw();
    }
}