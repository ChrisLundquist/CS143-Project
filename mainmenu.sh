#!/bin/sh

JAVA=/usr/lib/jvm/java-6-openjdk/bin/java

if [ `uname -m` = "x86_64" ]; then
    JOGL_PATH=./jogamp-dist/jogl-2.0-b23-20110303-linux-amd64
    JOAL_PATH=./jogamp-dist/joal-2.0-20110302-linux-amd64
else
    JOGL_PATH=./jogamp-dist/jogl-2.0-b23-20110303-linux-i586
    JOAL_PATH=./jogamp-dist/joal-2.0-20110302-linux-i586
fi

${JAVA} \
    -Djava.library.path=${JOGL_PATH}/lib:${JOAL_PATH}/lib \
    -classpath ./bin:${JOGL_PATH}/jar/gluegen-rt.jar:${JOGL_PATH}/jar/jogl.all.jar:${JOGL_PATH}/jar/nativewindow.all.jar:${JOGL_PATH}/jar/newt.all.jar:${JOAL_PATH}/jar/joal.jar \
    game.MainMenu
