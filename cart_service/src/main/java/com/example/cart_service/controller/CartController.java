package com.example.cart_service.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.cart_service.models.CartModel;
import com.example.cart_service.service.CartService;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cart;
 
    public CartController(CartService cart){
        this.cart=cart;
    }

    @RequestMapping
    @ResponseBody
    public List<CartModel> getAll_Product_Into_Cart(){
        return cart.getAll_Product_Into_Cart();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public CartModel changeQuantity(@PathVariable String id,@RequestParam int soluong){
        return cart.changeQuantity(id,soluong);
    }
}
