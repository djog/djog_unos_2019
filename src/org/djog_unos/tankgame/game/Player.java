package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;
import org.joml.*;


public class Player
{
    private float m_movespeed = 256f;
    private Sprite m_sprite;

    private float m_x;
    private float m_y;

    Player(float x, float y)
    {
        m_x = x;
        m_y = y;
    }

    public void init()
    {
        // Sprites MUST be initialized in init() 
        m_sprite = new Sprite("tank.png", 128, 128, 0);               
    }

    public void update()
    {
        // Movement
        Vector2f movement = InputManager.getNormalizedInputVector();
        movement.mul(m_movespeed * (float)Game.getDeltaTime()); // Multiply by movespeed and deltatime
        m_x += movement.x;
        m_y += movement.y;
        m_sprite.setPosition(m_x, m_y);
        
        // Rotate to mouse
        Vector2f screenPos = Window.WorldToScreenCoords(new Vector2f(m_x, m_y));
        float directionX = screenPos.x - InputManager.getMousePos().x;
        float directionY = screenPos.y - InputManager.getMousePos().y;
        float radians = (float)java.lang.Math.atan2(directionX, directionY);
        m_sprite.setRotation(radians);
    }

    public void draw() 
    {
        m_sprite.draw();
    }
}