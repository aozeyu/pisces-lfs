# 该镜像需要依赖的基础镜像
FROM apache/skywalking-java-agent:8.10.0-java8
# 设置环境变量
ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms512m -Xmx512m -Dfile.encoding=utf-8" SPRING_CONFIG="--spring.application.name=lfs-monitor"
# 设置时区
RUN set -eux; \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime; \
    echo $TZ > /etc/timezone \
# 拷贝jar包，并重命名
COPY ./lfs-monitor/target/lfs-monitor.jar /lfs-monitor.jar
# 指定docker容器启动时运行jar包
ENTRYPOINT exec java ${JAVA_OPTS} -jar ${SPRING_CONFIG} /lfs-monitor.jar
# 指定维护者的名字
MAINTAINER besscroft