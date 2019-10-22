package org.djog_unos.tankgame.unittest;

/*
Using JUnit 4
 */

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("\nRunning tests...");
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
    }