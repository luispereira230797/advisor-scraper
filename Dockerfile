FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM ubuntu:20.04
RUN apt-get update -y \
    && apt-get -qqy dist-upgrade \
    && apt-get -qqy install software-properties-common gettext-base unzip wget \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/*

RUN apt-get update && apt-get install -y \
curl
CMD /bin/bash
# install google chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
RUN apt-get -y update
RUN apt-get -y update
RUN apt-get install -y google-chrome-stable

# install chromedriver
#RUN apt-get install -yqq unzip
#RUN wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/`curl -sS chromedriver.storage.googleapis.com/2.46`/chromedriver_linux64.zip
#RUN unzip /tmp/chromedriver.zip chromedriver -d /usr/local/bin/
RUN wget -q https://storage.googleapis.com/chrome-for-testing-public/126.0.6478.126/linux64/chromedriver-linux64.zip
RUN unzip chromedriver-linux64.zip -d /usr/local/bin
RUN chmod +x /usr/local/bin/chromedriver-linux64/chromedriver

# Install java
RUN wget -O- https://apt.corretto.aws/corretto.key | apt-key add -
RUN add-apt-repository 'deb https://apt.corretto.aws stable main'
RUN apt-get update; apt-get install -y java-11-amazon-corretto-jdk
RUN java -version

COPY --from=build /home/app/target/news-scraper.jar /usr/local/lib/app.jar
EXPOSE 8080
ENV CHROMEDRIVER_PATH="/usr/local/bin/chromedriver-linux64/chromedriver"
ENV CUSTOM_GOOGLE_CHROME_BIN="/usr/bin/google-chrome"
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]

# docker build -t image/scraper:1 .
# docker run -p 8080:8080 image/scraper:1