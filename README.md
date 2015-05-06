data
====

Simple storage for timebased values.

Usage
-----

To start the service, use the following command:

    mvn spring-boot:run

Now you can create series:

    curl -i -X POST -H "Content-type: application/json" --data "{\"name\": \"Temp\"}" http://localhost:8080/series

To request all series, use:

    curl -i http://localhost:8080/series

To change the name of a series, use:

    curl -i -X PUT -H "Content-type: application/json" --data "{\"name\": \"Temperature\"}" http://localhost:8080/series/Temp

To push values to the Series, use:

    curl -X POST -H "Content-type: application/json" --data '{"value": 20}' http://localhost:8080/series/Temperature

To get all values, use:

    curl http://localhost:8080/series/Temperature

The resulting documents are filled with HATEOAS links, so it should be easy to understand the structure.
