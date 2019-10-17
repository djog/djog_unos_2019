package org.djog_unos.tankgame.unittest;

/*
Using JUnit 4
 */

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ShellTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("Failure: " + failure.toString());
        }

        System.out.println("Testing Shell was successful: " + result.wasSuccessful());

        result = JUnitCore.runClasses(TextureTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("Failure: " + failure.toString());
        }

        System.out.println("Testing Texture was successful: " + result.wasSuccessful());
    }
}