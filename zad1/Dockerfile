FROM docker.io/library/perl:latest as perlstage

RUN cpanm CGI
RUN cpanm Date::Calc

FROM docker.io/library/openjdk:15 as jdkstage

FROM docker.io/library/node:14-slim as nodestage

FROM httpd

RUN apt --yes update
RUN apt --yes upgrade
RUN apt --yes install mono-mcs
RUN apt --yes install gcc
RUN apt --yes install libcgi-pm-perl libdate-calc-perl

COPY ./httpd.conf /usr/local/apache2/conf/httpd.conf

COPY --from=perlstage /usr/local/bin/ /usr/local/bin/
COPY --from=perlstage /usr/local/lib/ /usr/local/lib/
COPY --from=nodestage /usr/local/bin/ /usr/local/bin/
COPY --from=nodestage /usr/local/lib/ /usr/local/lib/
COPY --from=jdkstage /usr/java/openjdk-15 /usr/local/java

RUN npm install get-stdin

ENV PATH="${PATH}:/usr/local/java/bin"
ENV JAVA_HOME="/usr/local/java/"
