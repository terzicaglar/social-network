# Social Network API

A Spring Boot-based social network API that provides core social networking features including user management, posts,
likes, and visits including fraud detection.

## Tech Stack

- Java 17
- Spring Boot 3.4.5
- Spring Web MVC
- Spring JDBC
- Spring Validation
- H2 Database
- OpenAPI 3.0
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Git

### Installation

1. Clone the repository:

```bash
git clone https://github.com/terzicaglar/social-network.git
cd social-network
```

2. Build the project:

```bash
./mvnw clean install
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### API Documentation

Once the application is running, you can access the API documentation at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Fraud Detection Configuration

This application includes a **fraud detection mechanism** that flags users as fraudulent if they perform excessive
activity in a short period.

You can customize this behavior via the `application.properties` file:

```properties
# Maximum number of likes + visits allowed within the configured time window
fraud.detection.limit=100
# Time window (in minutes as an integer) used to evaluate user activity
fraud.detection.period_minutes=10
```

## Microservices Architecture Proposal

### Current Monolithic Structure Analysis

The existing monolithic application consists of the following core services and a single database:

- User Profile Service
- Like Service
- Visit Service
- Fraud Service
- Data Storage (Single H2 Database)

## Proposed Microservices Architecture

Above-mentioned services can be decomposed into microservices to improve scalability, maintainability, and fault
tolerance. Like Service and Visit Service can be merged into a single Interaction microservice, while the User Profile
Service and Fraud Service will remain as separate microservices.  
The proposed architecture will consist of the following components:

| Microservice                | Responsibilities                                  |
|-----------------------------|---------------------------------------------------|
| **User Service**            | CRUD for user profile                             |
| **Interaction Service**     | Likes and Visits                                  |
| **Notification Service**    | Trigger system events, including fraud detection  |
| **Fraud Detection Service** | Detect abnormal interaction patterns              |
| **API Gateway**             | Central entry point, routing, auth, rate limiting |

#### 1. Service Decomposition

1. **User Service**
    - Profile management
    - User-defined fields
    - Database: PostgreSQL

2. **Interaction Service**
    - Like functionality
    - Visit tracking
    - Database: Neo4j -> Graph database for social interactions for exploring friend/like/visit networks or recommend
      users

3. **Fraud Detection Service**
    - Interaction events from a message queue (such as Kafka or RabbitMQ)
    - Pattern recognition for fraud detection

4. **Notification Service (can be added)**
    - Real-time notifications
    - Email notifications
    - Push notifications
    - Message queue: RabbitMQ/Kafka

5. **API Gateway**
    - Request routing
    - Load balancing
    - Rate limiting
    - Authentication/Authorization

#### 2. Communication Mechanisms

1. **Synchronous Communication**
    - REST APIs for direct service-to-service communication
    - OpenAPI/Swagger for API documentation

2. **Asynchronous Communication**
    - Event-driven architecture using Apache Kafka
    - Message queues for reliable communication

#### 3. Data Management

1. **Database Per Service**
    - Each service maintains its own database
    - Eventual consistency where appropriate

2. **Data Consistency**
    - Saga pattern for distributed transactions
    - Event sourcing for audit trails
    - CQRS pattern for read/write separation

3. **Caching Strategy**
    - Redis for distributed caching
    - Service-level caching
    - CDN for static content, if needed

#### 4. API Versioning Strategy

1. **URL Versioning**
    - `/v1/users`
    - `/v2/users`
    - Clear version separation

2. **Backward Compatibility**
    - Deprecation policies
    - Version sunsetting
    - Migration tools

#### 5. Fault Tolerance and Resilience

1. **Circuit Breakers**
    - Fallback mechanisms

2. **Monitoring and Observability**
    - Distributed tracing (Zipkin)
    - Centralized logging (ELK Stack)
    - Metrics collection (Prometheus/Grafana)

3. **Resilience Patterns**
    - Retry mechanisms
    - Timeout handling
    - Rate limiting

#### 6. Security Considerations

1. **Authentication/Authorization**
    - OAuth 2.0 / OpenID Connect
    - JWT tokens
    - Role-based access control

2. **Service-to-Service Security**
    - Mutual TLS
    - API keys
    - Service mesh (Istio/Linkerd)

#### 7. Deployment Strategy

1. **Containerization**
    - Docker containers
    - Kubernetes orchestration
    - Helm charts for deployment

2. **CI/CD Pipeline**
    - Automated testing
    - Blue-green deployments
    - Canary releases
    - Feature flags

#### 8. Challenges and Solutions

1. **Data Consistency**
    - Eventual consistency with compensation
    - Distributed transactions using Saga pattern
    - Conflict resolution strategies

2. **Service Discovery**
    - Service registry (Eureka)
    - Dynamic service registration
    - Health checks

3. **Cross-Cutting Concerns**
    - Centralized configuration
    - Distributed tracing
    - Log aggregation
    - Metrics collection

4. **Scalability**
    - Horizontal scaling
    - Auto-scaling policies
    - Load balancing
    - Caching strategies

This microservices architecture proposal addresses the current monolithic application's limitations while providing a
scalable, maintainable, and resilient system architecture. The proposed structure allows for independent deployment,
scaling, and evolution of each service while maintaining system-wide consistency and reliability.

