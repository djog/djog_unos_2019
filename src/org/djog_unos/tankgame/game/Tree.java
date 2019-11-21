package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;


public class Tree extends StaticGameObject{

    public Tree(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticCircleCollider(x, y, 128/4);
    }

    public void init()
    {
        super.init("tree.png", 128, 128);
    }

}
