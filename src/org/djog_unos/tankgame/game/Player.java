package org.djog_unos.tankgame.game;

import java.util.*;

import org.djog_unos.tankgame.engine.*;
import org.joml.Vector2f;


public class Player
{
    private float m_movespeed = 256f;
    private Sprite m_sprite;

    private float m_x;
    private float m_y;
    
    private ArrayList<Shell> m_shells = new ArrayList<>();
    private static final float FIRE_DELAY = .05f;
    private static final float FIRE_OFFSET = 68;
    private float m_fireCountdown = 0.0f;

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
        float directionX = screenPos.x - InputManager.getMousePosition().x;
        float directionY = screenPos.y - InputManager.getMousePosition().y;
        float radians = (float)java.lang.Math.atan2(directionX, directionY);
        m_sprite.setRotation(radians);

        if (m_fireCountdown > 0.0f)
        {
            m_fireCountdown -= Game.getDeltaTime();
        }

        // Firing
        if (InputManager.isMouseButtonDown(0) && m_fireCountdown <= 0.0f)
        {
            m_fireCountdown = FIRE_DELAY;
            Vector2f shellTarget = Window.ScreenToWorldCoords(InputManager.getMousePosition());
            Vector2f shellDirection = shellTarget.sub(new Vector2f(m_x, m_y)); 
            shellDirection.normalize();
            Vector2f shellPosition = new Vector2f(m_x, m_y);
            Vector2f offsetDirection = new Vector2f(shellDirection); // Copy shellDirection otherwise shellDirectoin will change
            shellPosition.add(offsetDirection.mul(FIRE_OFFSET));
            m_shells.add(new Shell(shellPosition.x, shellPosition.y, radians, shellDirection));
        }

        // Update & destroy shells
        Iterator<Shell> i = m_shells.iterator();
        while (i.hasNext()) {
            Shell shell = i.next(); 
            shell.update();
            if (shell.destroyed)
            {
                i.remove();
            }
        }
    }

    public void draw() 
    {
        m_sprite.draw();
    }

    public ArrayList<Shell> getShells()
    {
        return m_shells;
    }
}