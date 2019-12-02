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

    public static Texture[] getTexturesFromDir(String dirName, int amount)
    {
        Texture[] textures = new Texture[amount];
        for (int i = 0; i < amount; i++)
        {
            String relativePath = dirName + "/" + (i+1) + ".png";
            textures[i] = getTexture(relativePath);
        }
        return textures;
    }

    public static void clearAllTextures()
    {
        textures.clear();
    }

    public static int getTotalTextures()
    {
        return textures.size();
    }
}

