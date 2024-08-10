package org.example.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StoreManager {
    private static final String URL = "jdbc:mysql://localhost:3306/stockmanagment";
    private static final String USER = "root";
    private static final String PASSWORD = "zizo2003";

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void insert(Store store) {
        String sql = "INSERT INTO store (store_code, store_name) VALUES (?, ?)";
        try (Connection connect = getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, store.getStoreCode());
            preparedStatement.setString(2, store.getStoreName());
            preparedStatement.executeUpdate();

            System.out.println("Store inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Store select(String storeCode) {
        String sql = "SELECT * FROM store WHERE store_code = ?";
        Store store = null;
        try (Connection connect = getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, storeCode);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    store = new Store(rs.getString("store_code"), rs.getString("store_name"));
                    store.setStoreId(rs.getInt("id"));
                } else {
                    System.out.println("No store found with code: " + storeCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public boolean isDatabaseEmpty() {
        String sql = "SELECT COUNT(*) FROM store";
        try (Connection connect = getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
