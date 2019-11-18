package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;

public class Box extends StaticGameObject {

    public Box(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticAABBCollider(x, y, 64, 64);
    }

    public void init()
    {
        super.init("box.png", 64, 64);
    }
}