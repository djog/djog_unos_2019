package org.djog_unos.tankgame.engine;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;

public class InputManager {
	
	private static boolean keys[];
	
	static {
		keys = new boolean[GLFW_KEY_LAST];
		for(int i = 0; i < GLFW_KEY_LAST; i++)
		{
			keys[i] = false;
		}
	}
	
	public static Vector2f getInputVector()
    {
        float inputH = isKeyDownInt(GLFW_KEY_D) - isKeyDownInt(GLFW_KEY_A);
        float inputV = isKeyDownInt(GLFW_KEY_W) - isKeyDownInt(GLFW_KEY_S);
        return new Vector2f(inputH, inputV);
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
		return glfwGetKey(Game.getWindow(), key) == 1;
	}
	
	public static int isKeyDownInt(int key)
	{
		return glfwGetKey(Game.getWindow(), key);
	}
	
	public static boolean isKeyPressed(int key)
	{
		return (isKeyDown(key) && !keys[key]);
	}
	
	public static void update()
	{
		for(int i = 0; i < GLFW_KEY_LAST; i++)
		{
			keys[i] = isKeyDown(i);
		}
	}
}
