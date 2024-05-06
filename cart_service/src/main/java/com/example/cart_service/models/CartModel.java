package com.example.cart_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "GioHang")
public class CartModel {
    @Id
    private String id;

    @Column(name = "name_Product")
    private String name;
    private double price;
    private int soluong;

    public CartModel(){

    }

    public CartModel(String id, String name, double price,int soluong){
     this.id=id;
     this.name=name;
     this.price=price;
     this.soluong=soluong;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getSoluong() {
        return soluong;
    }
    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}
