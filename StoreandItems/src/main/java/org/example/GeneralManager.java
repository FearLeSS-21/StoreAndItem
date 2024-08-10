package org.example;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


  /*---------------------------New Class--------------------------------------*/
public class GeneralManager<T> {

    /*---------------------------Connection--------------------------------------*/
    private static final String URL = "jdbc:mysql://localhost:3306/stockmanagment";
    private static final String USER = "root";
    private static final String PASSWORD = "zizo2003";

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /*---------------------------Insert Func--------------------------------------*/
    public void insert(T obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");
        List<Object> params = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value != null) {
                sql.append(field.getName()).append(", ");
                values.append("?, ");
                params.add(value);
            }
        }

        if (sql.length() > 2) {
            sql.setLength(sql.length() - 2);
        }
        if (values.length() > 7) {
            values.setLength(values.length() - 2);
        }

        sql.append(") ").append(values).append(")");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ps.executeUpdate();
            System.out.println("Item inserted successfully.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*---------------------------Select Func--------------------------------------*/

    public List<T> select(T obj) {
        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
        List<Object> params = new ArrayList<>();

        try {
            boolean first = true;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value != null) {
                    if (first) {
                        sql.append(" WHERE ");
                        first = false;
                    } else {
                        sql.append(" AND ");
                    }
                    sql.append(field.getName()).append(" = ?");
                    params.add(value);
                }
            }

            List<T> items = new ArrayList<>();
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        T item = (T) clazz.getDeclaredConstructor().newInstance();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            field.set(item, rs.getObject(field.getName()));
                        }
                        items.add(item);
                    }
                }
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
