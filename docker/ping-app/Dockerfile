FROM openjdk:12-jdk-alpine
WORKDIR /root/
COPY  pingapp-1.0.0.jar /root/
# COPY glowroot/ /root/glowroot
# CMD java -javaagent:/root/glowroot/glowroot.jar  -jar pingapp-1.0.0.jar
CMD java -jar pingapp-1.0.0.jar
