package org.djog_unos.tankgame.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.djog_unos.tankgame.engine.audio.AudioManager;
import org.lwjgl.opengl.GL;

public abstract class Game 
{
	protected abstract void init();
    protected abstract void update();
	protected abstract void draw();

	private static Window window;
	private static double totalGameTime;
	private static double deltaTime = 1.0/60.0; // Default for testing
	private static double lastFrameTime;
	private static boolean isInitialized = false;
	private long variableYieldTime, lastTime;

	protected void run(int width, int height, boolean fullscreen, String title, int maxFPS) {
		setupWindow(title, width, height, fullscreen);
		AudioManager.init();
		init();
		isInitialized = true;

		// The main game loop
		while(!window.isOpen()){
			updateDeltaTime();

			// Input
			InputManager.update();
			glfwPollEvents();
			// Update
			update();
			
			// Draw
			glClear(GL_COLOR_BUFFER_BIT); // Clear the last frame
			glClearColor(0.6f, 0.8f, 1f, 1);
			draw();
			window.swapBuffers();

			// Vsync & Wait for next frame
			vsync(maxFPS); 
		}
		// Cleanup everything properly
		destroy();
	}
	
	private void setupWindow(String title, int width, int height, boolean fullscreen)
	{
		if(!glfwInit()){
			System.err.println("Failed To Initialize!");
			System.exit(1);
		}

		window = new Window(title, width, height, fullscreen);

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
		totalGameTime += deltaTime;
	}

	private void destroy() {
		AudioManager.cleanup();
		GL.setCapabilities(null);

		window.destroyWindow();
		glfwTerminate();
	}
	
	// STATIC GETTERS

	public static double getDeltaTime() {
		return deltaTime;
	}
	
	public static double getTotalGameTime()
	{
		return totalGameTime;
	}

    public static double getFPS()
	{
		return (1 / getDeltaTime());
	}

	public static boolean isInitialized()
	{
		return isInitialized;
	}
}