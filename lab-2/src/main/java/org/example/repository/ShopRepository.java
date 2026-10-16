package org.example.repository;

import org.example.entity.ShopEntity;

import java.util.List;

public interface ShopRepository {

    int save(ShopEntity shop);
    ShopEntity findById(int id);
    ShopEntity findByName(String name);
    List<ShopEntity> findAll();
    boolean update(ShopEntity shop);
    void deleteById(int id);
}