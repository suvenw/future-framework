#!/bin/bash

SERVICE_NAME="com.suven.framework.FileMain"
SERVICE_PATH=""

COMMAND="$1"

if [[ "$COMMAND" != "start" ]] && [[ "$COMMAND" != "stop" ]] && [[ "$COMMAND" != "restart" ]]; then
	echo "Usage: $0 | start | stop | restart"
	exit 0
fi

SERVICE_PATH=$(cd `dirname $0`; pwd)
RUN_SERVICE_PATH="${SERVICE_PATH}/${SERVICE_NAME}"

echo " ${RUN_SERVICE_PATH}"

if [ ! -d "${SERVICE_PATH}/logs" ]; then
  mkdir ${SERVICE_PATH}/logs
fi

echo " ${SERVICE_PATH}"

# check the start.out log output file
if [ ! -f "${SERVICE_PATH}/app.log" ]; then
  touch "${SERVICE_PATH}/app.log "
fi


function start()
{
    nohup ${SERVICE_PATH}/server.sh > /dev/null  2>&1 &

    echo "--------JAVA ${SERVICE_NAME} 服务启动成功--------"
    echo "--------欢迎使用 ${SERVICE_NAME}  广州团队微服 CRM ^_^--------"
    sleep 1
    tail -f ${SERVICE_PATH}/app.log

}

function stop()
{
	PID=`ps -ef | grep "${SERVICE_NAME}" | grep -v "grep" | grep "java"| awk '{print $2}'`

	echo "PDI =[ $PID ] ---------------"

	if [ $? -eq 0 ]; then
			echo "process id:$PID"
	else
			echo "process  ${SERVICE_NAME} not exit"
			exit
	fi


	kill -9 ${PID}

	if [ $? -eq 0 ];then
			echo "kill  ${SERVICE_NAME} success"
	else
			echo "kill  ${SERVICE_NAME} fail"
	fi

	echo "PDI =[ $PID ] --------${SERVICE_NAME}  服务已关闭 end --------------"
	sleep 3

}

if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "stop" ]]; then
  stop
else
    stop
    start
fi
