package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connect = Util.getConnect();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS user_table " +
                "(id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(70) not NULL, " +
                "lastName VARCHAR(70) not NULL, age TINYINT, " +
                "PRIMARY KEY (id))";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlCreateTable)) {
            preparedStatement.executeUpdate();
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

        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlDropTable)) {
            preparedStatement.executeUpdate();
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
        String sqlInsert = "INSERT INTO user_table(name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
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
        String sqlRemove = "DELETE FROM user_table WHERE id = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlRemove)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
//               System.out.println("Удален пользователь c id = " + id);
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


        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlGetAll)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
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
            try (PreparedStatement preparedStatement = connect.prepareStatement(sqlClean)) {
                preparedStatement.execute();
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

