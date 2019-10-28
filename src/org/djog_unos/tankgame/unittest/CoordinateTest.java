package org.djog_unos.tankgame.unittest;

import static org.junit.Assert.*;

import org.djog_unos.tankgame.engine.Window;
import org.joml.Vector2f;
import org.junit.Test;

public class CoordinateTest {

    @Test
    public void testCoordinates() {
        { // The coordinates are still the same when converted back
            Vector2f screenCoords = new Vector2f(300, 600);
            Vector2f worldCoords = Window.ScreenToWorldCoords(screenCoords);
            Vector2f screenCoords2 = Window.WorldToScreenCoords(worldCoords);
            assertEquals(screenCoords, screenCoords2);
        }
    }
}