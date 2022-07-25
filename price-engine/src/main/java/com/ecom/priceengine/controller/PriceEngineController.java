package com.ecom.priceengine.controller;

import com.ecom.priceengine.bean.ProductPrice;
import com.ecom.priceengine.service.ProductEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceEngineController {

    @Autowired
    ProductEngineService productEngineService;

    @GetMapping("/price")
    public ResponseEntity<ProductPrice> getProductPrice(@RequestParam(name="title") String name){
        return new ResponseEntity<>(productEngineService.getProductPrice(name), HttpStatus.OK);
    }
}
