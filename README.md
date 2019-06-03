For this Application to work,

You must have maven installed on your system.
To run this application, run main method in EcommerceApplication as a java application.

This microservice will run on port 8050 by default and if you want to change, you can change it in application.properties.
To Call the APIs, you need to call using postman like: localhost:8050/order/getAllOrders

I haven't added features to add new users or accounts(I have pre-created 4 users which you can see in schema.sql file).

You can either play with CRUD operations on orders and adding to place order for now new products to inventory.

I am using in-memory database H2, so you don't need any external database and datasource is configured.

To see database, open your browser and hit: http://localhost:8050/h2-console

    a. Enter JDBC URL: jdbc:h2:mem:testdb
    b. username: sa
    c. leave password blank.

    You can see the database now.


    Thanks.