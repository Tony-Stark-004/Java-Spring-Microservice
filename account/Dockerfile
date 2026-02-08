# Start with a base image containing Java Runtime
FROM openjdk:27-ea-oraclelinux9

# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER aditya.com
# Information around who maintains the image
LABEL "org.opencontainers.image.authors"="aditya.com"

# Add the application's jar to the image
COPY target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar 

# execute the application
ENTRYPOINT ["java", "-jar", "account-0.0.1-SNAPSHOT.jar"]