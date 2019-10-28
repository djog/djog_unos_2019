package org.djog_unos.tankgame.engine;

import static java.awt.Font.*;

import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryUtil;

public class Font {

    private final Map<Character, FontGlyph> m_glyphs;

    private final Texture m_texture;

    private int m_fontHeight;

    public Font() {
        this(new java.awt.Font(MONOSPACED, PLAIN, 16), true);
    }

    public Font(boolean antiAlias) {
        this(new java.awt.Font(MONOSPACED, PLAIN, 16), antiAlias);
    }

    public Font(int size) {
        this(new java.awt.Font(MONOSPACED, PLAIN, size), true);
    }

    public Font(int size, boolean antiAlias) {
        this(new java.awt.Font(MONOSPACED, PLAIN, size), antiAlias);
    }

    public Font(InputStream in, int size) throws FontFormatException, IOException {
        this(in, size, true);
    }

    public Font(InputStream in, int size, boolean antiAlias) throws FontFormatException, IOException {
        this(java.awt.Font.createFont(TRUETYPE_FONT, in).deriveFont(PLAIN, size), antiAlias);
    }
    public Font(java.awt.Font font) {
        this(font, true);
    }

    public Font(java.awt.Font font, boolean antiAlias) {
        m_glyphs = new HashMap<>();
        m_texture = createFontTexture(font, antiAlias);
    }

    private Texture createFontTexture(java.awt.Font font, boolean antiAlias) {
       
        int imageWidth = 0;
        int imageHeight = 0;

       
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
               
                continue;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
               
                continue;
            }

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        m_fontHeight = imageHeight;

       
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        int x = 0;

       
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
               
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
               
                continue;
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

           
            FontGlyph ch = new FontGlyph(charWidth, charHeight, x, image.getHeight() - charHeight, 0f);
            g.drawImage(charImage, x, 0, null);
            x += ch.width;
            m_glyphs.put(c, ch);
        }

       
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform,
                                                            AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

       
        int width = image.getWidth();
        int height = image.getHeight();

       
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

       
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
               
                int pixel = pixels[i * width + j];
               
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
               
                buffer.put((byte) (pixel & 0xFF));
               
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
       
        buffer.flip();
       
        Texture fontTexture = new Texture(width, height, buffer);
        MemoryUtil.memFree(buffer);
        return fontTexture;
    }

    private BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
       
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

       
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

       
        if (charWidth == 0) {
            return null;
        }

       
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public int getWidth(CharSequence text) {
        int width = 0;
        int lineWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
               
                width = Math.max(width, lineWidth);
                lineWidth = 0;
                continue;
            }
            if (c == '\r') {
               
                continue;
            }
            FontGlyph g = m_glyphs.get(c);
            lineWidth += g.width;
        }
        width = Math.max(width, lineWidth);
        return width;
    }

    public int getHeight(CharSequence text) {
        int height = 0;
        int lineHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
               
                height += lineHeight;
                lineHeight = 0;
                continue;
            }
            if (c == '\r') {
               
                continue;
            }
            FontGlyph g = m_glyphs.get(c);
            lineHeight = Math.max(lineHeight, g.height);
        }
        height += lineHeight;
        return height;
    }


    public void drawText(CharSequence text, float x, float y) {
        int textHeight = getHeight(text);

        float drawX = x;
        float drawY = y;
        if (textHeight > m_fontHeight) {
            drawY += textHeight - m_fontHeight;
        }

        float[] m_vertices = new float[] {
            -0.5f, 0.5f, 0,         // TOP RIGHT       0
            0.5f, 0.5f, 0,          // TOP LEFT        1
            0.5f, -0.5f, 0,         // BOTTOM RIGHT    2
            -0.5f, -0.5f, 0,        // BOTTOM LEFT     3
        };
    
        float[] m_textureCoords = new float[] {
            0,0,
            1,0,
            1,1,
            0,1,
        };
    
        int[] m_indices = new int[] {
            0,1,2,
            2,3,0
        };
        Model m_model = new Model(m_vertices, m_textureCoords, m_indices);
        Shader m_shader = new Shader("texture");
        System.out.println(m_texture.getWidth() + " x " + m_texture.getHeight());
        m_shader.bind();
        m_shader.setUniform("sampler", 0);
        m_shader.setUniform("projection", new Transform().getProjection(Window.getMatrixProjection()));

        m_texture.bind(0);
        m_model.render();


        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
               
                drawY -= m_fontHeight;
                drawX = x;
                continue;
            }
            if (ch == '\r') {
               
                continue;
            }
            FontGlyph g = m_glyphs.get(ch);
           // renderer.drawTextureRegion(texture, drawX, drawY, g.x, g.y, g.width, g.height, c);
            drawX += g.width;
        }
    }

}