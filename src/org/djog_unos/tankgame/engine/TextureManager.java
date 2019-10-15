package org.djog_unos.tankgame.engine;

import java.util.HashMap;

public class TextureManager {
    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String fileName) {
        if (textures.containsKey(fileName))
        { 
            return textures.get(fileName);
        }
        else
        {   // Load new texture
            Texture newTexture = new Texture("./assets/textures/" + fileName);
            textures.put(fileName, newTexture);
            return newTexture;
        }
    }
}

