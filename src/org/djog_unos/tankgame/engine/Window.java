package org.djog_unos.tankgame.engine;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWVidMode;

public class Window
{
    private static long window;
    private static int width, height;
    private static boolean fullscreen;

    @SuppressWarnings("static-access")
	public Window(String title, int width, int height, boolean fullscreen)
    {
        this.fullscreen = fullscreen;

        if (fullscreen)
        {
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            this.width = vidMode.width();
            this.height = vidMode.height();
        }
        else
        {
            this.width = width;
            this.height = height;
        }
        window = glfwCreateWindow(this.width, this.height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

        if (window == 0)
            throw new IllegalStateException("Failed to create window!");

        if (!fullscreen)
        {
            // Center window
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidMode.width()-width) / 2, (vidMode.height()-height) / 2);

            glfwShowWindow(window);
        }

        glfwMakeContextCurrent(window);
    }

    public boolean isOpen()
    {
        return glfwWindowShouldClose(window);
    }

    public void swapBuffers()
    {
        glfwSwapBuffers(window);
    }
    
    // STATIC GETTERS
    public static long getWindow() { return window; }
    public static int getWidth() { return width; }
    public static int getHeight() { return height; }
	public static Vector2f getWindowCenter()
	{
		return new Vector2f(width / 2, height / 2);
	}
	public static Matrix4f getMatrixProjection()
	{
		return new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2); 
    }
    public static boolean getFullscreen() { return fullscreen; }

    public static Vector2f ScreenToWorldCoords(Vector2f screenCoords)
	{
		return new Vector2f(screenCoords.x - (width / 2), -(screenCoords.y - (height / 2)));
	}
	public static Vector2f WorldToScreenCoords(Vector2f worldCoords)
	{
		return new Vector2f(worldCoords.x + (width / 2), -(worldCoords.y - (height / 2)));
	}
}