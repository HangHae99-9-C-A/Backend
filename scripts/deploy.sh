REPOSITORY=/home/ubuntu/app/deploy
cd $REPOSITORY

APP_NAME=LoginLiveSession2-0.0.1-SNAPSHOT.jar

JAR_NAME=$(ls $REPOSITORY | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ] #2
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 10
fi

echo "> $JAR_PATH 배포" #3
nohup java -jar /home/ubuntu/app/deploy/LoginLiveSession2-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
