package org.djog_unos.tankgame.unittest;


import org.djog_unos.tankgame.game.Shell;
import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.assertEquals;

public class ShellTest {

    @Test
    public void testShell() {
        System.out.println("Inside testShell()");
        Shell shell = new Shell(0, 0, 0);
        assertEquals(shell.get_x(), 0, 0);
        shell.set_x(40.5f);
        assertEquals(shell.get_x(), 40.5f, 0);
    }
}