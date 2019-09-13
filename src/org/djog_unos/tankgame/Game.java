package org.djog_unos.tankgame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

public class Game extends Player{

	private Player player;

	public void run() {
		if(!glfwInit()){
			System.err.println("Failed To Initialize");
			System.exit(1);
		}

		long win = glfwCreateWindow(640, 480, "Window", 0, 0);

		glfwShowWindow(win);

		glfwMakeContextCurrent(win);

		GL.createCapabilities();

		player = new Player();

		while(glfwWindowShouldClose(win) != true){
			glfwPollEvents();

			glClear(GL_COLOR_BUFFER_BIT);

			player.update(win);

			glfwSwapBuffers(win);
		}

		glfwTerminate();
	}

	public static void main(String[] args) {
		new Game().run();
	}

}