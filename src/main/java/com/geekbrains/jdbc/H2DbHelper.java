package com.geekbrains.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class H2DbHelper {

    private Connection connection;
    private Statement statement;

    public void connect() throws Exception {
        Class.forName("org.h2.Driver");
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "sa");
        connectionProperties.put("password", "");
        connection = DriverManager.getConnection(
                "jdbc:h2:/Users/user/backend-test-sep-2021/backend-test-sep-2021.db",
                connectionProperties
        );
        statement = connection.createStatement();
    }

    public Product getProductById(int id) {
        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT " +
                            "P.ID, " +
                            "P.TITLE AS PRODUCT_TITLE, " +
                            "P.PRICE, " +
                            "C.TITLE AS CATEGORY_TITLE " +
                            "FROM PRODUCTS P " +
                            "JOIN CATEGORIES C ON P.CATEGORY_ID = C.ID " +
                            "WHERE P.ID = " + id
            );
            while (rs.next()) {
                int productId = rs.getInt("ID");
                String title = rs.getString("PRODUCT_TITLE");
                String categoryTitle = rs.getString("CATEGORY_TITLE");
                int price = rs.getInt("PRICE");
                return Product.builder()
                        .id(productId)
                        .price(price)
                        .title(title)
                        .categoryTitle(categoryTitle)
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
        throw new RuntimeException("Product not found");
    }

    public List<Product> getProducts() {
        return new ArrayList<>();
    }

    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
