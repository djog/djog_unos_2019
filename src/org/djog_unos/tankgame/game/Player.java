package org.djog_unos.tankgame.game;

import java.util.*;

import org.djog_unos.tankgame.engine.*;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;


public class Player
{
    private float m_movespeed = 256f;
    private float m_hull_rotatespeed = 40f;
    private float m_turret_rotatespeed = 0.75f;
    private Sprite m_hull_sprite;
    private Sprite m_turret_sprite;

    private float m_x;
    private float m_y;
    // Degrees
    private float m_hull_rotation;
    private float m_turret_rotation;
    
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
        m_hull_sprite = new Sprite("hull.png", 128, 128, 0);
        m_turret_sprite = new Sprite("turret.png", 176, 176, 0);
    }

    public void update(TankGame game)
    {
        // Movement
        Vector2f movement = new Vector2f(); // = InputManager.getNormalizedInputVector();
        //movement.mul(m_movespeed * (float)Game.getDeltaTime()); // Multiply by movespeed and deltatime
        float hull_radian = m_hull_rotation * (PI / 180);
        movement.x = ((m_movespeed * (float)Game.getDeltaTime())
                     * (InputManager.isKeyDownInt(GLFW_KEY_W) - InputManager.isKeyDownInt(GLFW_KEY_S)))
                     * (float)Math.sin(hull_radian);
        movement.y = ((m_movespeed * (float)Game.getDeltaTime())
                     * (InputManager.isKeyDownInt(GLFW_KEY_W) - InputManager.isKeyDownInt(GLFW_KEY_S)))
                     * (float)Math.cos(hull_radian);
        if(!Game.collide(m_x + movement.x, m_y, game)){
            m_x += movement.x;
        }
        if(!Game.collide(m_x, m_y + movement.y, game)){
            m_y += movement.y;
        }
        m_hull_sprite.setPosition(m_x, m_y);
        m_turret_sprite.setPosition(m_x, m_y + 12);
        
        // Rotate turret to mouse
        Vector2f screenPos = Window.WorldToScreenCoords(new Vector2f(m_x, m_y));
        float directionX = screenPos.x - InputManager.getMousePosition().x;
        float directionY = screenPos.y - InputManager.getMousePosition().y;
        float turret_radians = (float)java.lang.Math.atan2(directionX, directionY);
        float turret_degrees = turret_radians * (180 / PI);
        float shortest_angle = (((turret_degrees - m_turret_rotation % 360) + 540) % 360) - 180;
        m_turret_rotation += ((shortest_angle * m_turret_rotatespeed) % 360) * (float)Game.getDeltaTime();
        m_turret_sprite.setRotation(m_turret_rotation * (PI / 180));
        m_hull_sprite.setRotation(-hull_radian);

        // Rotate hull
        m_hull_rotation += ((InputManager.isKeyDownInt(GLFW_KEY_D) - InputManager.isKeyDownInt(GLFW_KEY_A)) * (m_hull_rotatespeed * (float)Game.getDeltaTime())) % 360;
        m_hull_rotation = m_hull_rotation % 360;

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
            shellTarget.x = (float)Math.sin(-m_turret_rotation * (PI / 180));
            shellTarget.y = (float)Math.cos(-m_turret_rotation * (PI / 180));
            Vector2f shellPosition = new Vector2f(m_x, m_y);
            Vector2f offsetDirection = new Vector2f(shellTarget); // Copy shellDirection otherwise shellDirectoin will change
            shellPosition.add(offsetDirection.mul(FIRE_OFFSET));
            m_shells.add(new Shell(shellPosition.x, shellPosition.y + 12, m_turret_rotation * (PI / 180), shellTarget));
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
        m_hull_sprite.draw();
        m_turret_sprite.draw();
    }

    public ArrayList<Shell> getShells()
    {
        return m_shells;
    }

    public float get_x() { return m_x; }
    public float get_y() { return m_y; }
}