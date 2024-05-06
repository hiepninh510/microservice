package com.example.product_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.product_service.models.ProductModel;
import com.example.product_service.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;


@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
      String responseData = "Hello";
      return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @RequestMapping
    @ResponseBody
      public List<ProductModel> getAllProducts() {
        return productService.findAllProducts();
    }

    // @GetMapping("/{id}")
    // @ResponseBody
    // public ProductModel sendToCart(@PathVariable String id) throws JsonProcessingException{
    //   return productService.sendToCartService(id);
    // }

    // @GetMapping("/{id}")
    // @ResponseBody
    // public ProductModel getByID(@PathVariable String id){
    //   return productService.getByID(id);
    // }

    @GetMapping("/get{id}")
    @ResponseBody
    public Optional<ProductModel> getByID(@PathVariable String id){
      return productService.getByID(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductModel sendProduct(@PathVariable String id) throws JsonProcessingException{
      return productService.senModel(id);

    }

    @GetMapping("/cart_service")
    @ResponseBody
    public String getCartProductsFromCartProductService() throws Exception {
      
      return productService.getCartProductsFromCartProductService();
  }
}


