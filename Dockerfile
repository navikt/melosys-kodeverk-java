FROM navikt/java:8

# Må installere Maven
RUN curl -SL http://apache.uib.no/maven/maven-3/3.6.1/binaries/apache-maven-3.6.1-bin.tar.gz \
    | tar -xzv -C /opt/ \
    && rm -rf apache-maven-3.6.1-bin.tar.gz
ENV PATH=/opt/apache-maven-3.6.1/bin:$PATH

# Kopierer over settings-filen vi trenger for å deploye
COPY .circleci/settings.xml /settings.xml

# Selve applikasjonen...
COPY melosys-kodeverk-generator/target/melosys-kodeverk-generator-1.0.0-SNAPSHOT.jar /app.jar
