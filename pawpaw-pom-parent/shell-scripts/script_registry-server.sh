#!/bin/bash

application_home=$(cd $(dirname ${BASH_SOURCE[0]}); pwd)
echo "enter $application_home"
cd $application_home

jar_file=`ls *.jar|head -n1`
if [ -z "jar_file" ] ; then
  echo "jar文件不存在，退出..."
  exit 1
fi

echo "use executable jar file " $jar_file

# command is start---------------
if [ "$1" == "start" ] ; then
  java_ops="-Xms256m -Xmx256m"
  java $java_ops  -jar $jar_file  &
  echo "*************finish start server**************"

# command is stop
elif [ "$1" == "stop" ] ; then
  pid=`  ps -ef|grep java|grep $jar_file|awk '{print $2}'  `
     if [ -z "$pid" ]; then  
       echo "process doesn't exist , finish..."
       exit 0
     fi

  echo "going to stop pid $pid..."
  kill -9 $pid
  echo "**************finish kill pid $pid !!!****************8"
  exit 0

# others ,show useage infomation
else
  echo "useage: sh $0  start|stop"
  exit 0
fi



