package com.anivo4.service;

import com.anivo4.entity.ShopEntity;

import java.util.List;

public interface ShopService {
    int save(String name, double revenue);
    ShopEntity findById(int id);
    ShopEntity findByName(String name);
    List<ShopEntity> findAll();
    void update(ShopEntity entity);
    void deleteById(int id);
}