package org.djog_unos.tankgame.unittest;


import org.djog_unos.tankgame.engine.Texture;
import org.djog_unos.tankgame.engine.TextureManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Texture.class,
        TextureManager.class
})
public class TextureTest {
    @Test
    public void TestTexture() {
        System.out.println("Testing Texture.class");
        Texture testTexture = new Texture("./assets/textures/pig.png");

    }
}
