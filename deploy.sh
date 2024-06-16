#!/bin/bash
# 파일 경로 설정
LOG_FILE="/home/ubuntu/deploy.log"
ERR_LOG_FILE="/home/ubuntu/deploy_err.log"

# 기존 파일 삭제
if [ -f "$LOG_FILE" ]; then
    rm "$LOG_FILE"
    echo "$LOG_FILE 삭제 완료"
fi

if [ -f "$ERR_LOG_FILE" ]; then
    rm "$ERR_LOG_FILE"
    echo "$ERR_LOG_FILE 삭제 완료"
fi

# doto 프로세스 종료
pid=$(pgrep -f doto)
if [ -n "${pid}" ]; then
    kill -15 ${pid}
    sleep 5
    if ps -p ${pid} > /dev/null; then
        kill -9 ${pid}
        echo "Force killed process ${pid}"
    else
        echo "Gracefully killed process ${pid}"
    fi
else
    echo "No process found"
fi

# 포트 8080 사용 중인 프로세스 종료
pid=$(lsof -t -i:8080)
if [ -n "${pid}" ]; then
    kill -15 ${pid}
    sleep 5
    if ps -p ${pid} > /dev/null; then
        kill -9 ${pid}
        echo "Force killed process using port 8080 (${pid})"
    else
        echo "Gracefully killed process using port 8080 (${pid})"
    fi
else
    echo "No process found using port 8080"
fi

# 새로운 애플리케이션 실행
chmod +x /home/ubuntu/doto-0.0.1-SNAPSHOT.jar
nohup java -jar -Duser.timezone=Asia/Seoul /home/ubuntu/doto-0.0.1-SNAPSHOT.jar >> /home/ubuntu/deploy.log 2>> /home/ubuntu/deploy_err.log &
