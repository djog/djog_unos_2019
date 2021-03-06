package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.*;
import org.djog_unos.tankgame.engine.audio.*;
import org.joml.Vector2f;

public class Player
{
    private static final int GLFW_KEY_LEFT = 263;
    private static final int GLFW_KEY_RIGHT = 262;
    private Turret turret = new Turret();
    private Hull hull = new Hull();

    private float m_x;
    private float m_y;

    private static final float FIRE_OFFSET = 68;
    private static final float BUTTON_DELAY = 0.5f;
    private static final float RADIUS = 128/2;
    private static final float TURRET_ROTATION_SPEED = 4;
    private static final float HULL_ROTATION_SPEED = 110;

    private float m_fireCountdown = 0.0f;
    private boolean m_buttonDown = false;
    private float m_buttonDelay = 0.0f;
    private int m_tracerCountdown = 5;

    private int gun = 1;
    
    private SoundSource shellSource;
    private SoundSource machineGunSource;
    
    Player(float x, float y)
    {
        m_x = x;
        m_y = y;

        turret.setRotation(0);

        hull.setSpeed(256f);
        hull.setRotation(0);
        PhysicsManager.addDynamicCircleCollider("Player", PhysicsManager.Layer.Player, m_x, m_y, 128/2);
    }

    public void init()
    {
        // Sprites MUST be initialized in init() 
        hull.sprite = new Sprite("hull.png", 128, 128, 0);
        turret.sprite = new Sprite("turret.png", 128, 128, 0);
        
        // Setup audio
 		SoundBuffer soundBuffer = new SoundBuffer("machine_gun_fire.ogg");
 		AudioManager.addSoundBuffer(soundBuffer);
 		machineGunSource = new SoundSource(false, false);
 		machineGunSource.setBuffer(soundBuffer.getBufferId());
 		AudioManager.addSoundSource("machine_gun_fire", machineGunSource);
 		
    	SoundBuffer sound2Buffer = new SoundBuffer("heavy_fire.ogg");
        AudioManager.addSoundBuffer(sound2Buffer);
    	shellSource = new SoundSource(false, false);
    	shellSource.setBuffer(sound2Buffer.getBufferId());
    	AudioManager.addSoundSource("shell_fire", shellSource);
    }

    public void update()
    {
        // Movement
        Vector2f movement = new Vector2f(); 
        float hull_radian = hull.getRotation() * ((float)Math.PI / 180);
        movement.x = ((hull.getSpeed() * (float)Game.getDeltaTime())
                     * (InputManager.getVerticalInput()))
                     * (float)Math.sin(hull_radian);
        movement.y = ((hull.getSpeed() * (float)Game.getDeltaTime())
                     * (InputManager.getVerticalInput()))
                     * (float)Math.cos(hull_radian);
        if(!PhysicsManager.checkCircle(m_x + movement.x, m_y, RADIUS, PhysicsManager.Layer.Player)){
            m_x += movement.x;
        }
        if(!PhysicsManager.checkCircle(m_x, m_y + movement.y, RADIUS, PhysicsManager.Layer.Player)){
            m_y += movement.y;
        }

        // Apply movement
        hull.sprite.setPosition(m_x, m_y);
        turret.sprite.setPosition(m_x, m_y);
        AudioManager.setListenerPosition(new Vector2f(m_x, m_y));
        PhysicsManager.updateDynamicCircleCollider("Player", m_x, m_y);
        
        // Rotate turret to mouse
        Vector2f screenPos = Window.WorldToScreenCoords(new Vector2f(m_x, m_y));
        float directionX = screenPos.x - InputManager.getMousePosition().x;
        float directionY = screenPos.y - InputManager.getMousePosition().y;
        float turret_radians = (float)java.lang.Math.atan2(directionX, directionY);
        float turret_degrees = turret_radians * (180 / (float)Math.PI);
        float shortest_angle = ((((turret_degrees - turret.getRotation()) % 360) + 540) % 360) - 180;
        turret.setRotation(((shortest_angle * 0.75f) % 360) * (float)Game.getDeltaTime() * TURRET_ROTATION_SPEED);
        hull.sprite.setRotation(-hull_radian);

        // Rotate hull
        hull.setRotation((InputManager.getHorizontalInput() * HULL_ROTATION_SPEED % 360) * (float)Game.getDeltaTime());

        if(m_buttonDown && !InputManager.isMouseButtonDown(0)){
            m_buttonDown = false;
        }

        if (m_fireCountdown > 0.0f)
        {
            m_fireCountdown -= Game.getDeltaTime();
        }

        if(m_buttonDelay > 0.0f){
            m_buttonDelay -= Game.getDeltaTime();
        }
        
        if(InputManager.isKeyDownInt(GLFW_KEY_LEFT) == 1 && m_buttonDelay <= 0.0f){
            m_buttonDelay = BUTTON_DELAY;
            gun--;
            if(gun < 1) gun = 2;
        }
        else if(InputManager.isKeyDownInt(GLFW_KEY_RIGHT) == 1 && m_buttonDelay <= 0.0f){
            m_buttonDelay = BUTTON_DELAY;
            gun++;
            gun = Math.max(1, Math.min(2, gun % 3));
        }
        
        // Firing
        if (InputManager.isMouseButtonDown(0) && m_fireCountdown <= 0.0f && !m_buttonDown)
        {
            m_tracerCountdown--;
            ProjectileType type = ProjectileType.Bullet;
            if(m_tracerCountdown == 0) {
                type = ProjectileType.Tracer;
                m_tracerCountdown = 5;
            }
            Vector2f target = new Vector2f();
            target.x = (float)Math.sin(-turret.getRotation() * (Math.PI / 180));
            target.y = (float)Math.cos(-turret.getRotation() * (Math.PI / 180));
            Vector2f projectilePosition = new Vector2f(m_x, m_y);
            Vector2f offsetDirection = new Vector2f(target); // Copy shellDirection otherwise shellDirectoin will change
            projectilePosition.add(offsetDirection.mul(FIRE_OFFSET));
            switch(gun)
            {
                case 1:
                	shellSource.setPosition(m_x, m_y);
                    AudioManager.playSoundSource("shell_fire");            		
                    ProjectileManager.addProjectile(ProjectileType.Shell, projectilePosition.x, projectilePosition.y, turret.getRotation() * ((float)Math.PI / 180), target);
                    m_fireCountdown = 1.0f;
                    m_buttonDown = true;
                    break;
                case 2:
            		machineGunSource.setPosition(m_x, m_y);                		
            		AudioManager.playSoundSource("machine_gun_fire");
                    ProjectileManager.addProjectile(type, projectilePosition.x, projectilePosition.y, turret.getRotation() * ((float)Math.PI / 180), target);
                    m_fireCountdown = 0.1f;
                    break;
            }
        }
    }

    public void draw() 
    {
        hull.sprite.draw();
        turret.sprite.draw();
    }

    public float get_x() { return m_x; }
    public float get_y() { return m_y; }
}