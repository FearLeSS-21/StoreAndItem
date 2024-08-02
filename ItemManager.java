package main.item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private static final String URL = "jdbc:mysql://localhost:3306/stockmanagment";
    private static final String USER = "root";
    private static final String PASSWORD = "zizo2003";

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void insert(Item item) {
        String sql = "INSERT INTO item (item_code, item_name) VALUES (?, ?)";
        try (Connection connect = getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.executeUpdate();
            System.out.println("Item inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Item> select(String itemCode) {
        String sql = "SELECT * FROM item WHERE item_code = ?";
        List<Item> items = new ArrayList<>();
        try (Connection connect = getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, itemCode);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(rs.getString("item_code"), rs.getString("item_name"));
                    item.setItemId(rs.getInt("id"));
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> findItemsByNameContains(String name) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE item_name LIKE ?";
        try (Connection connect = getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(rs.getString("item_code"), rs.getString("item_name"));
                    item.setItemId(rs.getInt("id"));
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean isDatabaseEmpty() {
        String sql = "SELECT COUNT(*) AS count FROM item";
        try (Connection connect = getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            if (rs.next() && rs.getInt("count") == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
