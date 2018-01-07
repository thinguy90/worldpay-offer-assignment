# worldpay-merchant-offer-assignment
Worldpay Offer REST assignment 


Java solution using Springboot,using JPA with H2 as an in-memory database.

It was assumed that the merchant will not use any other ID other than the one allocated to them, using authentication 
would provide a way to ensure this validation could be put in place
  
Default port is 8001
Swagger can be accessed at http://localhost:8001/swagger-ui.html to interact with the API
 
 
Main entry point - MerchantOfferApplication
 
 
 POST - create a new offer
 http://localhost:8001/offers
 
     {
         "merchantId": 1,
         "status": "ACTIVE",
         "description": "My cool description",
         "timeToLiveMinutes": 15,
         "price": 550.95,
         "currency": "USD"
     }
     
     
PUT  - to update an existing offer
http://localhost:8001/offers

     {
         "id": 1,
         "merchantId": 1,
         "status": "ACTIVE",
         "description": "My cool description",
         "timeToLiveMinutes": 15,
         "price": 550.95,
         "createDateTime": "2018-01-07@15:17:35.849+0000",
         "currency": "USD"
     }



PATCH - to update only the status on an existing offer
http://localhost:8001/offers/{offer id}/status?newStatus=EXPIRED

     
