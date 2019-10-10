package org.djog_unos.tankgame.engine;

import java.util.HashMap;

public class TextureManager {
    private static HashMap<String, Texture> textures = new HashMap<>();

    static {
        textures.put("pig.png", new Texture("./assets/textures/pig.png"));
    }

    public static Texture getTexture(String key) {
        return textures.get(key);
    }
}

