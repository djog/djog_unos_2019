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

    private int m_id;
    private int m_width, m_height;

    public Texture(String path) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(path));
            m_width = bi.getWidth();
            m_height = bi.getHeight();

            int[] rawPixels = new int [m_width * m_height * 4];
            rawPixels = bi.getRGB(0, 0, m_width, m_height, null, 0, m_width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(m_width * m_height * 4);

            for (int x = 0; x < m_width; x++)
            {
                for (int y = 0; y < m_height; y++)
                {
                    int pixel = rawPixels[x * m_width + y];
                    pixels.put((byte)((pixel >> 16) & 0xFF));   // RED
                    pixels.put((byte)((pixel >> 8) & 0xFF));    // GREEN
                    pixels.put((byte)(pixel & 0xFF));           // BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF));   // ALPHA
                }
            }

            pixels.flip();

            m_id = glGenTextures();
            
            glBindTexture(GL_TEXTURE_2D, m_id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,  GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,  GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, m_width, m_height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    Texture(int width, int height, ByteBuffer pixels)
    {
        m_id = glGenTextures();
        this.m_width = width;
        this.m_height = height;

        glBindTexture(GL_TEXTURE_2D, m_id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,  GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,  GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
    }

    public void bind(int sampler)
    {
        if (sampler >= 0 && sampler <= 31)
        {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, m_id);
        }
        else
        {
            System.err.println("ERROR: The sampler must be in the range of 0 - 31!");
        }
    }

    public int getWidth() { return m_width; }
    public int getHeight() { return m_height; }
}