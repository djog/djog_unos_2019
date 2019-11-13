package org.djog_unos.tankgame.game;

public class Box extends StaticGameObject{

    public Box(float x, float y)
    {
        super(x, y, 64/2);
    }

    public void init()
    {
        super.init("box.png", 64, 64);
    }
}