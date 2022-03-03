package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
   private static String URL = "jdbc:mysql://localhost:3306/testschema";
   private static String USERNAME = "root";
   private static String PASSWORD = "Three5Six";

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
   private static SessionFactory sessionFactory;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory == null){
         try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/testschema");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "Three5Six");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "false");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(serviceRegistry.build());
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return sessionFactory;
   }
}

