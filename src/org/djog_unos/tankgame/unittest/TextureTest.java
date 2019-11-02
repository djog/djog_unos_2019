package org.djog_unos.tankgame.unittest;

import static org.junit.Assert.*;

import org.djog_unos.tankgame.engine.Texture;
import org.djog_unos.tankgame.engine.TextureManager;
import org.junit.Test;

public class TextureTest {

    @Test
    public void testTextures() {
        { // A texture can be loaded with the texturemanager
            TextureManager.clearAllTextures();
            Texture texture = TextureManager.getTexture("pig.png");
            assertNotNull(texture);
            assertEquals(TextureManager.getTotalTextures(), 1, 0);
        }
        { // A texture is only sored once in the texturemanager to save RAM
            TextureManager.clearAllTextures();
            for (int i = 0; i < 40; i++)
            {
                @SuppressWarnings("unused")
                Texture texture = TextureManager.getTexture("box.png");
            }
            assertEquals(TextureManager.getTotalTextures(), 1, 0);
        }
    }
}