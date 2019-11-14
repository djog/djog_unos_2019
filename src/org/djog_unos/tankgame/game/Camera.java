package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Game;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private static float m_x = 0;
    private static float m_y = 0;

    public static void update(float x, float y)
    {

     //   Vector2f position = new Vector2f(m_x, m_y);
    //    Vector2f targetPosition = new Vector2f(x, y);
   //     Vector2f movement = targetPosition.sub(position);

      //  if (movement.x != 0 && movement.y != 0)
      //      movement.normalize();


        m_x = (float)Game.getDeltaTime() * (float)Game.getTotalGameTime() * 10;
        //m_y += movement.y;
    //    System.out.println(m_x + " " + m_y);

    }


    public static Vector3f get(){
        return new Vector3f(-m_x, -m_y, 0);
    }

}
