#!/usr/bin/env sh

set -e
readonly DIR=modules/app-spring/build/logs/gatling/$(uuidgen)
readonly LOG=$DIR/app-spring-gatling.log
mkdir -p "$DIR"
echo "Starting application and writing log to '${LOG}'"
./gradlew :app-spring:bootRun >"$LOG" 2>&1 &
readonly PID=$!
echo "Waiting for 30s"
sleep 30
echo "Running Gatling Simulations"
./gradlew :app-spring:gatlingRun
kill $PID
