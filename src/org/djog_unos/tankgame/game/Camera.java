package org.djog_unos.tankgame.game;

import org.joml.*;

public class Camera {
    private static Vector2f m_position = new Vector2f(0,0);
    private static final float FOLLOW_SPEED = 0.03f;
    
    public static void update(float targetX, float targetY)
    {
    	
        Vector2f desiredPosition = new Vector2f(targetX, targetY);
        
        Vector2f smoothedPosition = new Vector2f();
        
        m_position.lerp(desiredPosition, FOLLOW_SPEED, smoothedPosition);
        
        m_position = new Vector2f(smoothedPosition.x, smoothedPosition.y);
    }


    public static Vector3f getPositon() {
        return new Vector3f(-m_position.x, -m_position.y, 0);
    }

}
