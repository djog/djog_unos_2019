package org.djog_unos.tankgame.game;

public class Tree extends StaticGameObject{

    public Tree(float x, float y)
    {
        super(x, y, 128/2);
    }

    public void init()
    {
        super.init("tree.png", 128, 128);
    }

}
