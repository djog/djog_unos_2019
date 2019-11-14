package org.djog_unos.tankgame.engine.fontRendering;

import static java.lang.Math.round;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.util.*;

import static java.lang.Math.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Text {

    private final ByteBuffer ttf;

    private final STBTTFontinfo info;

    private final int ascent;
    private final int descent;
    private final int lineGap;
    
    private static final Vector3f BG_COLOR = new Vector3f(0.1f, 0.1f, 0.1f);
    private static final Vector3f TEXT_COLOR = new Vector3f(0,0.6f,1);

    protected final String text;
    private final   int    lineCount;

    private long window;
    private int ww = 800;
    private int wh = 600;

    private float contentScaleX, contentScaleY;

    private int fontHeight;

    private int   scale;
    private int   lineOffset;
    private float lineHeight;

    private boolean kerningEnabled = true;
    private boolean lineBBEnabled;
    private int BITMAP_W;
    private int BITMAP_H;
    private STBTTBakedChar.Buffer cdata;

    public Text(String text, int size) {

        this.text = text;
        this.fontHeight = size;
        lineCount = 1;
        this.lineHeight = fontHeight;

        try {
            ttf = IOUtil.ioResourceToByteBuffer("./assets/fonts/roboto_condensed.ttf", 512 * 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        info = STBTTFontinfo.create();
        if (!stbtt_InitFont(info, ttf)) {
            throw new IllegalStateException("Failed to initialize font information.");
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer pAscent  = stack.mallocInt(1);
            IntBuffer pDescent = stack.mallocInt(1);
            IntBuffer pLineGap = stack.mallocInt(1);

            stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap);

            ascent = pAscent.get(0);
            descent = pDescent.get(0);
            lineGap = pLineGap.get(0);
        }
        /*
            INIT
        */
        GLFWErrorCallback.createPrint().set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        long monitor = glfwGetPrimaryMonitor();

        int framebufferW;
        int framebufferH;
        try (MemoryStack s = stackPush()) {
            FloatBuffer px = s.mallocFloat(1);
            FloatBuffer py = s.mallocFloat(1);

            glfwGetMonitorContentScale(monitor, px, py);

            contentScaleX = px.get(0);
            contentScaleY = py.get(0);

            if (Platform.get() == Platform.MACOSX) {
                framebufferW = ww;
                framebufferH = wh;
            } else {
                framebufferW = round(ww * contentScaleX);
                framebufferH = round(wh * contentScaleY);
            }
        }

        this.window = glfwCreateWindow(framebufferW, framebufferH, "Test window", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetWindowSizeCallback(window, this::windowSizeChanged);
        glfwSetFramebufferSizeCallback(window, Text::framebufferSizeChanged);

        // Center window
        GLFWVidMode vidmode = Objects.requireNonNull(glfwGetVideoMode(monitor));

        glfwSetWindowPos(
            window,
            (vidmode.width() - framebufferW) / 2,
            (vidmode.height() - framebufferH) / 2
        );

        // Create context
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(1);
        glfwShowWindow(window);

        GLFWUtil.glfwInvoke(window, this::windowSizeChanged, Text::framebufferSizeChanged);

    }

    public static void main(String[] args) {
        new Text("MY TEXT", 24).run("TEXT TESTING");
    }

    private STBTTBakedChar.Buffer init(int BITMAP_W, int BITMAP_H) {
        int texID = glGenTextures();
        STBTTBakedChar.Buffer cdata = STBTTBakedChar.malloc(96);

        ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
        stbtt_BakeFontBitmap(ttf, getFontHeight() * getContentScaleY(), bitmap, BITMAP_W, BITMAP_H, 32, cdata);

        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glClearColor(BG_COLOR.x, BG_COLOR.y, BG_COLOR.z, 1.0f); // BG color
        glColor3f(TEXT_COLOR.x, TEXT_COLOR.y, TEXT_COLOR.z); // Text color

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        return cdata;
    }

    public void generate()
    {
        BITMAP_W = round(512 * getContentScaleX());
        BITMAP_H = round(512 * getContentScaleY());

        cdata = init(BITMAP_W, BITMAP_H);
    }   

    public void draw()
    {
      renderText(cdata, BITMAP_W, BITMAP_H);
    }
    
    public void cleanup()
    {
        cdata.free();
    }

    void loop() {
        generate();

        while (!glfwWindowShouldClose(getWindow())) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            float scaleFactor = 1.0f + getScale() * 0.25f;

            glPushMatrix();
            // Zoom
            glScalef(scaleFactor, scaleFactor, 1f);
            // Scroll
            glTranslatef(4.0f, getFontHeight() * 0.5f + 4.0f - getLineOffset() * getFontHeight(), 0f);

            draw();

            glPopMatrix();

            glfwSwapBuffers(getWindow());
        }
        cleanup();
    }

    protected void run(String title) {
        try {
            loop();
        } finally {
            try {
                destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Included
    private void destroy() {
        GL.setCapabilities(null);

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    // RENDERING
    private void renderText(STBTTBakedChar.Buffer cdata, int BITMAP_W, int BITMAP_H) {
        float scale = stbtt_ScaleForPixelHeight(info, getFontHeight());

        try (MemoryStack stack = stackPush()) {
            IntBuffer pCodePoint = stack.mallocInt(1);

            FloatBuffer x = stack.floats(0.0f);
            FloatBuffer y = stack.floats(0.0f);

            STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);

            int lineStart = 0;

            float factorX = 1.0f / getContentScaleX();
            float factorY = 1.0f / getContentScaleY();

            float lineY = 0.0f;

            glBegin(GL_QUADS);
            for (int i = 0, to = text.length(); i < to; ) {
                i += getCP(text, to, i, pCodePoint);

                int cp = pCodePoint.get(0);
                if (cp == '\n') {
                    if (isLineBBEnabled()) {
                        glEnd();
                        renderLineBB(lineStart, i - 1, y.get(0), scale);
                        glBegin(GL_QUADS);
                    }

                    y.put(0, lineY = y.get(0) + (ascent - descent + lineGap) * scale);
                    x.put(0, 0.0f);

                    lineStart = i;
                    continue;
                } else if (cp < 32 || 128 <= cp) {
                    continue;
                }

                float cpX = x.get(0);
                stbtt_GetBakedQuad(cdata, BITMAP_W, BITMAP_H, cp - 32, x, y, q, true);
                x.put(0, scale(cpX, x.get(0), factorX));
                if (isKerningEnabled() && i < to) {
                    getCP(text, to, i, pCodePoint);
                    x.put(0, x.get(0) + stbtt_GetCodepointKernAdvance(info, cp, pCodePoint.get(0)) * scale);
                }

                float
                    x0 = scale(cpX, q.x0(), factorX),
                    x1 = scale(cpX, q.x1(), factorX),
                    y0 = scale(lineY, q.y0(), factorY),
                    y1 = scale(lineY, q.y1(), factorY);

                glTexCoord2f(q.s0(), q.t0());
                glVertex2f(x0, y0);

                glTexCoord2f(q.s1(), q.t0());
                glVertex2f(x1, y0);

                glTexCoord2f(q.s1(), q.t1());
                glVertex2f(x1, y1);

                glTexCoord2f(q.s0(), q.t1());
                glVertex2f(x0, y1);
            }
            glEnd();
            if (isLineBBEnabled()) {
                renderLineBB(lineStart, text.length(), lineY, scale);
            }
        }
    }

    private void renderLineBB(int from, int to, float y, float scale) {
        glDisable(GL_TEXTURE_2D);
        glPolygonMode(GL_FRONT, GL_LINE);
        glColor3f(1.0f, 1.0f, 0.0f);

        float width = getStringWidth(info, text, from, to, getFontHeight());
        y -= descent * scale;

        glBegin(GL_QUADS);
        glVertex2f(0.0f, y);
        glVertex2f(width, y);
        glVertex2f(width, y - getFontHeight());
        glVertex2f(0.0f, y - getFontHeight());
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glPolygonMode(GL_FRONT, GL_FILL);
        glColor3f(TEXT_COLOR.x, TEXT_COLOR.y, TEXT_COLOR.z); // Text color
    }

    // WINDOW CALLBACKS
    private void windowSizeChanged(long window, int width, int height) {
        if (Platform.get() != Platform.MACOSX) {
            width /= contentScaleX;
            height /= contentScaleY;
        }

        this.ww = width;
        this.wh = height;

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, width, height, 0.0, -1.0, 1.0);
        glMatrixMode(GL_MODELVIEW);

        setLineOffset(lineOffset);
    }

    private static void framebufferSizeChanged(long window, int width, int height) {
        glViewport(0, 0, width, height);
    }

    // Private Scaling & Size
    private float getStringWidth(STBTTFontinfo info, String text, int from, int to, int fontHeight) {
        int width = 0;

        try (MemoryStack stack = stackPush()) {
            IntBuffer pCodePoint       = stack.mallocInt(1);
            IntBuffer pAdvancedWidth   = stack.mallocInt(1);
            IntBuffer pLeftSideBearing = stack.mallocInt(1);

            int i = from;
            while (i < to) {
                i += getCP(text, to, i, pCodePoint);
                int cp = pCodePoint.get(0);

                stbtt_GetCodepointHMetrics(info, cp, pAdvancedWidth, pLeftSideBearing);
                width += pAdvancedWidth.get(0);

                if (isKerningEnabled() && i < to) {
                    getCP(text, to, i, pCodePoint);
                    width += stbtt_GetCodepointKernAdvance(info, cp, pCodePoint.get(0));
                }
            }
        }

        return width * stbtt_ScaleForPixelHeight(info, fontHeight);
    }

    private static int getCP(String text, int to, int i, IntBuffer cpOut) {
        char c1 = text.charAt(i);
        if (Character.isHighSurrogate(c1) && i + 1 < to) {
            char c2 = text.charAt(i + 1);
            if (Character.isLowSurrogate(c2)) {
                cpOut.put(0, Character.toCodePoint(c1, c2));
                return 2;
            }
        }
        cpOut.put(0, c1);
        return 1;
    }
   
    private void setScale(int scale) {
        this.scale = max(-3, scale);
        this.lineHeight = fontHeight * (1.0f + this.scale * 0.25f);
        setLineOffset(lineOffset);
    }

    private void setLineOffset(float offset) {
        setLineOffset(round(offset));
    }

    private void setLineOffset(int offset) {
        lineOffset = max(0, min(offset, lineCount - (int)(wh / lineHeight)));
    }
    
    private static float scale(float center, float offset, float factor) {
        return (offset - center) * factor + center;
    }

    // GETTERS & SETTERS
    public String getText() {
        return text;
    }

    public long getWindow() {
        return window;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public int getScale() {
        return scale;
    }

    public float getContentScaleX() {
        return contentScaleX;
    }

    public float getContentScaleY() {
        return contentScaleY;
    }

    public int getLineOffset() {
        return lineOffset;
    }

    public boolean isKerningEnabled() {
        return kerningEnabled;
    }

    public boolean isLineBBEnabled() {
        return lineBBEnabled;
    }

    
}