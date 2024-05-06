package com.example.product_service.models;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

// public interface UserReponsitory extends CrudRepository<ProductModel, String>{

    
// }
public interface UserReponsitory extends JpaRepository<ProductModel,String>{

    @Transactional
    @Modifying
    @Query("UPDATE ProductModel p SET p.soluong = p.soluong - 1 WHERE p.id = :id")
    int decreaseSoluongById(@Param("id") String id);
}