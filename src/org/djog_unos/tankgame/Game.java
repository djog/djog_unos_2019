package org.djog_unos.tankgame;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game 
{
	private Player player;
	private long window;

	private void run() {
		init();

		// Main game loop
		while(glfwWindowShouldClose(window) != true){
			input();

			update();

			draw();
		}

		glfwTerminate();
	}

	private void init()
	{
		if(!glfwInit()){
			System.err.println("Failed To Initialize");
			System.exit(1);
		}

		window = glfwCreateWindow(840, 680, "Game Window", 0, 0);

		glfwShowWindow(window);

		glfwMakeContextCurrent(window);

		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);
		
		player = new Player();
		player.init();
	}

	private void input()
	{
		glfwPollEvents();
	}

	private void update()
	{
		player.update(window);
	}

	private void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT);

		player.draw();

		glfwSwapBuffers(window);
	}

	public static void main(String[] args) {
		new Game().run();
	}
}