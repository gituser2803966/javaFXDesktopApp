package db.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/BusData";
    private static final String DATABASE_USER_NAME = "root";
    private static final String DATABASE_PASSWORD = "Tomahawk2803966";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
    }

//    public static void main(String[] args){
//        String url = "jdbc:mysql://localhost:3306/BusData";
//        String user = "root";
//        String password = "Tomahawk2803966";
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection =  DriverManager.getConnection(url,user,password);
//            System.out.println("connect database Successfully "+connection);
////            String query = "insert into Users values (4,'admin','123password')";
////            Statement statement = connection.createStatement();
////            statement.execute(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
}
