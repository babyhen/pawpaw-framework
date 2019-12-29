#!/bin/sh

#通用的变量
dev_jmp_server=106.75.126.100
dev_jmp_server_user=liujx3

#杀掉已经启动的所有ssh本地端口转发
all_ssh_pid=`ps ax|grep ssh | grep  ${dev_jmp_server_user} |awk '{print $1}' `
echo "开始杀掉已经启动的ssh本地端口转发。。。。"
for i in ${all_ssh_pid};
  do kill -9 $i;
  echo "杀死了进程${i}"
done
echo "杀掉了所有已经启动的本地转发端口。。。。。"


#qa3环境的mysql本机端口转发
 ssh  -f -L 3364:172.16.2.176:3364 -N ${dev_jmp_server_user}@${dev_jmp_server}


#小学服务端开发mysql本机端口转发
# ssh  -f -L 3309:172.16.2.27:3308 -N ${dev_jmp_server_user}@${dev_jmp_server}






#启动本地的Eureka
all_eureka_pid=` ps -ef|grep "registry-server"|grep -v "grep" |awk '{print $2}' `
echo "开始杀掉已经启动的Eureka。。。。"
for i in ${all_eureka_pid};
  do kill -9 $i;
  echo "杀死了进程${i}"
done
echo "杀掉了所有已经启动的Eureka。。。。。"
cd ~/scripts
 nohup  java -jar registry-server-1.0.0.jar  & 


# 开启course 服务
all_course_service_pid=` ps -ef|grep "course-app"|grep -v "grep" |awk '{print $2}' `
echo "开始杀掉已经启动的course service。。。。"
for i in ${all_course_service_pid};
  do kill -9 $i;
  echo "杀死了进程${i}"
done
echo "杀掉了所有已经启动的course service。。。。。"
cd ~/scripts
  nohup  java -jar /Users/liujixin/knowbox/course-service/course-app/target/course-app.jar  &



#开启订单服务
all_order_service_pid=` ps -ef|grep "order-app"|grep -v "grep" |awk '{print $2}' `
echo "开始杀掉已经启动的order service。。。。"
for i in ${all_order_service_pid};
  do kill -9 $i;
  echo "杀死了进程${i}"
done
echo "杀掉了所有已经启动的order service。。。。。"
cd ~/scripts
  nohup  java -jar /Users/liujixin/knowbox/order-service/order-app/target/order-app.jar  &
