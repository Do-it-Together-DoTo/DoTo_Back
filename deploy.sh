#!/bin/bash
pid=$(pgrep -f doto)
if [ -n "${pid}" ]
then
        kill -15 ${pid}
        echo kill process ${pid}
else
        echo no process
fi
chmod +x ./DoTo_Back/doto-0.0.1-SNAPSHOT.jar
nohup java -jar ./DoTo_Back/doto-0.0.1-SNAPSHOT.jar >> /home/ubuntu/deploy.log 2>/home/ubuntu/deploy_err.log &