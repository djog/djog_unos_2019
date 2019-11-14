package org.djog_unos.tankgame.game;

import java.util.*;

import org.djog_unos.tankgame.engine.*;
import org.joml.Vector2f;

public class Player
{
    private Turret turret = new Turret();
    private Hull hull = new Hull();

    private float m_x;
    private float m_y;

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

        turret.setRotation(0);
        turret.setRotation_speed(3f);

        hull.setSpeed(256f);
        hull.setRotation(0);
        hull.setRotation_speed(80f);
    }

    public void init()
    {
        // Sprites MUST be initialized in init() 
        hull.sprite = new Sprite("hull.png", 128, 128, 0);
        turret.sprite = new Sprite("turret.png", 128, 128, 0);
    }

    public void update()
    {
        // Movement
        Vector2f movement = new Vector2f(); 
        float hull_radian = hull.getRotation() * (PI / 180);
        movement.x = ((hull.getSpeed() * (float)Game.getDeltaTime())
                     * (InputManager.getVerticalInput()))
                     * (float)Math.sin(hull_radian);
        movement.y = ((hull.getSpeed() * (float)Game.getDeltaTime())
                     * (InputManager.getVerticalInput()))
                     * (float)Math.cos(hull_radian);
        if(!PhysicsManager.checkCircle(m_x + movement.x, m_y, 128/2)){
            m_x += movement.x;
        }
        if(!PhysicsManager.checkCircle(m_x, m_y + movement.y, 128/2)){
            m_y += movement.y;
        }
        hull.sprite.setPosition(m_x, m_y);
        turret.sprite.setPosition(m_x, m_y);
        
        // Rotate turret to mouse
        Vector2f screenPos = Window.WorldToScreenCoords(new Vector2f(m_x, m_y));
        float directionX = screenPos.x - InputManager.getMousePosition().x;
        float directionY = screenPos.y - InputManager.getMousePosition().y;
        float turret_radians = (float)java.lang.Math.atan2(directionX, directionY);
        float turret_degrees = turret_radians * (180 / PI);
        float shortest_angle = ((((turret_degrees - turret.getRotation()) % 360) + 540) % 360) - 180;
        turret.setRotation(((shortest_angle * 0.75f) % 360) * (float)Game.getDeltaTime() * turret.getRotation_speed());
        hull.sprite.setRotation(-hull_radian);

        // Rotate hull
        hull.setRotation((InputManager.getHorizontalInput() * hull.getRotation_speed() % 360) * (float)Game.getDeltaTime());

        if(m_buttonDown && !InputManager.isMouseButtonDown(0)){
            m_buttonDown = false;
        }

        if (m_fireCountdown > 0.0f)
        {
            m_fireCountdown -= Game.getDeltaTime();
        }

        // Firing
        if (InputManager.isMouseButtonDown(0) && m_fireCountdown <= 0.0f && !m_buttonDown)
        {
            m_fireCountdown = FIRE_DELAY;
            m_buttonDown = true;
            Vector2f shellTarget = new Vector2f();
            shellTarget.x = (float)Math.sin(-turret.getRotation() * (PI / 180));
            shellTarget.y = (float)Math.cos(-turret.getRotation() * (PI / 180));
            Vector2f shellPosition = new Vector2f(m_x, m_y);
            Vector2f offsetDirection = new Vector2f(shellTarget); // Copy shellDirection otherwise shellDirectoin will change
            shellPosition.add(offsetDirection.mul(FIRE_OFFSET));
            m_shells.add(new Shell(shellPosition.x, shellPosition.y, turret.getRotation() * (PI / 180), shellTarget));
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
        hull.sprite.draw();
        turret.sprite.draw();
    }

    public ArrayList<Shell> getShells()
    {
        return m_shells;
    }

    public float get_x() { return m_x; }
    public float get_y() { return m_y; }
}