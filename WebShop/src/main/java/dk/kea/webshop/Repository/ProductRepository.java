package dk.kea.webshop.Repository;

import dk.kea.webshop.Model.Products;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    //Database-properties
    private final static String DB_URL ="jdbc:mysql://localhost:3306/webshop";
    private final static String UID = "root";
    private final String PWD = "Dickduck1";

    public List<Products> getAll(){
        List<Products> productList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM products";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                Products product = new Products(id, name, price);
                productList.add(product);
                System.out.println(product);
            }

        }
        catch(SQLException e)
        {
            System.out.println("Could not query database");
            e.printStackTrace();
        }
        return productList;
    }

}
