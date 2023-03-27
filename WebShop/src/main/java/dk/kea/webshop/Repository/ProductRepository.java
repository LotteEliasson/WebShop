package dk.kea.webshop.Repository;

import dk.kea.webshop.Model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    //Database-properties
    //private final static String DB_URL ="jdbc:mysql://lfenew.mysql.database.azure.com:3306/webshop";
    //private final static String UID = "lfe";
    //private final String PWD = "Skolekode1";
    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String UID;

    @Value("${spring.datasource.password}")
    private String PWD;



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

    public void updateProduct(Product product) {
        //SQL statement
        final String UPDATE_QUERY = "UPDATE products SET name = ?, price = ? WHERE id=?";

        try {
            //connect db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            //set parameter
            String name = product.getName();
            double price = product.getPrice();
            int id = product.getId();
            preparedStatement.setString(1,name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3,id);

            //execute statement
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Could not update product");
            e.printStackTrace();
        }
    }

    public Product findProductById(int id) {
        //SQL-statement
        final String FIND_QUERY = "SELECT * FROM products WHERE id = ?";
        Product product = new Product();
        product.setId(id);

        try {
            //DB connection
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

            //Prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);

            //Set parameters
            preparedStatement.setInt(1, id);

            //Execute statement
            ResultSet resultSet = preparedStatement.executeQuery();

            //Få produkt ud af resultset
            resultSet.next();
            String name = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            product.setName(name);
            product.setPrice(price);

        }catch (SQLException e){
            System.out.println("Could not find product");
            e.printStackTrace();
        }
        //return product
        return product;
    }

    public void deleteById(int id){
        //SQL-query
        final String DELETE_QUERY = "DELETE FROM products WHERE id=?";

        try{
            //Connect til db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

            //Create statement
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);

            //set parameter
            preparedStatement.setInt(1,id);

            //execute statement
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println("Could not delete product");
            e.printStackTrace();
        }

    }


}
