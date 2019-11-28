package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;

public class Bush extends DrawableGameObject{

    public Bush(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticCircleCollider(x, y, 64/3, true);
    }

    public void init()
    {
        super.init("bush.png", 64, 64);
    }

}
