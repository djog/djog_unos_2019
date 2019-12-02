package org.djog_unos.tankgame.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.File;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
    private int id;

    public Texture(String path) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(path));
            int width = bi.getWidth();
            int height = bi.getHeight();

            int[] rawPixels = new int [width * height * 4];
            rawPixels = bi.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    int pixel = rawPixels[x * width + y];
                    pixels.put((byte)((pixel >> 16) & 0xFF));   // RED
                    pixels.put((byte)((pixel >> 8) & 0xFF));    // GREEN
                    pixels.put((byte)(pixel & 0xFF));           // BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF));   // ALPHA
                }
            }

            pixels.flip();

            id = glGenTextures();
            
            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,  GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,  GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        } 
        catch (IOException e)
        {
            System.out.println("PATH: " + path);
            e.printStackTrace();
        }
    }

    public void bind(int sampler)
    {
        if (sampler >= 0 && sampler <= 31)
        {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
        else
        {
            System.err.println("ERROR: The sampler must be in the range of 0 - 31!");
        }
    }
}