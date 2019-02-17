FROM openjdk:12-jdk-alpine
WORKDIR /root/
COPY  target/app-1.0.2-REDIS.jar /root/
# COPY glowroot/ /root/glowroot
# CMD java -javaagent:/root/glowroot/glowroot.jar  -jar pingapp-1.0.0.jar
CMD java -jar app-1.0.2-REDIS.jar
