#!/bin/sh
echo "Building Silent Client for MC 1.21.11... first build takes a few minutes."
chmod +x gradlew
./gradlew build && echo "DONE! Jar is at build/libs/silent-client-1.0.0.jar"
