@echo off
set jars=%~dp0
java -jar %jars%target\init-1.0-SNAPSHOT-shaded.jar %*
echo on