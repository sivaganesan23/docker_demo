FROM jenkins/jenkins:lts-alpine 
COPY password.groovy /usr/share/jenkins/ref/init.groovy.d/password.groovy
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

