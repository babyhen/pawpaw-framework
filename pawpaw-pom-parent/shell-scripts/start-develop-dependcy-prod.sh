#!/bin/sh

#通用的变量
dev_jmp_server=106.75.98.208
dev_jmp_server_user=liujx3

#杀掉已经启动的所有ssh本地端口转发
all_ssh_pid=`ps ax|grep ssh | grep  ${dev_jmp_server_user} |awk '{print $1}' `
echo "开始杀掉已经启动的ssh本地端口转发。。。。"
for i in ${all_ssh_pid};
  do kill -9 $i;
  echo "杀死了进程${i}"
done
echo "杀掉了所有已经启动的本地转发端口。。。。。"



 ssh  -f -L 6379:10.19.132.33:6379 -N ${dev_jmp_server_user}@${dev_jmp_server} -p 2222
