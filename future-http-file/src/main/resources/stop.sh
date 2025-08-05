#!/bin/bash
echo "Input process name first"

NAME='HttpOauthMain'
echo $NAME

PID=`ps -ef | grep "$NAME" | grep -v "grep" | awk '{print $2}'`

echo "PDI =[ $PID ] ---------------"

if [ $? -eq 0 ]; then
    echo "process id:$PID"
else
    echo "process  $NAME not exit"
    exit
fi


kill -9 ${PID}

if [ $? -eq 0 ];then
    echo "kill  $NAME success"
else
    echo "kill  $NAME fail"
fi

echo "PDI =[ $PID ] --------end-------"