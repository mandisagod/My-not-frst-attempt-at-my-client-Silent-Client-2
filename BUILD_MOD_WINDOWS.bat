@echo off
title Building Silent Client mod...
echo ============================================
echo   Silent Client - one-click builder
echo   For Minecraft 1.21.11 (Fabric)
echo ============================================
echo.
echo Checking for Java 21...
java -version 2>nul
if errorlevel 1 (
    echo.
    echo  Java was not found on this PC.
    echo  Please install Java 21 first - it's free and takes 1 minute:
    echo.
    echo    https://adoptium.net/temurin/releases/?version=21
    echo.
    echo  Download the .msi installer, run it, then double-click
    echo  this file again.
    echo.
    pause
    exit /b 1
)
echo.
echo Java found! Building the mod now.
echo The FIRST build downloads Minecraft libraries and can take
echo 3-10 minutes. After that it's fast. Please wait...
echo.
call gradlew.bat build
if errorlevel 1 (
    echo.
    echo Build failed - scroll up to see the error, or ask Claude for help.
    pause
    exit /b 1
)
echo.
echo ============================================
echo   DONE! Your mod jar is ready:
echo   build\libs\silent-client-1.0.0.jar
echo   Opening the folder for you now...
echo ============================================
start "" "build\libs"
pause
