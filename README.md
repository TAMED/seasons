seasons
=======

Configuring the build path:
1) Navigate to Project > Properties > Java Build Path
2) Switch to the Libraries tab
3) Click "Add External JARs" and add all of the .jar files in /lib
4) Click on the arrow next to lwjgl.jar
5) Double click "Native library location"
6) Select the folder in /lib/lwjgl-natives that corresponds to your system

Exporting as an executable
1) Go to File > Export
2) Select Java > Runnable JAR file, and click Next
3) Set the launch configuration
4) Set the export destination to the project folder
5) Make sure the Library Handling is set to "Extract required libraries into generated JAR"
6) Click Finish
7) Run "bash export.sh" in a terminal (or a terminal emulator like Cygwin on Windows). If you don't have acces to one, then just manually perform the steps in export.sh