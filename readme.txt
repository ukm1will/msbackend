Had trouble deploying to the aws environment.

The following is a link to how I managed to create a jar file via maven:
https://stackoverflow.com/questions/38792031/springboot-making-jar-files-no-auto-configuration-classes-found-in-meta-inf


In the end I established the following facts:

The POM file needs the following:
  <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.App</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>


With a backend (@RestController) you need to create the JAR file via the command:

$ mvn clean package


You should establish that the JAR file produces the correct behaviour via the command:

$ java -jar target/msBackEnd-1.0-SNAPSHOT.jar

The correct behaviour in this instance being to start the Spring Boot application on port 8080.

To check the correct behaviour, open a browser and go to 'localhost:8080/hello'

You should see a message from the backend controller.

When deploying to AWS bear in mind the following:

Platform: Java 8 running on 64bit Amazon Linux/2.10.0
Environment Properties (Configuration) SERVER_PORT should be 5000


Note that the front end (that has view etc. runs on Tomcat 8.5 with Java 8 running on 64bit Amazon Linux/3.3.0)

http://msbackend.wkftwqs2fz.eu-west-2.elasticbeanstalk.com/