package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Animation;
import org.djog_unos.tankgame.engine.PhysicsManager;

public class Landmine extends DrawableGameObject {

    private boolean toBeDestroyed = false;

    public Landmine(float x, float y)
    {
        super(x, y);
    }

    public void update(GameView view){
        if(PhysicsManager.checkCircle(PhysicsManager.Layer.Player, m_x, m_y, 16)){
            Animation explosion = new Animation("explosion", 12, 0.05f, 96, 96, 1);
            explosion.setPosition(m_x, m_y);
            view.explosions.add(explosion);
            toBeDestroyed = true;
        }
    }

    public void init()
    {
        super.init("landmine.png", 32, 32);
    }

    public boolean getToBeDestroyed() { return toBeDestroyed; }
}
