package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Game;
import org.djog_unos.tankgame.engine.PhysicsManager;
import org.djog_unos.tankgame.engine.Sprite;
import org.joml.Vector2f;
import org.djog_unos.tankgame.engine.audio.*;

public class MachineGunNest extends DrawableGameObject {

    MachineGun machineGun = new MachineGun();

    private static final float FIRE_DELAY = 0.1f;
    private static final float FIRE_OFFSET = 56;
    private static final float RANGE = 500;
    private float m_fireCountdown = 0.0f;
    private SoundSource m_soundSource;
    private int m_tracerCountdown = 5;

    public MachineGunNest(float x, float y){
       super(x, y);
       machineGun.setRotation(0);
       machineGun.setRotation_speed(3f);
    }
    
    public void init()
    {
        PhysicsManager.addDynamicCircleCollider(String.valueOf(Math.random()), PhysicsManager.Layer.MachineGunNest, m_x, m_y,  128/3);
        
        init("machine_nest.png", 128, 128);
        machineGun.sprite = new Sprite("machine_gun.png", 128, 128, 0);
        machineGun.sprite.setPosition(m_x, m_y + 32);

        // Setup audio
		SoundBuffer soundBuffer = new SoundBuffer("machine_gun_fire.ogg");
        AudioManager.addSoundBuffer(soundBuffer);
        m_soundSource = new SoundSource(false, false);
        m_soundSource.setPosition(m_x, m_y);
        m_soundSource.setBuffer(soundBuffer.getBufferId());
        m_soundSource.setGain(200);
        m_soundSource.setMaxDistance(200);
        AudioManager.addSoundSource("machine_gun_fire", m_soundSource);
    }
    
    public void update(Player player){
        double distance = Math.sqrt(((player.get_x() - m_x) * (player.get_x() - m_x) +
        (player.get_y() - m_y) * (player.get_y() - m_y)));
        
        if(distance > RANGE) return;
        
        
        float gun_radians = (float) java.lang.Math.atan2(m_x - player.get_x(), m_y - player.get_y());
        float gun_degrees = -gun_radians * (180 / (float)Math.PI);
        float shortest_angle = ((((gun_degrees - machineGun.getRotation()) % 360) + 540) % 360) - 180;
        machineGun.setRotation(((shortest_angle * 0.75f) % 360) * (float)Game.getDeltaTime() * machineGun.getRotation_speed());
        
        if (m_fireCountdown > 0.0f)
        {
            m_fireCountdown -= Game.getDeltaTime();
        }
        
        if (m_fireCountdown <= 0.0f)
        {          
            if (!m_soundSource.isPlaying())      
                m_soundSource.play();

            m_tracerCountdown--;
            ProjectileType type = ProjectileType.Bullet;
            if(m_tracerCountdown == 0) {
                type = ProjectileType.Tracer;
                m_tracerCountdown = 5;
            }
            m_fireCountdown = FIRE_DELAY;
            Vector2f shellTarget = new Vector2f();
            shellTarget.x = (float)-Math.sin(-machineGun.getRotation() * (Math.PI / 180));
            shellTarget.y = (float)-Math.cos(-machineGun.getRotation() * (Math.PI / 180));
            Vector2f shellPosition = new Vector2f(m_x, m_y);
            Vector2f offsetDirection = new Vector2f(shellTarget); // Copy shellDirection otherwise shellDirectoin will change
            shellPosition.add(offsetDirection.mul(FIRE_OFFSET));
            ProjectileManager.addProjectile(type, shellPosition.x, shellPosition.y + 32, machineGun.getRotation() * ((float)Math.PI / 180) + (float)Math.PI, shellTarget);
        }
    }
}
