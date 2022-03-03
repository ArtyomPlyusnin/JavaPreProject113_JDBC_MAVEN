package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connect = Util.getConnect();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS user_table " +
                "(id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(70) not NULL, " +
                "lastName VARCHAR(70) not NULL, age TINYINT, " +
                "PRIMARY KEY (id))";
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(sqlCreateTable);
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        String sqlDropTable = "DROP TABLE IF EXISTS user_table";

        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(sqlDropTable);
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        String sqlInsert = "INSERT user_table (name, lastName, age)" +
                "VALUES ('" + name + "', " + "'" + lastName + "', " + age + ")";
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(sqlInsert);
            connect.commit();
            System.out.println("User с именем " + name + " добавлен в базу");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM user_table WHERE id = " + id;
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(sqlRemove);
//                System.out.println("Удален пользователь c id = " + id);
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sqlGetAll = "SELECT id, name, lastName, age FROM user_table";


        try (Statement statement = connect.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlGetAll);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getByte(4));
                list.add(user);
            }
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE user_table";
            try (Statement statement = connect.createStatement()) {
                statement.execute(sqlClean);
                connect.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connect.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
    }
}

