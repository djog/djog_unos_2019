package org.djog_unos.tankgame.game;

public class Hedgehog extends StaticGameObject {

    public Hedgehog(float x, float y)
    {
        super(x, y, 64/2);
    }

    public void init()
    {
        super.init("hedgehog.png", 96, 96);
    }
}