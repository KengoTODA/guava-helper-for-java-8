FROM sonarqube:7.9-community

ENV SONAR_FINDBUGS_VERSION=3.11.0
ENV GUAVA_HELPER_VERSION=1.2.1-SNAPSHOT

RUN wget -O $SONARQUBE_HOME/extensions/plugins/sonar-findbugs-plugin-$SONAR_FINDBUGS_VERSION.jar --no-verbose https://github.com/spotbugs/sonar-findbugs/releases/download/$SONAR_FINDBUGS_VERSION/sonar-findbugs-plugin-$SONAR_FINDBUGS_VERSION.jar
COPY target/guava-helper-sonarqube-plugin-$GUAVA_HELPER_VERSION.jar $SONARQUBE_HOME/extensions/plugins/
