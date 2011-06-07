REM @ECHO OFF

IF EXIST "%programfiles(x86)%" (GOTO 64-Bit) ELSE (GOTO 32-Bit)

:32-Bit

set JAVA="C:\Program Files\Java\jre6\bin\java.exe"
set JOGL_PATH=.\jogamp-dist\jogl-2.0-b23-20110303-windows-i586
set JOAL_PATH=.\jogamp-dist\joal-2.0-20110302-windows-i586

GOTO RUN

:64-Bit

IF EXIST "C:\Program Files\Java\jre6" (GOTO 64-BitJava) ELSE (GOTO 32-BitJava)

:32-BitJava

set JAVA="C:\Program Files (x86)\Java\jre6\bin\java.exe"
set JOGL_PATH=.\jogamp-dist\jogl-2.0-b23-20110303-windows-i586
set JOAL_PATH=.\jogamp-dist\joal-2.0-20110302-windows-i586

GOTO RUN

:64-BitJava

set JAVA="C:\Program Files\Java\jre6\bin\java.exe"
set JOGL_PATH=.\jogamp-dist\jogl-2.0-b23-20110303-windows-amd64
set JOAL_PATH=.\jogamp-dist\joal-2.0-20110302-windows-amd64

GOTO RUN


:RUN
%JAVA% ^
  -Djava.library.path=%JOGL_PATH%/lib;%JOAL_PATH%/jar ^
  -classpath .\bin;%JOGL_PATH%/jar/gluegen-rt.jar;%JOGL_PATH%/jar/jogl.all.jar;%JOGL_PATH%/jar/nativewindow.all.jar;%JOGL_PATH%/jar/newt.all.jar;%JOAL_PATH%/jar/joal.jar ^
  game.MainMenu
