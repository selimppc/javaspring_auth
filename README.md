# Spring Boot Auth with JWT TOKEN and ROLES


### TOPIC CRUD API

| API           | HTTP Method | Response Code |
|---------------|-------------|---------------|
| signup        | POST        | 200           |
| signin        | POST        | 200           |
| test employee | PATCH / PUT | 200           |
| test admin    | DELETE      | 200           |

Note: postman collection is attached here "Spring-Boot-Auth.postman_collection.json"


### API URLS 

    = For signup =
    http://localhost:8080/api/auth/signup
    
    = For signin =
    http://localhost:8080/api/auth/signin

    = For Test Employee =
    http://localhost:8080/api/test/employee

    = For Test Admin = 
    http://localhost:8080/api/test/admin


    Postman Collection is attached here 
    find the file in root directory : "Spring-Boot-Auth.postman_collection.json"



### JAR File

    Clean MVN ( clean the target | download necessary dependencies | compile run the unit tests )
    and Create a JAR file and make that aviable in the project directory 

    $ mvn clean install   

            = JAR FILE =  
            /target/demo-0.0.1-SNAPSHOT.jar

    Run JAR FILE :
    $ java -jar target/demo-0.0.1-SNAPSHOT.jar



### Docker

    Building and Running the Container
    $ docker build -t javaspring_auth .

    Run the container
    $ docker run -it -p 8080:8080 javaspring_auth

    Utils:
    $ docker ps -a
    $ docker stop <container_id>



#### Optional:  Local environment Setup (Java and Maven) in MAC

    Need Maven to install 
    Download version 19 (for example ) from https://jdk.java.net/19/
    and Install as below:

    For Mac

    Downloading Java for Mac OS:
    $ tar -xvf openjdk-19.0.1_macos-x64_bin.tar.gz
    $ sudo mv jdk-19.0.1.jdk /Library/Java/JavaVirtualMachines/
    
    Setting Environment Variables - JAVA_HOME and Path
    $ JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-13.0.1.jdk/Contents/Home"
    $ PATH="${JAVA_HOME}/bin:${PATH}"
    $ export PATH

    Verifying the JDK installation
    $ java -version


    Maven for Mac OS  https://maven.apache.org/download.cgi
    $ tar -xvf apache-maven-3.8.6-bin.tar.gz
    
    Setting Maven Environment Variables - M2_HOME and Path
    $ export M2_HOME="/Users/pankaj/Downloads/apache-maven-3.6.3"
    $ PATH="${M2_HOME}/bin:${PATH}"
    $ export PATH

    Verifying the Maven Installation
    $ mvn -version     