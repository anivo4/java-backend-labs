package com.anivo4.controller;

import com.anivo4.entity.ShopEntity;
import com.anivo4.exception.ShopNotFoundException;
import com.anivo4.service.ShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ShopControllerMvcTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18-alpine");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShopService service;

    @Test
    void create_returns201WithId() throws Exception {
        when(service.save("New", 50.0)).thenReturn(7);

        mockMvc.perform(post("/api/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New\",\"revenue\":50.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7));
    }

    @Test
    void findById_returns200() throws Exception {
        when(service.findById(1)).thenReturn(new ShopEntity(1, "Test", 100.0));

        mockMvc.perform(get("/api/shops/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.revenue").value(100.0));
    }

    @Test
    void findById_returns404() throws Exception {
        when(service.findById(99)).thenThrow(new ShopNotFoundException("not found"));

        mockMvc.perform(get("/api/shops/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not found"));
    }

    @Test
    void findByName_returns200() throws Exception {
        when(service.findByName("Test")).thenReturn(new ShopEntity(1, "Test", 100.0));

        mockMvc.perform(get("/api/shops/by-name/Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void findAll_returns200() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                new ShopEntity(1, "A", 10.0),
                new ShopEntity(2, "B", 20.0)
        ));

        mockMvc.perform(get("/api/shops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void update_returns204() throws Exception {
        doNothing().when(service).update(any(ShopEntity.class));

        mockMvc.perform(put("/api/shops/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\",\"revenue\":200.0}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_returns204() throws Exception {
        doNothing().when(service).deleteById(anyInt());

        mockMvc.perform(delete("/api/shops/1"))
                .andExpect(status().isNoContent());
    }
}