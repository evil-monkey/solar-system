#!/bin/bash

echo "Swagger UI set-up"

ln -s /code/deployment/swagger-ui/dist $CATALINA_HOME/webapps/swagger-ui

echo "Webapp set-up"

ln -s /code/service/target/wheater-predictor-service.war $CATALINA_HOME/webapps
ln -s /code/service/src/main/resources/conf/application.properties $CATALINA_HOME/conf/
ln -s /code/service/src/main/resources/applicationContext.xml $CATALINA_HOME/conf/
ln -s /code/service/src/main/resources/logback.xml $CATALINA_HOME/lib/
ln -s /code/docker-tools/setenv.sh $CATALINA_HOME/bin/setenv.sh

cd $CATALINA_HOME
catalina.sh run
