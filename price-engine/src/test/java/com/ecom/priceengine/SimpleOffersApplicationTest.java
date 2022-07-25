package com.ecom.priceengine;

import com.ecom.priceengine.bean.Product;
import com.ecom.priceengine.bean.ProductPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(classes = PriceEngineApplication.class)
@AutoConfigureMockMvc
class SimpleOffersApplicationTest {
	
	@Test
	void getProductPriceTest() {

		List<Product> cart = createFruitCart();
		System.out.println("\n" + " Simple Fruit offers " + "\n");

		BigDecimal cartTotal = cart.stream().map(product -> getPriceByProduct(product)).filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("Total = " + cartTotal);

	}

	private BigDecimal getPriceByProduct(Product product) {
		BigDecimal appleTotal = BigDecimal.ZERO;
		BigDecimal orangeTotal = BigDecimal.ZERO;

		OkHttpClient client = new OkHttpClient();

		String url = HttpUrl.parse("http://localhost:2022/price").newBuilder()
				.addQueryParameter("title", product.getName()).build().toString();

		Request request = new Request.Builder().url(url).build();

		Response response = null;
		String priceData = null;
		try {
			response = client.newCall(request).execute();
			priceData = response.body().string();

			ObjectMapper objectMapper = new ObjectMapper(); // used Jackson object mapper to "convert JSON String to
															// JavaObject"
			if (priceData.isEmpty() || null == priceData)
				return null;
			ProductPrice productPrice = objectMapper.readValue(priceData, ProductPrice.class);

			/** Buy one, get one free on Apple **/
			if ((!product.getName().isEmpty() || !product.getName().isBlank())
					&& product.getName().equalsIgnoreCase("Apple")) {
				appleTotal = getCalculatePriceForBuyOneGetOne(productPrice.getPrice(), product.getQuantity());
				
			}
			/** 3 for the price of 2 on Oranges **/
			if ((!product.getName().isEmpty() || !product.getName().isBlank())
					&& product.getName().equalsIgnoreCase("Orange")) {
				orangeTotal = getCalculatePriceForBuyTwoGetOne(productPrice.getPrice(), product.getQuantity());
			}
			System.out.println("Apple Total="+appleTotal+"  Orange Total="+orangeTotal);
			return appleTotal.add(orangeTotal);
		} catch (IOException e) {
			return null;
		}
	}

	private BigDecimal getCalculatePriceForBuyOneGetOne(double price, long quantity) {
		long eligOfferItem = quantity / 2;
		long notEligOfferItem = quantity % 2;
		BigDecimal eligOfferItemPrice = getCalculatedPrice(price, eligOfferItem);
		BigDecimal notEligOfferItemPrice = getCalculatedPrice(price, notEligOfferItem);
		return eligOfferItemPrice.add(notEligOfferItemPrice);

	}

	private BigDecimal getCalculatePriceForBuyTwoGetOne(double price, long quantity) {
		long eligOfferItem = quantity / 3;
		eligOfferItem = eligOfferItem * 2;
		long notEligOfferItem = quantity % 3;
		BigDecimal eligOfferItemPrice = getCalculatedPrice(price, eligOfferItem);
		BigDecimal notEligOfferItemPrice = getCalculatedPrice(price, notEligOfferItem);
		return eligOfferItemPrice.add(notEligOfferItemPrice);

	}

	private BigDecimal getCalculatedPrice(double price, long quantity) {
	
		return BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));
	}

	private List<Product> createFruitCart() {
		List<Product> products = new ArrayList<>();
		Product product = new Product("Apple", 8); // 1 for 1 offer
		Product product2 = new Product("Orange", 8); // buy 3 oranges but cost only for 2 oranges

		products.add(product);
		products.add(product2);
		return products;
	}
}
