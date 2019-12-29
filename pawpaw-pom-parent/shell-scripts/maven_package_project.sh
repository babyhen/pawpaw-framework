#!/bin/sh

source /etc/profile

file_path=`pwd`
echo "暂时使用当前目录$file_path"

#遍历参数，找到第一个不是sh结尾的参数，因为有些情况下，第一个参数是脚本的名字
if [ $# != 0  ] ; then 
   file_path=$0
   for i in $*
     do
       echo $i
       file_exp=${i##*.}
       echo $file_exp

       if [ $file_exp != "sh"  ] ; then
          file_path=$i
          echo "变更工程目录为${file_path}"
          break
       fi
    done
fi


echo "开始发布工程$file_path"
echo "**********************"  
mvn  clean  package   -Dmaven.test.skip=true -f ${file_path}
