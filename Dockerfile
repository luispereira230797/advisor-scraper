# Stage 1: Build Maven project with JDK 11
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Stage 2: Build final image with Java 17 and application artifact
FROM ubuntu:20.04
RUN apt-get update -y \
    && apt-get -qqy dist-upgrade \
    && apt-get -qqy install software-properties-common gettext-base unzip wget curl \
    && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

# Install Google Chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
RUN apt-get update && apt-get install -y google-chrome-stable

# Install Chromedriver
RUN wget -q https://storage.googleapis.com/chrome-for-testing-public/126.0.6478.126/linux64/chromedriver_linux64.zip
RUN unzip chromedriver_linux64.zip -d /usr/local/bin
RUN chmod +x /usr/local/bin/chromedriver

# Install OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Check Java version
RUN java -version

# Copy application JAR from build stage
COPY --from=build /home/app/target/zp-api.jar /usr/local/lib/app.jar

EXPOSE 8080
ENV CHROMEDRIVER_PATH="/usr/local/bin/chromedriver"
ENV CUSTOM_GOOGLE_CHROME_BIN="/usr/bin/google-chrome"
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]

# docker build -t image/scraper:1 .
# docker run -p 8080:8080 image/scraper:1