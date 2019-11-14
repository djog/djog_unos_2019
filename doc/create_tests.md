# How to use the unit tests

## 1. Setup the test class

First create a new file called `ExampleTest.java`.

Then add this code & replace example with the name of your test:

```java
package org.djog_unos.tankgame.unittest;

public class ExampleTest {

    @Test
    public void testExample() {
        // Your test here
    }
}
```

Go to TestRunner.java and add your class:

```java
Result result = JUnitCore.runClasses(
    // ... (other tests here)
    // Add your class to this list
);
```

## 2. Create the test

Create the test in your test function. Example to use asserts:

```java
// Make sure to import assert
import static org.junit.Assert.*;

// Assert that x is y with a delta of 5
assertEquals(x, y, 5);

// Assert that an statement is true
assertTrue(x == y);

// Assert that x is not null
assertNotNull(x);
```

## 3. Run the test

Run the main class in `TestRunner.java`. If all tests have been successfully completed then you are done!
If not, make sure your test works properly before commiting changes.
