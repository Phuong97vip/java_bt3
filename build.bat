@echo off
echo Building Dictionary Application...

:: Create directories if they don't exist
if not exist "bin" mkdir bin
if not exist "release" mkdir release

:: Compile Java file with XML library
javac -cp ".;Libs/*" -d bin Source/*.java

:: Create manifest file
echo Manifest-Version: 1.0 > manifest.txt
echo Main-Class: DictionaryApp >> manifest.txt
echo Class-Path: Libs/* >> manifest.txt

:: Create JAR file
jar cfm release/dict.jar manifest.txt -C bin .

:: Copy data files to release directory
copy data\*.xml release\

echo Build completed successfully!
echo You can run the application using: java -jar release/dict.jar 