package org.djog_unos.tankgame.unittest;

import static org.junit.Assert.*;

import org.djog_unos.tankgame.game.Shell;
import org.joml.Vector2f;
import org.junit.Test;

public class ShellTest {

    @Test
    public void testShell() {
        {   // A shell has a position and angle
            Shell shell = new Shell(10, 20, 5, null);
            assertEquals(shell.getX(), 10, 0);
            assertEquals(shell.getY(), 20, 0);
            assertEquals(shell.getAngle(), 5, 0);
        }
        {   // When a shell updates it's position changes
            Shell shell = new Shell(0, 0, 0, new Vector2f(1,1).normalize());
            for (int i = 0; i < 10; i++)
                shell.update();
            assertTrue(shell.getX() != 0);
            assertTrue(shell.getY() != 0);
        }
        {   // A shell has a lifetime and gets destroyed
            Shell shell = new Shell(0, 0, 0, new Vector2f(1,1).normalize());
            for (int i = 0; i < 60 * 10; i++)
                shell.update();
            assertTrue(shell.destroyed);
        }
    }
}