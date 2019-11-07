package org.djog_unos.tankgame.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.djog_unos.tankgame.game.Box;
import org.djog_unos.tankgame.game.Bush;
import org.djog_unos.tankgame.game.TankGame;
import org.djog_unos.tankgame.game.Tree;
import org.lwjgl.opengl.GL;

import java.util.Iterator;

public abstract class Game 
{
	protected abstract void init();
    protected abstract void update();
	protected abstract void draw();

	private static Window window;
	private static double totalGameTime;
	private static double deltaTime = 1.0/60.0; // Default for testing
	private static double lastFrameTime;
	private long variableYieldTime, lastTime;

	
	protected void run(int width, int height, boolean fullscreen, String title, int maxFPS) {
		setupWindow(title, width, height, fullscreen);
		init();

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

			// Vsync / Wait for next frame
			vsync(maxFPS); 
		}

		glfwTerminate();
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

	public static boolean collide(float x, float y, TankGame game){
		int radius = 64;
        float distanceToCenter = (float) Math.sqrt((radius * radius) / 2);
        // Boxes
		Iterator<Box> i = game.m_boxes.iterator();
		while (i.hasNext()) {
		    Box box = i.next();
            float closestX = Math.max(box.get_x(), Math.min(box.get_x() + box.get_width(), x + distanceToCenter));
            float closestY = Math.max(box.get_y(), Math.min(box.get_y() + box.get_height(), y + distanceToCenter));

            float distanceX = x + 45 - closestX;
            float distanceY = y + 45 - closestY;

            float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
            if(distanceSquared < (radius * radius)) return true;
		}

		// Trees
		Iterator<Tree> j = game.m_trees.iterator();
		while (j.hasNext()) {
			Tree tree = j.next();
			float closestX = Math.max(tree.get_x(), Math.min(tree.get_x() + tree.get_width(), x + distanceToCenter));
			float closestY = Math.max(tree.get_y(), Math.min(tree.get_y() + tree.get_height(), y + distanceToCenter));

			float distanceX = x + distanceToCenter - closestX;
			float distanceY = y + distanceToCenter - closestY;

			float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
			if(distanceSquared < (radius * radius)) return true;
		}

		// Boxes
		Iterator<Bush> k = game.m_bushes.iterator();
		while (k.hasNext()) {
			Bush bush = k.next();
			float closestX = Math.max(bush.get_x(), Math.min(bush.get_x() + bush.get_width(), x + distanceToCenter));
			float closestY = Math.max(bush.get_y(), Math.min(bush.get_y() + bush.get_height(), y + distanceToCenter));

			float distanceX = x + distanceToCenter - closestX;
			float distanceY = y + distanceToCenter - closestY;

			float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
			if(distanceSquared < (radius * radius)) return true;
		}
		return false;
	}
}