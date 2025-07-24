#!/bin/bash

# Copyright 1999-2018 Alibaba Group Holding Ltd.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at

#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

cygwin=false
darwin=false
os400=false
case "`uname`" in
CYGWIN*) cygwin=true;;
Darwin*) darwin=true;;
OS400*) os400=true;;
esac
error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java
[ ! -e "$JAVA_HOME/bin/java" ] && unset JAVA_HOME

if [ -z "$JAVA_HOME" ]; then
  if $darwin; then

    if [ -x '/usr/libexec/java_home' ] ; then
      export JAVA_HOME=`/usr/libexec/java_home`

    elif [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]; then
      export JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
    fi
  else
    JAVA_PATH=`dirname $(readlink -f $(which javac))`
    if [ "x$JAVA_PATH" != "x" ]; then
      export JAVA_HOME=`dirname $JAVA_PATH 2>/dev/null`
    fi
  fi
  if [ -z "$JAVA_HOME" ]; then
        error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
  fi
fi

export SERVER="future.FileMain"
export MODE="cluster"
export SERVER_ENV="test"
export JAVA_OPT_EXT=""
export COMMAND=""
export RUN_MAIN=top.suven.future.FileMain

SERVER_PORT=29020



#===========================================================================================
# JVM Configuration
# prod ,dev ,test, stage
#===========================================================================================
while getopts ":e:s" opt
do
    case $opt in
        e)
          SERVER_ENV=$OPTARG;;
        s)
          COMMAND=$OPTARG;;
        ?)
        echo "Unknown parameter $opt , $OPTARG"
        exit 1;;
    esac
done


if [[ "$COMMAND" != "start" ]] && [[ "$COMMAND" != "stop" ]] && [[ "$COMMAND" != "restart" ]]; then
	echo "Usage: $0 start | stop | restart"
	exit 0
fi

export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=`cd $(dirname $0)/..; pwd`
export CUSTOM_SEARCH_LOCATIONS=file:${BASE_DIR}/etc/

#===========================================================================================
# JVM lib/*.jar
# prod ,dev ,test, stage
#===========================================================================================
# shellcheck disable=SC2045
for jar in `ls ${BASE_DIR}/lib/*.jar`
  do
      JAVA_OPT_EXT="JAVA_OPT_EXT:""$jar"
  done

#===========================================================================================
# JVM Configuration
# prod ,dev ,test, stage
#===========================================================================================
if [[ "${ENV}" == "dev" ]]; then
    JAVA_OPT="${JAVA_OPT} -Xms128m -Xmx256m -Xmn64m"
elif [[ "${ENV}" == "test" ]]; then
      JAVA_OPT="${JAVA_OPT} -Xms128m -Xmx256m -Xmn64m"
elif [[ "${ENV}" == "stage" ]]; then
      JAVA_OPT="${JAVA_OPT} -Xms256m -Xmx512m -Xmn128m"
else
    if [[ "${EMBEDDED_STORAGE}" == "embedded" ]]; then
        JAVA_OPT="${JAVA_OPT} -DembeddedStorage=true"
    fi
    JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
    JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/java_heapdump.hprof"
    JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages"
fi


JAVA_MAJOR_VERSION=$($JAVA -version 2>&1 | sed -E -n 's/.* version "([0-9]*).*$/\1/p')
if [[ "$JAVA_MAJOR_VERSION" -ge "9" ]] ; then
  JAVA_OPT="${JAVA_OPT} -Xlog:gc*:file=${BASE_DIR}/logs/future_gc.log:time,tags:filecount=10,filesize=102400"
else
  JAVA_OPT="${JAVA_OPT} -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${JAVA_HOME}/lib/ext"
  JAVA_OPT="${JAVA_OPT} -Xloggc:${BASE_DIR}/logs/future_gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
fi


JAVA_OPT="${JAVA_OPT} -Dfuture.server.home=${BASE_DIR}"
JAVA_OPT="${JAVA_OPT} ${JAVA_OPT_EXT}"
JAVA_OPT="${JAVA_OPT} --spring.config.additional-location=${CUSTOM_SEARCH_LOCATIONS}"
JAVA_OPT="${JAVA_OPT} --logging.config=${BASE_DIR}/etc/logback.xml"
JAVA_OPT="${JAVA_OPT} --server.max-http-header-size=524288"

if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

echo "$JAVA ${JAVA_OPT}"

# check the start.out log output file
if [ ! -f "${BASE_DIR}/logs/start.out" ]; then
  touch "${BASE_DIR}/logs/start.out"
fi
# start
echo "$JAVA ${JAVA_OPT}" > ${BASE_DIR}/logs/start.out 2>&1 &
nohup $JAVA ${JAVA_OPT} $SERVER >> ${BASE_DIR}/logs/start.out 2>&1 &
#exec -a $SERVER_NAME java -server $JVM_PARAM $ARGS -classpath "$CLASSPATH" $RUN_MAIN $SERVER_PORT --spring.profiles.active=$SERVER_ENV
function start()
{
    nohup exec -a ${SERVER_NAME} java -server ${JAVA_OPT}  ${RUN_MAIN} ${SERVER_PORT} --spring.profiles.active=${SERVER_ENV}
    echo "${SERVER_NAME} is starting，you can check the ${BASE_DIR}/logs/start.out"
    echo "--------服务启动成功--------"
    echo "--------欢迎使用suven 广州私服 CRM ^_^--------"
    tail -f ${BASE_DIR}/logs/start.log
}

function stop()
{

    PID=`ps -ef | grep -w  "${SERVER}" | grep -v "grep" | awk '{print $2}'`
    echo "PDI =[ $PID ] ---------------"
    if [ $? -eq 0 ]; then
        echo "process id:$PID"
    else
        echo "process  {SERVER} not exit"
        exit
    fi
    kill -9 ${PID}

    if [ $? -eq 0 ];then
        echo "kill  $NAME success"
    else
        echo "kill  $NAME fail"
    fi

    echo "PDI =[ $PID ] --------end-------"
}

if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "stop" ]]; then
    stop
else
    stop
    start
fi
