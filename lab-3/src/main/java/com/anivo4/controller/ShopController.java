package com.anivo4.controller;

import com.anivo4.dto.ShopRequest;
import com.anivo4.entity.ShopEntity;
import com.anivo4.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService service;

    public ShopController(ShopService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, Integer>> create(@RequestBody com.anivo4.dto.ShopRequest request) {
        int id = service.save(request.name(), request.revenue());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    @GetMapping("/{id}")
    public ShopEntity findById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @GetMapping("/by-name/{name}")
    public ShopEntity findByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }
    @GetMapping
    public List<ShopEntity> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") int id, @RequestBody ShopRequest request) {
        service.update(new ShopEntity(id, request.name(), request.revenue()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.deleteById(id);
    }
}