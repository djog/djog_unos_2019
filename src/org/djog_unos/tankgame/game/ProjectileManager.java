package org.djog_unos.tankgame.game;

import java.util.ArrayList;
import java.util.Iterator;
import org.djog_unos.tankgame.engine.*;
import org.joml.Vector2f;

public final class ProjectileManager
{
    private static ArrayList<Projectile> m_projectiles = new ArrayList<>(); 

    private static Projectile createProjectile(ProjectileType type, float x, float y, float angle, Vector2f direction)
    { // Speed & direction are the same for the type
        switch(type)
        {
            case Shell:
                return new Projectile(type, x, y, angle, direction, 400);
            case Bullet:
                return new Projectile(type, x, y, angle, direction, 700);
            case Tracer:
                return new Projectile(type, x, y, angle, direction, 700);
            default:
                throw new RuntimeException("A projectile is not implemented in createProjectile!");
        }
    }

    public static void addProjectile(ProjectileType type, float x, float y, float angle, Vector2f direction)
    {
        m_projectiles.add(createProjectile(type, x, y, angle, direction));
    }

    public static void update()
    {
        // Update & destroy projectiles
        Iterator<Projectile> i = m_projectiles.iterator();
        while (i.hasNext()) {
            Projectile projectile = i.next(); 
            projectile.update();
            if (projectile.destroyed)
            {
                i.remove();
            }
        }
    }

    public static void draw()
    {
    	
        Sprite shellSprite = new Sprite("shell.png", 32, 32, 0);
        Sprite bulletSprite = new Sprite("bullet.png", 16, 16, 0);
        Sprite tracerSprite = new Sprite("tracer.png", 16, 16, 0);

        for(Projectile projectile : m_projectiles)
        {
            switch(projectile.getType())
            {   
                case Shell:
                    shellSprite.setPosition(projectile.getX(), projectile.getY());
                    shellSprite.setRotation(projectile.getAngle());
                    shellSprite.draw();
                    break;
                case Bullet:
                    bulletSprite.setPosition(projectile.getX(), projectile.getY());
                    bulletSprite.setRotation(projectile.getAngle());
                    bulletSprite.draw();
                    break;
                case Tracer:
                    tracerSprite.setPosition(projectile.getX(), projectile.getY());
                    tracerSprite.setRotation(projectile.getAngle());
                    tracerSprite.draw();
                    break;
            }
		}
    }
}