package org.djog_unos.tankgame.engine;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

public class InputManager {
	
	private static boolean keys[];
	
	static {
		keys = new boolean[GLFW_KEY_LAST];
		for(int i = 0; i < GLFW_KEY_LAST; i++)
		{
			keys[i] = false;
		}
	}

	public static float getHorizontalInput()
	{
		return isKeyDownInt(GLFW_KEY_D) - isKeyDownInt(GLFW_KEY_A);
	}

	public static float getVerticalInput()
	{
		return isKeyDownInt(GLFW_KEY_W) - isKeyDownInt(GLFW_KEY_S);
	}

	public static Vector2f getInputVector()
    {
        return new Vector2f(getHorizontalInput(), getVerticalInput());
    }
	
	public static Vector2f getNormalizedInputVector()
    {
		Vector2f vector = getInputVector();
		if (vector.length() != 0.0f)
			vector.normalize();
        return vector;
    }
	
	public static boolean isKeyDown(int key)
	{
		return glfwGetKey(Window.getWindow(), key) == 1;
	}
	
	public static int isKeyDownInt(int key)
	{
		return glfwGetKey(Window.getWindow(), key);
	}
	
	public static boolean isKeyPressed(int key)
	{
		return (isKeyDown(key) && !keys[key]);
	}

	public static boolean isMouseButtonDown(int button)
	{
		return glfwGetMouseButton(Window.getWindow(), button) == 1;
	}

	public static Vector2f getMousePosition()
	{
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Window.getWindow(), xBuffer, yBuffer);
		float x = (float)xBuffer.get(0);
		float y = (float)yBuffer.get(0);
		return new Vector2f(x, y);
	}

	public static void update()
	{
		for(int i = 0; i < GLFW_KEY_LAST; i++)
		{
			keys[i] = isKeyDown(i);
		}
	}
}
