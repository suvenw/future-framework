#!/bin/bash

PATH_LIB=./lib

CLASSPATH=./etc

JVM_PARAM='-Xms1g -Xmx1g -Xmn128m -Xss1m'

RUN_MAIN=top.suven.future.SysMain

SERVER_NAME=SysMain

SERVER_PORT=29020

SERVER_ENV=prod

for jar in `ls $PATH_LIB/*.jar`

do

      CLASSPATH="$CLASSPATH:""$jar"

done

exec -a $SERVER_NAME java -server $JVM_PARAM $ARGS -classpath "$CLASSPATH" $RUN_MAIN $SERVER_PORT --spring.profiles.active=$SERVER_ENV
