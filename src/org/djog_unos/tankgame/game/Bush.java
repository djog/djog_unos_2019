package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Bush extends StaticGameObject{

    public Bush(float x, float y)
    {
        super(x, y);
    }

    public void init()
    {
        super.init("bush.png", 64, 64);
    }

}
