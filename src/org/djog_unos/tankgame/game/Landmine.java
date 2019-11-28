package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;

public class Landmine extends DrawableGameObject {

    public Landmine(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticCircleCollider(x, y, 16, false);
    }

    public void init()
    {
        super.init("landmine.png", 32, 32);
    }
}
