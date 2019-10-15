echo "hello"
TOMCAT_PID=`ps -ef | grep tomcat | grep -v grep | awk '{print $2}'`
echo $TOMCAT_PID
echo "杀死进程"
kill -9 $TOMCAT_PID
/edu/tomcat9/bin/startup.sh