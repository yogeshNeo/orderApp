# Getting Started

HIT POST API http://localhost:8080/actuator/refresh for refresh latest GitHub app's configuration

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.3/gradle-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* 
Redis Config

redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7006 --cluster-replicas 1

redis-server ./Documents/redis-cluster/7000/redis.conf
redis-server ./Documents/redis-cluster/7001/redis.conf
redis-server ./Documents/redis-cluster/7002/redis.conf
redis-server ./Documents/redis-cluster/7003/redis.conf
redis-server ./Documents/redis-cluster/7004/redis.conf
redis-server ./Documents/redis-cluster/7005/redis.conf

redis-server ./Documents/redis-cluster/7006/redis.conf
redis-server ./Documents/redis-cluster/7007/redis.conf


7006-master-Id
e56180f5419d2b55989b20e6ff6f6f88170030ef

redis-cli -p 7000 --cluster add-node 127.0.0.1:7007 127.0.0.1:7000 --cluster-slave --cluster-master-id e56180f5419d2b55989b20e6ff6f6f88170030ef

https://developer.redis.com/operate/redis-at-scale/scalability/exercise-1/

In Kafka directory
sudo bin/zookeeper-server-start.sh config/zookeeper.properties
sudo bin/kafka-server-start.sh config/server.properties
