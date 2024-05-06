package com.example.product_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Products")
public class ProductModel{
    @Id
    private String ID;

    @Column(name = "name_product")
    private String name;

    private double price;

    private int soluong;

    public ProductModel(String ID, String name, double price,int soluong){
        this.ID=ID;
        this.name=name;
        this.price=price;
    }

    public ProductModel(){
        
    }


    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public String getName() {
        return name;
    }public void setName(String name) {
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
