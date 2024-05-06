package com.example.cart_service.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.cart_service.models.CartModel;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@Component
public class CartService {
  private List<CartModel> cartModels = new ArrayList<CartModel>();

  
    public CartService(){
        
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CartModel convertJsonToObject(String json) throws IOException {
        return objectMapper.readValue(json, CartModel.class);
    }

    // @RabbitHandler
    // @RabbitListener(queues = "cart_queue")
    // @SendTo("reply-to")
    // public void reciveDataToProduct(String cart) throws IOException{
    //   // CartModel cartModel = convertJsonToObject(cart);
    //   // byte[] bytes = cart.getBytes(StandardCharsets.ISO_8859_1);
    //   // String decodedString = new String(bytes, StandardCharsets.UTF_8);
    //   // CartModel cartModel = convertJsonToObject(decodedString);
    //   cartModels.add(cartModel);
    //   System.out.println("Sucess!!");

    // }

    @RabbitHandler
    @RabbitListener(queues = "cart_queue")
    @SendTo("reply-to")
  public void reciveDataToProduct(@Payload String cart) throws IOException {
    // Đảm bảo rằng dữ liệu đang sử dụng encoding UTF-8 ngay từ đầu
    byte[] bytes = cart.getBytes(StandardCharsets.UTF_8);
    String decodedString = new String(bytes, StandardCharsets.UTF_8);
    CartModel cartModel = convertJsonToObject(decodedString);
    cartModels.add(cartModel);
    System.out.println("Success!!");
}

    public List<CartModel> getAll_Product_Into_Cart(){
      return cartModels;
    }


    public CartModel changeQuantity(String id,int soluong){
    for (CartModel cartModel : cartModels) {
      if(cartModel.getId().equals(id)){
        if(soluong > cartModel.getSoluong()){
          cartModel.setSoluong(soluong);
          return cartModel;
        }else{
          return null;
        }
      }
    }
      return null;
    }
}


