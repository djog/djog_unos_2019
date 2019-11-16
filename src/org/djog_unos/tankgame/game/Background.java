package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Background
{
    private Sprite m_sprite;
    //desert
    /*public void init()
    {
        m_sprite = new Sprite("sand.png", 2048, 2048, 0);
    }
    */
    //toendra
    /*
        public void init()
    {
        m_sprite = new Sprite("toendra.png", 2048, 2048, 0);
    }
     */
    //snowy mountains
    public void init() {
        m_sprite = new Sprite("snow_mountains.png", 2048, 2048, 0);
    }
    //grassland
    /*
    public void init()
    {
        m_sprite = new Sprite("grassland.png", 2048, 2048, 0);
    }
*/
    public void draw() 
    {
        m_sprite.draw();
    }
}