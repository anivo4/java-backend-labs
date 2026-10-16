package com.anivo4.repository;

import com.anivo4.entity.ShopEntity;

import java.util.List;

public interface ShopRepository {

    int save(ShopEntity shop);
    ShopEntity findById(int id);
    ShopEntity findByName(String name);
    List<ShopEntity> findAll();
    boolean update(ShopEntity shop);
    void deleteById(int id);
}