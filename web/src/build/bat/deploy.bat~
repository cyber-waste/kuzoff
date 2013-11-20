@echo off

if ["%CATALINA_HOME%"] == [] (
    echo CATALINA_HOME must be specified
    exit 1
)
echo CATALINA_HOME=["%CATALINA_HOME%"]

call "%CATALINA_HOME%\bin\shutdown.bat"

rd /S /Q "%CATALINA_HOME%\webapps\kuzoff-ws"
del /F /Q "%CATALINA_HOME%\webapps\kuzoff-ws.war"

copy build\libs\kuzoff-ws.war "%CATALINA_HOME%\webapps"
