#!/bin/bash

# 需要先安装expect ---    brew install expect
#step1:  auto package project
#step2:  translate jar package to remote running server
#step3:  stop node
#step4:  start node
source /etc/profile

if [ $# -eq 0 ]; then
  echo "parameter project dir is missing .."
  exit 1
fi

REMOTE_SERVER_IP=pawpawtech.com
REMOTE_SERVER_SSH_PORT=22
REMOTE_SERVER_USER=root
REMOTE_SERVER_PASSWORD=HelloWorld123

REMOTE_ROOT_DIR=/home/servers

JAVA_OPS="-Xms256m -Xmx256m"



#打包和编译工程

 mvn clean package -Dmaven.test.skip=true -f $1
if [ $? -ne 0 ]; then
  echo "打包工程失败..."
  exit 1
fi


#进入目录
cd $1


# 判断工程是单模块还是多模块,如果是多模块，则进入对应的目录
module_dir=
packaging=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.packaging}' --non-recursive exec:exec 2>/dev/null)
echo "打包类型是$packaging"

if [ ${packaging} == "pom" ]; then
   # 多模块，找到对应的应用服务的子目录
   modules=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.modules} ""' --non-recursive exec:exec 2>/dev/null)
   modules=${modules//[/}
   modules=${modules//]/}
   modules=${modules//,/ }
   
   echo "模块列表 ${modules}"
   for m in ${modules} 
     do
        echo "分析模块$m"
        if [ $m != "*contract*"  ]; then 
            module_dir="$1/$m"
        fi
        break
     done 
     
elif [ ${packaging} == "jar" ] ; then
   #单模块
   module_dir=$1

else
   # 未知模块
   echo "获取模块路径失败。。。。" 
   exit 1
fi

echo "最终的模块位置${module_dir}"
if [ -z "${module_dir}" ];then
   echo "模块名为空，退出....."
   exit 1
fi 

cd ${module_dir}


echo "<------------步骤1:打包工程---------->"

# 得到maven工程的artifactid
artifact_id=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.artifactId}' --non-recursive exec:exec 2>/dev/null)
echo "工程的 artifactid 是 ${artifact_id}"





echo "<-------------步骤2: 找到目标jar文件------------->"

cd ./target
jar_file=`ls *.jar|grep -v -E source|head -n1`
if [ -z "jar_file" ] ; then
  echo "jar文件不存在，退出..."
  exit 1
fi

echo "user jar file $jar_file"


echo "<-------------步骤3: 清理远程地址的旧文件和创建必要的目录,把jar包传上去------------->"
remote_server_dir=${REMOTE_ROOT_DIR}/${artifact_id}
remote_server_jar_file=${remote_server_dir}/${jar_file}

echo "***** 创建远程必要的目录,移除旧文件*******"
create_dir_cmd="mkdir -p ${remote_server_dir}"
rename_jar_cmd="cd ${remote_server_dir} ; mv ${jar_file} ${jar_file}.old"


expect <<EOF
   spawn ssh -p${REMOTE_SERVER_SSH_PORT}  ${REMOTE_SERVER_USER}@${REMOTE_SERVER_IP} "${create_dir_cmd} ; ${rename_jar_cmd}"
   expect { 
        " (yes/no)? " {send "yes\r";exp_continue}
        "password:" {send "${REMOTE_SERVER_PASSWORD}\r";} 
        } 
   expect eof 
EOF


echo "**********开始传输文件到${REMOTE_SERVER_IP}"

expect <<EOF
   spawn scp -P $REMOTE_SERVER_SSH_PORT $jar_file ${REMOTE_SERVER_USER}@${REMOTE_SERVER_IP}:${remote_server_jar_file}
   expect { 
        " (yes/no)? " {send "yes\r";exp_continue}
        "password:" {send "${REMOTE_SERVER_PASSWORD}\r"; exp_continue} 
        "100%" {}
        "%"  {exp_continue}
         
           } 
    
   expect eof 
EOF

echo "<-------------步骤4: 关闭远程服务------------->"
echo "******** 关闭远程服务************"
echo "*********解析服务端口号**********"
cd ${module_dir}
APP_SERVER_PORT="8080"

APP_CONFIG_FILE=./src/main/resources/application.properties
echo "application config file is ${APP_CONFIG_FILE}"

if [ -a ${APP_CONFIG_FILE} ]; then
   CUSTOM_SRVER_PORT=`cat ${APP_CONFIG_FILE}|grep "server.port"|grep -v "\\${server.port}"`
  #  CUSTOM_SRVER_PORT="server.port=1000"
   echo "${CUSTOM_SRVER_PORT}"
      if [ -n ${CUSTOM_SRVER_PORT} ]; then
          APP_SERVER_PORT=${CUSTOM_SRVER_PORT#*=}
         
      fi

fi 

echo "----------final app port ${APP_SERVER_PORT} --------------"

SHUTDOWN_URL="http://${REMOTE_SERVER_IP}:${APP_SERVER_PORT}/application/shutdown"
echo "调用关闭地址 ${SHUTDOWN_URL}"
curl -X POST "${SHUTDOWN_URL}"

echo -e "\r\n"




startcmd="java ${JAVA_OPS}  -jar ${remote_server_jar_file} >> /dev/null &" 

echo "use start command : ${startcmd}"

expect <<EOF
   spawn  ssh -p${REMOTE_SERVER_SSH_PORT}  ${REMOTE_SERVER_USER}@${REMOTE_SERVER_IP} "source /etc/profile ;${startcmd}"
   expect { 
          " (yes/no)? " {send "yes\r";exp_continue}
          "password:" {send "${REMOTE_SERVER_PASSWORD}\r"} 
          } 
   expect eof 
EOF
