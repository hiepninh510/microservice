package com.example.product_service.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.product_service.models.ProductModel;
import com.example.product_service.models.UserReponsitory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ProductService {
     private List<ProductModel> products = new ArrayList<ProductModel>();
     private final UserReponsitory userReponsitory;


    @Autowired
    private RabbitTemplate rabbitTemplate;
    private RestTemplate restTemplate;

    public ProductService(UserReponsitory userReponsitory) {
        // Khởi tạo danh sách sản phẩm ở đây
        // products.add(new ProductModel("SP1", "Sản phẩm 1", 10.0));
        // products.add(new ProductModel("SP2", "Sản phẩm 2", 15.0));
        // products.add(new ProductModel("SP3", "Sản phẩm 3", 20.0));
        this.userReponsitory=userReponsitory;
    }

    public List<ProductModel> findAllProducts() {
        return (List<ProductModel>) userReponsitory.findAll();
    }



    private final ObjectMapper objectMapper = new ObjectMapper();

    // public String convertObjectToJson(Object object) throws JsonProcessingException {
    //     return objectMapper.writeValueAsString(object);
    // }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object instanceof Optional<?>) {
            Optional<?> optionalObject = (Optional<?>) object;
            if (optionalObject.isPresent()) {
                return objectMapper.writeValueAsString(optionalObject.get());
            } else {
                // Trường hợp Optional rỗng
                return "{}";
            }
        } else {
            return objectMapper.writeValueAsString(object);
        }
    }

    public ProductModel sendToCartService(String id)throws JsonProcessingException{
        for(ProductModel product:products){
            if(product.getID().equals(id)){
                String json = convertObjectToJson(product);
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setReplyTo("cart_queue");
                messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                Message message = new Message(json.getBytes(), messageProperties);
                rabbitTemplate.send("cart-exchange","cart-routing-key",message);
                return product;
            }
        }

        return null;
    } 

    public ProductModel senModel(String id)throws JsonProcessingException{
        Optional<ProductModel> product = userReponsitory.findById(id);
        if(product.isPresent()){
            ProductModel productModel = product.get();
            if(productModel.getSoluong()>0){
                userReponsitory.decreaseSoluongById(id);
            }
            productModel.setSoluong(1);
            String json = convertObjectToJson(productModel);
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setReplyTo("cart_queue");
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding("UTF-8"); 
            Message message = new Message(json.getBytes(StandardCharsets.UTF_8), messageProperties);
            rabbitTemplate.send("cart-exchange","cart-routing-key",message);
            return productModel;
        }
        return null;
    }
 
    // public ProductModel getByID(String id){
    //     for(ProductModel product:products){
    //         if(product.getID().equals(id)){
    //             return product;
    //         }
    //     }
    //     return null;
    // }

    public Optional<ProductModel> getByID(String id){
        return userReponsitory.findById(id);
    }

    public String getCartProductsFromCartProductService() throws Exception {
        // Gửi yêu cầu GET đến Eureka server để lấy thông tin về cart_service
        String eurekaUrl = "http://eureka-server-hostname:port/eureka/apps/cart_service";
        String cartServiceInfo = restTemplate.getForObject(eurekaUrl, String.class);

        // Phân tích thông tin trả về từ Eureka server để lấy địa chỉ của cart_service
        String cartProductServiceUrl = parseCartServiceUrl(cartServiceInfo);

        // Gửi yêu cầu GET đến cart_service để lấy thông tin về sản phẩm trong giỏ hàng
        String cartProducts = restTemplate.getForObject(cartProductServiceUrl + "/cart-products", String.class);
        System.out.println(cartProducts);
        return cartProducts;
    }

    public String parseCartServiceUrl(String cartServiceInfo) throws Exception {
        // Chuyển đổi dữ liệu JSON thành đối tượng JsonNode
        JsonNode rootNode = objectMapper.readTree(cartServiceInfo);

        // Lặp qua danh sách các instance (trong trường hợp có nhiều instance của cart_service)
        for (JsonNode instanceNode : rootNode.get("application").get(0).get("instance")) {
            // Lấy thông tin về địa chỉ và cổng của mỗi instance của cart_service
            String host = instanceNode.get("hostName").asText();
            int port = instanceNode.get("port").get("$").asInt();
            
            // Tạo và trả về URL của cart_service
            return "http://" + host + ":" + port;
        }

        // Trả về null nếu không tìm thấy instance của cart_service
        return null;
    }
}
