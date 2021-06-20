# yelp-review
An API that gets the user and Reviews of a place using the Yelp API

To run the app:
1. Be sure you have maven installed in your machine.
2. Clone the source.
3. Open a command line/git bash in the directory 
4. Run "mvn clean spring-boot:run" 


To view the API go to 
http://{host}:{port}/yelp-review/swagger-ui.html


To get the users and their reviews:
1. Get the id of the store or business, this is done thru 

GET 
http://{host}:{port}/yelp-review/search-business/{location}

The response will contain the alias of the store/business and the Id, you will need this Id to search for the review.


2. To get the review, get the businessId from step one and do a search 

GET
http://{host}:{port}/yelp-review/reviews/{businessId}

The response will give you 3 reviews with their user and their review.

