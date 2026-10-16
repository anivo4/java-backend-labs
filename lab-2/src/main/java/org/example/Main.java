package org.example;

import org.example.config.DatabaseConfig;
import org.example.entity.ShopEntity;
import org.example.repository.ShopRepository;
import org.example.repository.ShopRepositoryImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig.runMigrations();

        ShopRepository repo = new ShopRepositoryImpl();

        List<ShopEntity> all = repo.findAll();
        all.forEach(System.out::println);

        ShopEntity newShop = new ShopEntity("Евгений Онегин", 1234.56);
        int newId = repo.save(newShop);
        System.out.println("Created id = " + newId);

        ShopEntity found = repo.findById(newId);
        System.out.println("Found: " + found);

        ShopEntity byName = repo.findByName("Евгений Онегин");
        System.out.println("By name: " + byName);

        found.setRevenue(9999.99);
        boolean updated = repo.update(found);
        System.out.println("Updated: " + updated);
        System.out.println("After update: " + repo.findById(newId));

        repo.deleteById(newId);
        System.out.println("After delete: " + repo.findById(newId));

        DatabaseConfig.shutdown();
    }
}