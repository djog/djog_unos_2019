package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;

public class Tree extends StaticGameObject{

    public Tree(float x, float y)
    {
        super(x, y);
    }

    public void init()
    {
        super.init("tree.png", 128, 128);
    }

}
