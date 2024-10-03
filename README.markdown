# Java Assignment 1
## Product price calculator
This is a proposed solution for Java Assigment 1 task.

## Business assumptions
1. There are two global discount policies: percentage-based, amount-based. I assumed that there will be no scenario when two distinct percentage/amount-based policies are necessary in the same time.
2. Every product can have only one discount policy
3. Endpoint for calculating product price should give an array of possible discount, or specific discount based on given amount. This gives as a possiblity to show discount like below:
![img.png](img.png)
4. Product endpoint is customer facing. It's accessible for everyone
5. Discount endpoints can be accessed only from some kind of admin-panel

## Technical assumptions
1. I decided to divide an application into two main packages: api and domain. In real life scenario I could also create another package for persistence, so domain objects won't be aware of JPA abstraction.
2. I used packages instead of separate gradle modules because of application scale. I think right now it's enough to separate domain and api.
3. I wrote unit test for crucial classes that contains business logic. All other simple classes are tested by integration tests.
4. I wrote integration tests to cover all accessible endpoints.
5. I purposely avoided adding spring security and setting discount endpoints as secured, to maintain simplicity of an application.
6. Database used in project is H2. In real life scenario I would db existing in infrastructure like oracle, postgres etc. :D
7. I used @Cacheable to create cache for product and discounts, so there will be no unnecessary database calls.
8. Cache is evicted only for discount because there is no way to change products state.
9. In Discount API there is still a possibility to confuse a user because state that user will see can be stale. There is no connection between backend - frontend to notify frontend that another user modified discounts in the same time. To prevent that we can try to use some kind of optimistic locking with the version passed as a header. Maybe ETag. 
10. I Decided to duplicate validation for Discount in api module and domain module to be safe at scenario when in future discount will be created by other source like apache kafka, or something else.
11. Behaviour of Discount endpoint requires to pass all discounts and changes that were made. If we pass only one discount all previous ones will be removed.

## How to run
1. Build an application. I'm using gradle as a build tool. You dont need to install gradle. It uses gradle wrapper. Execute command: `./gradlew clean build`
2. From this point you can either run this from your IDE (**in this case application runs on 8080 port**), or follow to next part to build and run docker image.
3. Build a docker image. Execute command: `docker build --tag=product-discount:latest .`
4. Run docker container. Execute command: `docker run -p8887:8080 product-discount:latest`
5. Now your application is running on 8887 port.

* For Windows I created shell script `buildAndRun.sh` that runs all commands.

## How to access
1. To discover API go to: `http://localhost:8887/swagger-ui.html`
2. To access H2 DB go to: `http://localhost:8887/h2-console`

## Predefined state
Application uses liquibase to create database schema. It will also insert test data into both tables. To disable this behaviour you need to remove file `resources/db/changelog/example-data.sql` and modify `resources/db/changelog/db.changelog-master.yaml`. 