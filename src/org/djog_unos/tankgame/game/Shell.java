package org.djog_unos.tankgame.game;

public class Shell {

    float m_x;
    float m_y;
    float m_angle;

    public Shell(float x, float y, float angle){
        m_x = x;
        m_y = y;
        m_angle = angle;
    }

    public void set_x(float x){
        m_x = x;
    }
    public float get_x(){
        return m_x;
    }

    public void set_y(float y){
        m_y = y;
    }
    public float get_y(){
        return m_y;
    }

    public void set_angle(float angle){
        m_angle = angle;
    }
    public float get_angle(){
        return m_angle;
    }
}
