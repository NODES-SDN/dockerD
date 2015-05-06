FROM centos
MAINTAINER Fleuri - lauri.suomalainen@cs.helsinki.fi
RUN yum install -y java-1.7.0-openjdk
ADD DummyDecisionServer.jar /
ADD configurationFile /root/
ADD DestIPAddresses /root/
ADD DestMacAddresses /root/
ADD SrcIPAddresses /root/
ADD SrcMacAddresses /root/
ADD add.sh /root/
ADD remove.sh /root/ 
ADD replace.sh /root/
RUN [ "chmod", "a+rwx", "/root/DestIPAddresses" ]
RUN [ "chmod", "a+rwx", "/root/DestMacAddresses" ]
RUN [ "chmod", "a+rwx", "/root/SrcIPAddresses" ]
RUN [ "chmod", "a+rwx", "/root/SrcMacAddresses" ]
RUN [ "chmod", "a+rwx", "/root/add.sh" ]
RUN [ "chmod", "a+rwx", "/root/remove.sh" ]
RUN [ "chmod", "a+rwx", "/root/replace.sh" ]
# CMD [ "mv", "configurationFile", "~/" ]
ENTRYPOINT [ "java", "-jar", "DummyDecisionServer.jar", "15001", "4" ]
