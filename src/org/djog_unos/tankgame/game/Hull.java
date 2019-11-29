package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Sprite;

public class Hull {
    private float speed;
    private float rotation;

    public Sprite sprite;

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setRotation(float rotation) {
        this.rotation += rotation;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRotation() {
        return rotation;
    }

}
