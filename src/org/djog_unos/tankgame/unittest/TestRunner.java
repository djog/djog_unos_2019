package org.djog_unos.tankgame.unittest;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

/*
Using JUnit 4
 */

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.lwjgl.opengl.GL;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("\nRunning tests...");
        
        createTestContext();
        
        Result result = JUnitCore.runClasses(
            ShellTest.class, 
            TextureTest.class
        );

        System.out.println(result.wasSuccessful() ? 
        "\nAll tests completed succesfully!\n" : 
        "\nNot all tests were succesful :(\nMake sure they work before merging changes!\n");

        for (Failure failure : result.getFailures()) {
            System.out.println("Failure: " + failure.toString());
        }
    }
    
    static void createTestContext()
    { // Create an openGL context for testing
    	if(!glfwInit()) {
			System.err.println("Failed To Initialize!");
			System.exit(1);
    	}
        long window = glfwCreateWindow(100, 100, "Test", 0, 0);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }
}