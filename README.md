seasons
=======

Configuring the build path:
1) Navigate to Project > Properties > Java Build Path
2) Switch to the Libraries tab
3) Click "Add External JARs" and add all of the .jar files in /lib (except the box2d javadoc)
4) Click on the arrow next to lwjgl.jar
5) Double click "Native library location"
6) Select the folder in /lib/lwjgl-natives that corresponds to your system
8) Click on the array next to jbox2d....jar
9) Edit javadoc location.
10) Select "Javadoc in archive" and "Workspace file"
11) Select "jbox2d-library-2.1.2.3-javadoc.jar"

git first steps:
git checkout -b staging origin/staging
That will checkout the staging branch and switch you to it, so from then on you can work from there.