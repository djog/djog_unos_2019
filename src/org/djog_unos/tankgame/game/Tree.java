package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;


public class Tree extends DrawableGameObject{

    public Tree(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticAABBCollider(x, y, 50, 128);
    }

    public void init()
    {
        super.init("tree.png", 128, 128);
    }

}
