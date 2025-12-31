![GitHub all releases](https://img.shields.io/github/downloads/mcasari/codingstrain/total)
![GitHub language count](https://img.shields.io/github/languages/count/mcasari/codingstrain)
![GitHub top language](https://img.shields.io/github/languages/top/mcasari/codingstrain?color=yellow)
![Bitbucket open issues](https://img.shields.io/bitbucket/issues/mcasari/codingstrain)
![GitHub forks](https://img.shields.io/github/forks/mcasari/codingstrain?style=social)
![GitHub Repo stars](https://img.shields.io/github/stars/mcasari/codingstrain?style=social)

# Coding Strain - Software Development Learning Repository




This repository contains comprehensive examples and tutorials for Spring Boot and Spring Cloud microservices development, along with Java programming tips and computer science algorithms.

## 📚 Repository Overview

This is a multi-module Maven project showcasing various aspects of modern Java development, with a focus on Spring Boot and Spring Cloud microservices architecture. Each module serves as a practical example accompanying articles on [codingstrain.com](https://codingstrain.com).

## 🏗️ Project Structure

### Core Spring Boot Examples

#### 1. **spring-boot-minimal-rest-h2**
- **Purpose**: Basic Spring Boot REST API with H2 database
- **Technologies**: Spring Boot, Spring Data JPA, H2 Database, Swagger
- **Features**: REST API development, database integration, API documentation
- **Articles**: [Spring Boot for Cloud – Configuration and Dependencies](https://codingstrain.com/spring-boot-for-cloud-configuration-dependencies/), [Spring Boot for Cloud – REST API Development](https://codingstrain.com/spring-boot-for-cloud-rest-api-development/)

#### 2. **spring-boot-minimal-rest-h2-actuator**
- **Purpose**: Spring Boot application with Actuator for monitoring
- **Technologies**: Spring Boot Actuator, health checks, metrics
- **Features**: Application monitoring, health endpoints, production-ready features
- **Articles**: [Spring Boot for Cloud – Actuator](https://codingstrain.com/spring-boot-for-cloud-actuator/)

### Spring Cloud Configuration Examples

#### 3. **spring-cloud-config-native-server**
- **Purpose**: Spring Cloud Config Server with native configuration
- **Technologies**: Spring Cloud Config Server
- **Features**: Centralized configuration management
- **Articles**: [Spring Cloud: How to Deal with Microservice Configuration (Part I)](https://codingstrain.com/spring-cloud-how-to-deal-with-microservice-configuration-part-i/)

#### 4. **spring-cloud-config-native-client**
- **Purpose**: Client application consuming native config server
- **Technologies**: Spring Cloud Config Client
- **Features**: Configuration consumption, dynamic configuration updates

#### 5. **spring-cloud-config-git-server**
- **Purpose**: Spring Cloud Config Server with Git backend
- **Technologies**: Spring Cloud Config Server, Git integration
- **Features**: Version-controlled configuration management

#### 6. **spring-cloud-config-git-client**
- **Purpose**: Client application consuming Git-based config server
- **Technologies**: Spring Cloud Config Client
- **Features**: Git-based configuration consumption

### Spring Cloud Service Discovery Examples

#### 7. **spring-cloud-discovery**
A comprehensive collection of service discovery examples including:
- **spring-cloud-discovery-server-localconfig**: Eureka server with local configuration
- **spring-cloud-discovery-server-localconfig-nozone**: Eureka server without zones
- **spring-cloud-discovery-gateway-localconfig**: API Gateway with service discovery
- **spring-cloud-discovery-client-localconfig**: Client applications with service discovery
- **spring-cloud-discovery-server-configfirst**: Server-first configuration approach
- **spring-cloud-discovery-server-discovfirst**: Discovery-first configuration approach
- **spring-cloud-discovery-configserver-configfirst**: Config server with config-first approach
- **spring-cloud-discovery-configserver-discovfirst**: Config server with discovery-first approach

**Articles**: [Spring Cloud: How to Implement Service Discovery (Part I)](https://codingstrain.com/spring-cloud-how-to-implement-service-discovery-part-i/), [Spring Cloud: How to Implement Service Discovery (Part II)](https://codingstrain.com/spring-cloud-how-to-implement-service-discovery-part-ii/)

### Complete Microservices Application

#### 8. **spring-cloud-sample-libraryapp**
A full-featured microservices library application demonstrating:
- **spring-cloud-sample-libraryapp-books**: Books microservice
- **spring-cloud-sample-libraryapp-authors**: Authors microservice
- **spring-cloud-sample-libraryapp-reviews**: Reviews microservice
- **libraryapp-discovery-gateway**: API Gateway
- **libraryapp-discovery-ssl**: SSL-enabled service discovery
- **libraryapp-discovery-oauth2**: OAuth2 integration
- **libraryapp-discovery-observability**: Observability and monitoring
- **libraryapp-openfeign-discovery**: OpenFeign client with service discovery
- **libraryapp-openfeign-discovery-circuitbreaker**: Circuit breaker patterns
- **libraryapp-messagedriven**: Event-driven architecture
- **libraryapp-testing-contract**: Contract testing
- **libraryapp-testing-pact**: Pact testing
- **libraryapp-testing-gatling-test**: Performance testing with Gatling

### Java Programming Examples

#### 9. **java-miscellaneous-tips**
Practical Java programming examples covering:
- **Articles**:
  - [Java Double: Parse String with Comma](https://codingstrain.com/java-double-parse-string-with-comma/)
  - [Java Map: Preserve Insertion Order](https://codingstrain.com/java-map-preserve-insertion-order/)
  - [Java Map: How to Get Sorted Keys](https://codingstrain.com/java-map-how-to-get-sorted-keys/)
  - [Java Object Copy Strategies: How to Create a Deep or a Shallow Copy of an Object in Java](https://codingstrain.com/how-to-create-a-deep-or-a-shallow-copy-of-an-object-in-java/)

### Computer Science

#### 10. **computer-science/algorithms**
Algorithms and data structures implementations in Java.

## 🛠️ Technology Stack

- **Java**: 8+ (with some modules using Java 17)
- **Spring Boot**: 2.7.5
- **Spring Cloud**: 2022.0.2
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **API Documentation**: Swagger/SpringFox
- **Service Discovery**: Eureka
- **Configuration Management**: Spring Cloud Config
- **API Gateway**: Spring Cloud Gateway
- **Testing**: JUnit, Pact, Gatling

## 🚀 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/mcasari/codingstrain.git
   cd codingstrain
   ```

2. **Build all modules**:
   ```bash
   mvn clean install
   ```

3. **Run individual modules**:
   Each module can be run independently. Navigate to the specific module directory and run:
   ```bash
   mvn spring-boot:run
   ```

## 📖 Learning Path

This repository is designed as a progressive learning path:

1. Start with **spring-boot-minimal-rest-h2** for basic Spring Boot concepts
2. Move to **spring-boot-minimal-rest-h2-actuator** for monitoring
3. Explore **spring-cloud-config-*** modules for configuration management
4. Study **spring-cloud-discovery** for service discovery patterns
5. Examine **spring-cloud-sample-libraryapp** for complete microservices architecture
6. Reference **java-miscellaneous-tips** for Java programming best practices

## 🔗 Related Resources

- **Blog**: [codingstrain.com](https://codingstrain.com)
- **Articles**: Each module links to detailed articles explaining the concepts and implementation

## 📄 License

This project is licensed under the terms specified in the [LICENSE](LICENSE) file.

---

**Note**: This repository serves as a comprehensive learning resource for Spring Boot and Spring Cloud microservices development, with practical examples accompanying detailed blog articles.
