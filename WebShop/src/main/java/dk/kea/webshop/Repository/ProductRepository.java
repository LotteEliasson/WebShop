package dk.kea.webshop.Repository;

import dk.kea.webshop.Model.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    //Database-properties
    private final static String DB_URL ="jdbc:mysql://lfenew.mysql.database.azure.com:3306/webshop";
    private final static String UID = "lfe";
    private final String PWD = "Skolekode1";

    public List<Product> getAll(){
        List<Product> productList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM products";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                Product product = new Product(id, name, price);
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

    public void addProduct(Product product){
        try{
            //connect to db
            Connection connection = DriverManager.getConnection(DB_URL,UID,PWD);
            final String CREATE_QUERY = "INSERT INTO products(id,name, price) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            //set attributer i prepared statement
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());

            //execute statement
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Could not create product");
            e.printStackTrace();
        }

    }
}
