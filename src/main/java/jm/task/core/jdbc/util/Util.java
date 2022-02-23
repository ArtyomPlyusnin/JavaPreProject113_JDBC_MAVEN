package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
   private static String URL = "jdbc:mysql://localhost:3306/testschema";
   private static String USERNAME = "root";
   private static String PASSWORD = "Three5Six";
   static Connection connect;

   public static Connection getConnect() throws SQLException {
      if (connect == null || connect.isClosed()) {
         try {
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connect.setAutoCommit(false);
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return connect;
   }
}