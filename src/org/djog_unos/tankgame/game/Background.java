package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Background implements IGameObject
{
    private Sprite m_sprite;
    //desert
    /*public void init()
    {
        m_sprite = new Sprite("sand.png", 1024, 1024, 0);
    }
    */
    //grassland
    public void init()
    {
        m_sprite = new Sprite("grassland.png", 1024, 1024, 0);
    }

    public void draw() 
    {
        m_sprite.draw();
    }
}