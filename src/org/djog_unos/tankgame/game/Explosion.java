package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Game;

public class Explosion extends DrawableGameObject {

    private String[] textures;

    private static final float FRAME_TIME = 0.05f;
    private float m_countDown = FRAME_TIME;
    public int current_frame = 0;

    public Explosion(float x, float y)
    {
        super(x, y);
        textures = new String[12];
        textures[0] = "explosion_1.png";
        textures[1] = "explosion_2.png";
        textures[2] = "explosion_3.png";
        textures[3] = "explosion_4.png";
        textures[4] = "explosion_5.png";
        textures[5] = "explosion_6.png";
        textures[6] = "explosion_7.png";
        textures[7] = "explosion_8.png";
        textures[8] = "explosion_9.png";
        textures[9] = "explosion_10.png";
        textures[10] = "explosion_11.png";
        textures[11] = "explosion_12.png";
    }

    public void update(){
        if(m_countDown > 0.0f){
            m_countDown -= Game.getDeltaTime();
        }

        if(m_countDown < 0.0f){
            m_countDown = FRAME_TIME;
            current_frame++;
        }
    }

    public void init()
    {
        super.init(textures[0], 96, 96);
    }

}
