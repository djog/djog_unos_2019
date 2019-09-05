## Linux (Ubuntu like systems & Mint)

1. First test is Java is alreagy installed on you machine by running `java -version`
2. If not install Java by using the following command `sudo apt-get install default-jre`
3. Also you have to install the Java developer kit (JDK) by running the following command `sudo apt-get install default-jdk`
4. Try running the first commang again. If it works: Congrats, you've installed Java on your linux machine. Otherwise try troubleshooting and googling.
5. Enjoy Java or test it again as described in the steps below.

## Windows (64-bit)

1. Go to [the java for 64-bit windows download page](https://www.java.com/nl/download/windows-64bit.jsp)
and click the big red _accept & download_ button.

2. Install java.

3. Go to your system's environment variables and edit path.

4. Add `C:\Program Files\Java\jdk-12.0.2\bin` to the **top** of the path and click ok.\
_Note: install location might be slightly different from example_

## Testing your install

1. Open the cmd or terminal.

2. Type `java -version` to check your runtime version.\
Type `javac -version` to check your compiler version.

3. Copy the `hello_world.java` file from [here](https://github.com/djog/djog_unos_2019/tree/master/doc) and place it in an empty folder.

4. In the cmd run the following commands:
- `cd <folder_where_you_placed_hello_world.java>`
- `javac hello_world.java`
- `java hello_world`

5. If all went well the program should output `Hello world!`, 
if this is not the case make sure you followed all of the steps correctly or ask a more experienced programmer.
