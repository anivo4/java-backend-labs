package com.anivo4.service;

import com.anivo4.entity.ShopEntity;
import com.anivo4.exception.ShopNotFoundException;
import com.anivo4.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository repository;

    public ShopServiceImpl(ShopRepository repository) {
        this.repository = repository;
    }

    @Override
    public int save(String name, double revenue) {
        return repository.save(new ShopEntity(name, revenue));
    }

    @Override
    public ShopEntity findById(int id) {
        ShopEntity shop = repository.findById(id);
        if (shop == null) {
            throw new ShopNotFoundException("Shop with id=" + id + " not found");
        }
        return shop;
    }

    @Override
    public ShopEntity findByName(String name) {
        ShopEntity shop = repository.findByName(name);
        if (shop == null) {
            throw new ShopNotFoundException("Shop with name='" + name + "' not found");
        }
        return shop;
    }

    @Override
    public List<ShopEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(ShopEntity entity) {
        if (entity.getId() == null) {
            throw new ShopNotFoundException("Cannot update shop without id");
        }
        if (repository.findById(entity.getId()) == null) {
            throw new ShopNotFoundException("Shop with id=" + entity.getId() + " not found");
        }
        repository.update(entity);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}