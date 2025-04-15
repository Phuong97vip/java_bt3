@echo off
echo Building Simple Dictionary Application...

:: Create necessary directories
if not exist "bin" mkdir bin
if not exist "bin\data" mkdir bin\data

:: Compile all Java files
javac -d bin src\*.java

:: Copy data files
copy /Y data\*.xml bin\data\
copy /Y data\*.txt bin\data\

echo Build completed successfully!
echo.
echo Running the application...
echo.

:: Run the application
java -cp bin Main

pause 