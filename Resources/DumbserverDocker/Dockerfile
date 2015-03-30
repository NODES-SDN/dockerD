FROM centos
MAINTAINER Fleuri - lauri.suomalainen@cs.helsinki.fi
RUN yum install -y java-1.7.0-openjdk
ADD dummydecision.jar /
ENTRYPOINT ["java", "-jar", "/dummydecision.jar"]
