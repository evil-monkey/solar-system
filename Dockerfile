FROM tomcat:7.0.70-jre8

RUN apt-get update && apt-get install -y curl \
        vim
