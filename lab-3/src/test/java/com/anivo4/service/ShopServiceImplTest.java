package com.anivo4.service;

import com.anivo4.entity.ShopEntity;
import com.anivo4.exception.ShopNotFoundException;
import com.anivo4.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceImplTest {

    @Mock
    private ShopRepository repository;

    @InjectMocks
    private ShopServiceImpl service;

    @Test
    void save_createsAndReturnsId() {
        when(repository.save(any(ShopEntity.class))).thenReturn(42);

        int id = service.save("Test Shop", 100.0);

        assertEquals(42, id);
        ArgumentCaptor<ShopEntity> captor = ArgumentCaptor.forClass(ShopEntity.class);
        verify(repository).save(captor.capture());
        assertEquals("Test Shop", captor.getValue().getName());
        assertEquals(100.0, captor.getValue().getRevenue());
    }

    @Test
    void findById_returnsShop_whenExists() {
        ShopEntity shop = new ShopEntity(1, "Test", 100.0);
        when(repository.findById(1)).thenReturn(shop);

        assertEquals(shop, service.findById(1));
        verify(repository).findById(1);
    }

    @Test
    void findById_throws_whenNotFound() {
        when(repository.findById(99)).thenReturn(null);

        ShopNotFoundException ex = assertThrows(ShopNotFoundException.class,
                () -> service.findById(99));
        assertTrue(ex.getMessage().contains("99"));
    }

    @Test
    void findByName_returnsShop_whenExists() {
        ShopEntity shop = new ShopEntity(1, "Test", 100.0);
        when(repository.findByName("Test")).thenReturn(shop);

        assertEquals(shop, service.findByName("Test"));
    }

    @Test
    void findByName_throws_whenNotFound() {
        when(repository.findByName("Missing")).thenReturn(null);

        assertThrows(ShopNotFoundException.class, () -> service.findByName("Missing"));
    }

    @Test
    void findAll_returnsList() {
        List<ShopEntity> shops = List.of(
                new ShopEntity(1, "A", 10.0),
                new ShopEntity(2, "B", 20.0)
        );
        when(repository.findAll()).thenReturn(shops);

        assertEquals(shops, service.findAll());
    }

    @Test
    void findAll_returnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());
        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void update_updatesExistingShop() {
        ShopEntity shop = new ShopEntity(1, "Updated", 200.0);
        when(repository.findById(1)).thenReturn(new ShopEntity(1, "Old", 100.0));

        assertDoesNotThrow(() -> service.update(shop));
        verify(repository).update(shop);
    }

    @Test
    void update_throws_whenShopNotFound() {
        ShopEntity shop = new ShopEntity(99, "X", 1.0);
        when(repository.findById(99)).thenReturn(null);

        assertThrows(ShopNotFoundException.class, () -> service.update(shop));
        verify(repository, never()).update(any());
    }

    @Test
    void update_throws_whenIdIsNull() {
        ShopEntity shop = new ShopEntity("No id", 100.0);

        assertThrows(ShopNotFoundException.class, () -> service.update(shop));
        verifyNoInteractions(repository);
    }

    @Test
    void deleteById_callsRepository() {
        service.deleteById(1);
        verify(repository).deleteById(1);
    }
}