FROM centos
RUN yum install httpd -y
RUN echo Hello >/var/www/html/index.html
CMD /usr/sbin/httpd -DFOREGROUND 
