package org.djog_unos.tankgame.engine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class Game 
{
	protected abstract void init();
    protected abstract void update();
	protected abstract void draw();

	private static long window;
	private static double deltaTime;
	private static double lastFrameTime;
	private long variableYieldTime, lastTime;

	protected void run(int width, int height, String title, int maxFPS) {
		setupWindow(width, height, title);
		init();

		while(glfwWindowShouldClose(window) != true){
			updateDeltaTime();

			// Input
			glfwPollEvents();
			// Update
			update();
			
			// Draw
			glClear(GL_COLOR_BUFFER_BIT); // Clear the last frame
			glClearColor(0.8f, 0.8f, 0.8f, 1);
			draw();
			glfwSwapBuffers(window);

			// Vsync / Wait for next frame
			vsync(maxFPS); 
		}

		glfwTerminate();
	}

	private void setupWindow(int width, int height, String title)
	{
		if(!glfwInit()){
			System.err.println("Failed To Initialize!");
			System.exit(1);
		}

		window = glfwCreateWindow(width, height, title, 0, 0);

		glfwShowWindow(window);

		glfwMakeContextCurrent(window);

		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);

		// Enable transparency
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
    
    private void vsync(int fps) {
		if (fps <= 0) return;

		long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
		// yieldTime + remainder micro & nano seconds if smaller than sleepTime
		long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
		long overSleep = 0; // time the sync goes over by
		
		try {
			while (true) {
				long t = System.nanoTime() - lastTime;
				
				if (t < sleepTime - yieldTime) {
					Thread.sleep(1);
				}
				else if (t < sleepTime) {
					Thread.yield();
				}
				else {
					overSleep = t - sleepTime;
					break; // exit while loop
				}
			}
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
		
			// auto tune the time sync should yield
			if (overSleep > variableYieldTime) {
				// increase by 200 microseconds (1/5 a ms)
				variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
			}
			else if (overSleep < variableYieldTime - 200*1000) {
				// decrease by 2 microseconds
				variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
			}
		}
	}

	private void updateDeltaTime()
	{
		double time = glfwGetTime();
		deltaTime = time - lastFrameTime;
		lastFrameTime = time;
	}

	// STATIC GETTERS

	public static long getWindow()
	{
		return window;
	}
	
	public static double getDeltaTime() {
		
		return deltaTime;
	}
    
    public static double getFPS()
	{
		return (1 / getDeltaTime());
	}
}