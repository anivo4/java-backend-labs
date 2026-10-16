package org.example.entity;

import java.util.Objects;

public class ShopEntity {
    private Integer id;
    private String name;
    private double revenue;

    public ShopEntity() {}

    public ShopEntity(String name, double revenue) {
        this.name = name;
        this.revenue = revenue;
    }

    public ShopEntity(Integer id, String name, double revenue) {
        this.id = id;
        this.name = name;
        this.revenue = revenue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "ShopEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", revenue=" + revenue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShopEntity that = (ShopEntity) o;
        return Double.compare(revenue, that.revenue) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, revenue);
    }
}