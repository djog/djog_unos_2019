package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;

public class Hedgehog extends StaticGameObject {

    public Hedgehog(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticAABBCollider(x, y, 96, 96);
    }

    public void init()
    {
        super.init("hedgehog.png", 96, 96);
    }
}