package org.djog_unos.tankgame.game;

public class Bush extends DrawableGameObject{

    public Bush(float x, float y)
    {
        super(x, y);
    }

    public void init()
    {
        super.init("bush.png", 64, 64);
    }

}
