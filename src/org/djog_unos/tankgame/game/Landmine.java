package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.PhysicsManager;

public class Landmine extends DrawableGameObject {

    private boolean toBeDestroyed = false;

    public Landmine(float x, float y)
    {
        super(x, y);
        PhysicsManager.addStaticCircleCollider(x, y, 16, false);
    }

    public void update(GameView view){
        if(PhysicsManager.checkPlayer(super.getX(), super.getY(), 16)){
            Explosion explosion = new Explosion(super.getX(), super.getY());
            explosion.init();
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
