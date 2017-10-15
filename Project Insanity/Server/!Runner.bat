@echo off
Title Project Insanity Server - Running...
:run
CLS
Java -Xmx1024m -cp bin;deps/poi.jar;deps/mysql.jar;deps/mina.jar;deps/slf4j.jar;deps/slf4j-nop.jar;deps/jython.jar;log4j-1.2.15.jar; server.Server
if %ERRORLEVEL% == 1 (
    call AutoBuilder.bat
)
goto run
pause