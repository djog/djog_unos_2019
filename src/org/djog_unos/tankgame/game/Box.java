package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Box extends StaticGameObject{

    public Box(float x, float y)
    {
        super(x, y);
    }

    public void init()
    {
        super.init("box.png", 96, 96);
    }
}