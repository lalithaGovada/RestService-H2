# price-engine

i) Service "PriceEngineApplication" : 
Is resposible for Pricing data for each product should be retrieved via an HTTP call. 
http://localhost:2022/price?title=Apple
http://localhost:2022/price?title=Orange
output : {"title":"Apple","price":0.61}
output : {"title":"Orange","price":0.25}


ii)H2 logic in "ProductEngineDao" to store data in temporty memory.
To test yaml : http://localhost:2022/h2-console 


iii) Step 1: Shopping cart -Code developed in "ShoppingCardApplicationTest"

● You are building a checkout system for a shop which only sells apples and
oranges.
● Apples cost 60p and oranges cost 25p.
● Build a checkout system which takes a list of items scanned at the till and outputs
the total cost
● For example: [ Apple, Apple, Orange, Apple ] => £2.05 ● Make reasonable assumptions about the inputs to your solution; for example, many
candidates take a list of strings as input

<lalitha> code is writtend in com.ecom.priceengine.ShoppingCardApplicationTest :: createCart()
                               com.ecom.priceengine.bean.Product
<lalitha> code is writtend in ShoppingCardApplicationTest :: createCart() ,  getPriceByProduct() & getCalculatedPrice()


/*--------------------------------------------------------------------------------------- */
Step 2: Simple offers - Code developed in "SimpleOffersApplicationTest"

● The shop decides to introduce two new offers ○ buy one, get one free on Apples ○ 3 for the price of 2 on Oranges
getCalculatePriceForBuyOneGetOne()
getCalculatePriceForBuyTwoGetOne()
