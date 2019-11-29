package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Sprite;

public class Turret {
    public Sprite sprite;
    private float rotation;

    public void setRotation(float rotation) {
        this.rotation = (this.rotation + rotation) % 360;
        if (sprite != null) {
            sprite.setRotation(this.rotation * ((float)Math.PI / 180));
        } 
    }

    public float getRotation(){
        return rotation;
    }
}
