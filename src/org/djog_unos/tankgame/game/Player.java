package org.djog_unos.tankgame.game;

import java.util.*;

import org.djog_unos.tankgame.engine.*;
import org.joml.Vector2f;


public class Player
{
    private float m_movespeed = 256f;
    private float m_rotatespeed = 100f;
    private Sprite m_hullSprite;
    private Sprite m_turretSprite;

    private float m_x;
    private float m_y;
    private float m_hullRotation;
    
    private ArrayList<Shell> m_shells = new ArrayList<>();
    private static final float FIRE_DELAY = 1f;
    private static final float FIRE_OFFSET = 68;
    private static final float PI = 3.14159265359f;
    private float m_fireCountdown = 0.0f;
    private boolean m_buttonDown = false;

    Player(float x, float y)
    {
        m_x = x;
        m_y = y;
    }

    public void init()
    {
        // Sprites MUST be initialized in init() 
        m_hullSprite = new Sprite("hull.png", 128, 128, 0);
        m_turretSprite = new Sprite("turret.png", 128, 128, 0);
    }

    public void update()
    {
        // Movement
        Vector2f movement = new Vector2f();
        float hull_radian = m_hullRotation * (PI / 180);
        movement.x = m_movespeed * (float)Game.getDeltaTime()
                     * InputManager.getVerticalInput()
                     * (float)Math.sin(hull_radian);
        movement.y = m_movespeed * (float)Game.getDeltaTime()
                     * InputManager.getVerticalInput()
                     * (float)Math.cos(hull_radian);

        if(!PhysicsManager.checkPoint(m_x + movement.x, m_y)){
            m_x += movement.x;
        }
        if(!PhysicsManager.checkPoint(m_x, m_y + movement.y)){
            m_y += movement.y;
        }
        m_hullSprite.setPosition(m_x, m_y);
        m_turretSprite.setPosition(m_x, m_y);
        
        // Rotate turret to mouse
        Vector2f screenPos = Window.WorldToScreenCoords(new Vector2f(m_x, m_y));
        float directionX = screenPos.x - InputManager.getMousePosition().x;
        float directionY = screenPos.y - InputManager.getMousePosition().y;
        float turret_radians = (float)java.lang.Math.atan2(directionX, directionY);
        m_turretSprite.setRotation(turret_radians);
        m_hullSprite.setRotation(-hull_radian);

        // Rotate hull
        m_hullRotation += (InputManager.getHorizontalInput() * (m_rotatespeed * (float)Game.getDeltaTime())) % 360;
        m_hullRotation = m_hullRotation % 360;

        if(m_buttonDown && !InputManager.isMouseButtonDown(0))
            m_buttonDown = false;

        if (m_fireCountdown > 0.0f)
            m_fireCountdown -= Game.getDeltaTime();

        // Firing
        if (InputManager.isMouseButtonDown(0) && m_fireCountdown <= 0.0f && !m_buttonDown)
        {
            m_fireCountdown = FIRE_DELAY;
            m_buttonDown = true;
            Vector2f shellTarget = Window.ScreenToWorldCoords(InputManager.getMousePosition());
            Vector2f shellDirection = shellTarget.sub(new Vector2f(m_x, m_y)); 
            shellDirection.normalize();
            Vector2f shellPosition = new Vector2f(m_x, m_y);
            Vector2f offsetDirection = new Vector2f(shellDirection); // Copy shellDirection otherwise shellDirectoin will change
            shellPosition.add(offsetDirection.mul(FIRE_OFFSET));
            m_shells.add(new Shell(shellPosition.x, shellPosition.y, turret_radians, shellDirection));
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
        m_hullSprite.draw();
        m_turretSprite.draw();
    }

    public ArrayList<Shell> getShells()
    {
        return m_shells;
    }

    public float get_x() { return m_x; }
    public float get_y() { return m_y; }
}