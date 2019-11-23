package org.djog_unos.tankgame.unittest;

import static org.junit.Assert.*;

import org.djog_unos.tankgame.game.*;


import org.joml.Vector2f;
import org.junit.Test;


public class ProjectileTest {

    @Test
    public void testProjectile() {
        {   // A projectile has a position and angle
           Projectile projectile = new Projectile(ProjectileType.Shell, 10f, 20f, 5f, null, 400);
          assertEquals(projectile.getX(), 10, 0);
          assertEquals(projectile.getY(), 20, 0);
          assertEquals(projectile.getAngle(), 5, 0);
        }
        {   // When a projectile updates it's position changes
            Projectile projectile = new Projectile(ProjectileType.Shell, 0, 0, 1, new Vector2f(1,1).normalize(), 400);
            for (int i = 0; i < 100; i++)
                projectile.update();
           assertTrue(projectile.getX() != 0);
           assertTrue(projectile.getY() != 0);
        }
        {   // A projectile has a lifetime and gets destroyed
           Projectile projectile = new Projectile(ProjectileType.Shell, 0, 0, 0, new Vector2f(1,1).normalize(), 400);
           for (int i = 0; i < 60 * 10; i++)
               projectile.update();
          assertTrue(projectile.destroyed);
        }
    }
}