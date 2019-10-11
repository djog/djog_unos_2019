package org.djog_unos.tankgame.engine;

import java.util.HashMap;

public class TextureManager {
    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String key) {
        if (textures.containsKey(key))
        { 
            return textures.get(key);
        }
        else
        {   // Load new texture
            Texture newTexture = new Texture("./assets/textures/" + key);
            textures.put("pig.png", newTexture);
            return newTexture;
        }
    }
}

