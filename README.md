
# Challenge

## :computer: How to execute

### Easiest way


1. Clone repository from Github
2. Start the needed application with `docker-compose up`
3. Run the app
  ```
	$./mvnw spring-boot:run
  ```

### Docker-compse way
A docker-compose.yml file was already sent with the assignment. There is already a  Dockerfile inside the app but it is not multistage so you should get a maven build before using it. After that just add the assignment app to the Docker-compose. yaml.
 ```yaml
 springboot-app-assignment:  
  build : ./api  
  ports:  
    - 8080:8080  
  depends_on:  
    - postgress  
    - kafka-server
 ```

## :memo: Notes

### Project Structure
The application has a pretty classic Spring Boot folder structure.
```
+---config
|	+---application.yaml
|
+---src
	+---main
	|	+---java
	|	|	+---com.wefox.payment.api
	|	|    	+---client
	|	|    	+---configuration
	|	|    	+---enumeration
	|	|    	+---exception
	|	|    	+---factory
	|	|    	+---model
	|	|    	+---repository
	|	|    	+---service
	|	|    	+---util
	|	|    	+---ApiAplication.java
	|	+---resources 
	|		+---logback.xml
	|
	+---test
		+---java
			+---com.wefox.payment.api
				+---factory
				+---service
				+---ApiAplicationTests.java
```
#### Configuration
Spring Beans can be very powerful. I used Spring beans to configure my application.

#### Config
Spring looks for application configurations at certain locations. I created a config folder and put the application.yaml inside it. With this, we don't need to build our application every time a configuration changes. Also, it can be helpful in cloud environments and distributed systems.

#### Client
Contain an abstract class that has simple restTemplate implementation other client class extends from this abstract class.

#### Log
Normally I preferred to use Logstash but due to project requirements and environment, I created PaymentErrorLogger. I used the static log method of PaymentErrorLogger for the entire project. But It is not the correct approach and should use in a Cloud environment.

#### Persistent
I used spring-boot-starter-data-jpa, which contains Spring Data JPA and Hibernate Core. Spring Data Jpa has very useful production level futures.

I also used @Transactional so if something went wrong payment saving process or Account info update, operations are rolling back.

#### Serialize and Deserialize Java Object
Jackson is used at spring boots as default. I leveraged its annotations to serialize models.

#### Lombok
Lombok also used at the project

#### Testing
jUnit5 and Mochito used to demostrate simple tests. But I don't enough time to write all the required tests.

> ***Note:** If you check the comments on the project there are some explanations why I use that approach. Like error, time complexity,
> etc.*


## :pushpin: Things to improve


1. More tests are needed.
2. @UpdateTimestamp,@PrePersist, or @Builder.DEFAULT didn't work and
   its workaround pollutes the service layer. Should be investigated
   why they didn't work.
3. Loggin definitely should be improved and implemented something like
   Elasticsearch, Logstash, and Kibana. But every Cloud solution has
   its implementation so maybe just Logstash be enough.
4. There is a comment-outed multistage build code at Dockerfile. It
   could be activated for CI/CD pipelines.
