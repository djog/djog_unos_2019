package org.djog_unos.tankgame.game;

import static org.lwjgl.glfw.GLFW.*;
import org.djog_unos.tankgame.engine.*;
import org.joml.*;

public class Player implements IGameObject
{
    private float m_movespeed = 2f;
    private Vector2f m_input;
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
        Vector2f movement = InputManager.getNormalizedInputVector();
        movement.mul(m_movespeed * (float)Game.getDeltaTime()); // Multiply by movespeed and deltatime
        m_x += movement.x;
        m_y += movement.y;
        // Set position of sprite
        m_sprite.setPosition(m_x, m_y);
    }

    public void draw() 
    {
        m_sprite.draw();
    }
}