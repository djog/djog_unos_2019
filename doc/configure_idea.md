# Configure Intellij IDEA

Disclaimer: I'm not entirely sure if this is correct,
I'll just describe how my IDEA is configured.

### Add module

Most of the time this is done automaticly, but if not, go to:
File -> Project Structure -> Modules -> Add Content Root and then select 
the folder djog_unos_2019. Mark src as Sources

### Add Libraries

Go to File -> Project Structure -> Libraries, Press the + icon, and select
the lwjgl folder. Then press the + icon again and select the joml jar

### Create a configuration

Next to the green run button in the top right, you'll see "Add Configuration"
Click on it, and a new window will open. In the top left, select the + icon
and select Application. As the Main Class, select TankGame by clicking on the 
3 dots. If not done automatically, select java 12 as JRE.

### I think you're done!

### (Optional) Create a Test configuration

If you want to create a new configuration to run the test, click on your
configuration and select "Edit Configurations". Then click again on the + icon
-> Application. Instead of TankGame as Main Class, select TestRunner.
