package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Sprite;

public class MachineGun {
    private float rotation_speed;
    public Sprite sprite;
    private float rotation;

    public void setRotation(float rotation) {
        this.rotation = (this.rotation + rotation) % 360;
        if (sprite != null) {
            sprite.setRotation(this.rotation * ((float)Math.PI / 180));
        }
    }

    public void setRotation_speed(float rotation_speed) {
        this.rotation_speed = rotation_speed;
    }

    public void setRotationRaw(float rotation) { this.rotation = rotation; }

    public float getRotation_speed(){
        return rotation_speed;
    }

    public float getRotation(){
        return rotation;
    }

    public void draw()
    {
        sprite.draw();
    }
}
