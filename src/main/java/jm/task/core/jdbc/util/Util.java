package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
   private static final String URL = "jdbc:mysql://localhost:3306/my_db";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "Three5Six";
   private static Connection connect;

   public static Connection getConnect() {
      Connection connect = null;
         try {
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connect.setAutoCommit(false);
         } catch (SQLException e) {
            e.printStackTrace();
         }

      return connect;
   }
   public static void closeConnect() {
      if (connect != null) {
         try {
            connect.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }
}

