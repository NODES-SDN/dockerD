FROM centos
MAINTAINER Fleuri - lauri.suomalainen@cs.helsinki.fi
RUN yum install -y java-1.7.0-openjdk
ADD decisionServer_with_SSL.jar /
ADD configurationFile /root/
# CMD [ "chmod", "a+rwx", "configurationFile" ]
# CMD [ "mv", "configurationFile", "~/" ]
ENTRYPOINT [ "java", "-jar", "decisionServer_with_SSL.jar", "15001", "4" ]
