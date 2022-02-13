FROM maven:3.8.4-jdk-11 AS build

WORKDIR /home/wiremock

COPY . .

#RUN mvn clean install -DskipTests

FROM wiremock/wiremock AS final

COPY --from=build /home/wiremock/target/async-send-sqs-wiremockextension-0.0.1.jar /var/wiremock/extensions/wiremock-extension.jar

