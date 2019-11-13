package org.djog_unos.tankgame.game;

public class Bush extends StaticGameObject{

    public Bush(float x, float y)
    {
        super(x, y, 64/2);
    }

    public void init()
    {
        super.init("bush.png", 64, 64);
    }

}
