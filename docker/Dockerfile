FROM ubuntu:14.04
MAINTAINER hyman
ENV REFRESHED_AT 2018-8-13
RUN apt-get -yqq update
RUN apt-get -yqq install tomcat7 default-jdk
ENV CATALINA_HOME /usr/share/tomcat7
ENV CATALINA_BASE /var/lib/tomcat7
ENV CATALINA_PID /var/run/tomcat7.pid
ENV CATALINA_SH /usr/share/tomcat7/bin/catalina.sh
ENV CATALINA_TMPDIR /tmp/tomcat7-tomcat7-tmp
RUN mkdir -p $CATALINA_TMPDIR
VOLUME ["/var/lib/tomcat7/webapps/"]
EXPOSE 8080 9000

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY docker ./docker
COPY target ./target
COPY target/Stage-1.0-SNAPSHOT.war /var/lib/tomcat7/webapps/

ENTRYPOINT ["/usr/share/tomcat7/bin/catalina.sh", "run" ]