package org.djog_unos.tankgame.game;

import org.djog_unos.tankgame.engine.Game;
import org.djog_unos.tankgame.engine.PhysicsManager;
import org.djog_unos.tankgame.engine.Sprite;
import org.joml.Vector2f;

public class MachineGunNest extends DrawableGameObject {

    private static final float PI = 3.14159265359f;

    MachineGun machineGun = new MachineGun();

    private static final float FIRE_DELAY = 0.1f;
    private static final float FIRE_OFFSET = 56;
    private static final float RANGE = 500;
    private float m_fireCountdown = 0.0f;

    public MachineGunNest(float x, float y){
       super(x, y);
       machineGun.setRotation(0);
       machineGun.setRotation_speed(3f);
       PhysicsManager.addStaticCircleCollider(x, y, 128/4);
    }

    public void init()
    {
        super.init("machine_nest.png", 128, 128);
        machineGun.sprite = new Sprite("machine_gun.png", 128, 128, 0);
        machineGun.sprite.setPosition(super.getX(), super.getY() + 32);
    }

    public void update(Player player){
        double distance = Math.sqrt(((player.get_x() - super.getX()) * (player.get_x() - super.getX()) +
                                     (player.get_y() - super.getY()) * (player.get_y() - super.getY())));

        if(distance > RANGE) return;

        float gun_radians = (float) java.lang.Math.atan2(super.getX() - player.get_x(), super.getY() - player.get_y());
        float gun_degrees = -gun_radians * (180 / PI);
        float shortest_angle = ((((gun_degrees - machineGun.getRotation()) % 360) + 540) % 360) - 180;
        machineGun.setRotation(((shortest_angle * 0.75f) % 360) * (float)Game.getDeltaTime() * machineGun.getRotation_speed());

        if (m_fireCountdown > 0.0f)
        {
            m_fireCountdown -= Game.getDeltaTime();
        }

        if (m_fireCountdown <= 0.0f)
        {
            m_fireCountdown = FIRE_DELAY;
            Vector2f shellTarget = new Vector2f();
            shellTarget.x = (float)-Math.sin(-machineGun.getRotation() * (PI / 180));
            shellTarget.y = (float)-Math.cos(-machineGun.getRotation() * (PI / 180));
            Vector2f shellPosition = new Vector2f(super.getX(), super.getY());
            Vector2f offsetDirection = new Vector2f(shellTarget); // Copy shellDirection otherwise shellDirectoin will change
            shellPosition.add(offsetDirection.mul(FIRE_OFFSET));
            ProjectileManager.addProjectile(ProjectileType.Bullet, shellPosition.x, shellPosition.y + 32, machineGun.getRotation() * (PI / 180) + PI, shellTarget);
        }
    }
}
