package com.ecom.priceengine.service;

import com.ecom.priceengine.bean.ProductPrice;
import com.ecom.priceengine.dao.ProductEngineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductEngineService {

    @Autowired
    ProductEngineDao productEngineDao;

    public ProductPrice getProductPrice(String productName){
        List<ProductPrice> productPrices = (List<ProductPrice>) productEngineDao.findAll();
        ProductPrice priceOfProduct = findProductPrice(productPrices, productName);
        return priceOfProduct;
    }

    private ProductPrice findProductPrice(List<ProductPrice> productPrices, String productName) {
        return productPrices
                .stream()
                .filter(Objects::nonNull)
                .filter(productPrice -> productPrice.getTitle().equalsIgnoreCase(productName))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
