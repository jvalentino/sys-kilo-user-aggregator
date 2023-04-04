#!/bin/sh
HELM_NAME=sys-user-aggregator
PORT=8083

kubectl port-forward --namespace default svc/$HELM_NAME $PORT:8080 > build/$HELM_NAME.log 2>&1 &
curl http://localhost:$PORT/actuator/health

while [ $? -ne 0 ]; do
    kubectl port-forward --namespace default svc/$HELM_NAME $PORT:8080 > build/$HELM_NAME.log 2>&1 &
    curl http://localhost:$PORT/actuator/health
    sleep 5
done
