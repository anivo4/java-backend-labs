package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.entity.ShopEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopRepositoryImpl implements ShopRepository {

    private final DataSource dataSource;

    public ShopRepositoryImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public int save(ShopEntity shop) {
        String sql = "INSERT INTO shop (name, revenue) VALUES (?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shop.getName());
            ps.setDouble(2, shop.getRevenue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    shop.setId(id);
                    return id;
                }
            }
            throw new SQLException("Insert failed, no ID returned");

        } catch (SQLException e) {
            throw new RuntimeException("save failed", e);
        }
    }

    @Override
    public ShopEntity findById(int id) {
        String sql = "SELECT id, name, revenue FROM shop WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("findById failed", e);
        }
    }

    @Override
    public ShopEntity findByName(String name) {
        String sql = "SELECT id, name, revenue FROM shop WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("findByName failed", e);
        }
    }

    @Override
    public List<ShopEntity> findAll() {
        String sql = "SELECT id, name, revenue FROM shop ORDER BY id";
        List<ShopEntity> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("findAll failed", e);
        }
    }

    @Override
    public boolean update(ShopEntity shop) {
        if (shop.getId() == null) {
            return false;
        }

        String sql = "UPDATE shop SET name = ?, revenue = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shop.getName());
            ps.setDouble(2, shop.getRevenue());
            ps.setInt(3, shop.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("update failed", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM shop WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();     // результат не важен, не бросаем исключение

        } catch (SQLException e) {
            throw new RuntimeException("deleteById failed", e);
        }
    }

    private ShopEntity mapRow(ResultSet rs) throws SQLException {
        return new ShopEntity(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("revenue")
        );
    }
}