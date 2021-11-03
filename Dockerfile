FROM maven:3.8.3-openjdk-11-slim

ENV PROJ_DIR /build
ENV MAIN_CLASS_FQDN ru.spbstu.telematics.gavrilov.lab01.Application
ENV CLASSPATH $PROJ_DIR/target/classes

VOLUME $PROJ_DIR
WORKDIR $PROJ_DIR

CMD mvn package; java $MAIN_CLASS_FQDN