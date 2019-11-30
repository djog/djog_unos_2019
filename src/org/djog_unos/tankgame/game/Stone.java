package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;

public class Stone extends DrawableGameObject {

    public Stone(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticCircleCollider(x, y, 64/2, true);
    }

    public void init()
    {
        super.init("stone.png", 64, 64);
    }
}