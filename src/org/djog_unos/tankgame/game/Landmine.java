package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Animation;
import org.djog_unos.tankgame.engine.PhysicsManager;

public class Landmine extends DrawableGameObject {

    public boolean destroyed = false;

    public Landmine(float x, float y)
    {
        super(x, y);
    }

    public void init()
    {
        super.init("landmine.png", 32, 32);
    }

    public void update() {
        if(PhysicsManager.checkCircle(PhysicsManager.Layer.Player, m_x, m_y, 16)){
            Animation explosion = new Animation("explosion", 12, 0.05f, false, 96, 96, 1);
            explosion.setPosition(m_x, m_y);
            GameView.animations.add(explosion);
            destroyed = true;
        }
    }
}
