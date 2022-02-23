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
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS user_table " +
                "(id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(70) not NULL, " +
                "lastName VARCHAR(70) not NULL, age TINYINT, " +
                "PRIMARY KEY (id))";
        try (Connection connect = Util.getConnect()) {
            try (Statement statement = connect.createStatement()){
                connect.setAutoCommit(false);
                statement.executeUpdate(sqlCreateTable);
//                System.out.println("Таблица добавлена.");
                connect.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connect.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
    String sqlDropTable = "DROP TABLE IF EXISTS user_table";
        try (Connection connect = Util.getConnect()) {
            try (Statement statement = connect.createStatement()){
                statement.executeUpdate(sqlDropTable);
//                System.out.println("Таблица удалена");
                connect.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connect.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
    String sqlInsert = "INSERT user_table (name, lastName, age)" +
            "VALUES ('" + name + "', " + "'" + lastName + "', " + age + ")";
        try (Connection connect = Util.getConnect()) {
            try (Statement statement = connect.createStatement()) {
                statement.executeUpdate(sqlInsert);
                connect.commit();
                System.out.println("User с именем " + name + " добавлен в базу");
            } catch (SQLException e) {
                e.printStackTrace();
                connect.rollback();
            }
            connect.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM user_table WHERE id = " + id;
        try (Connection connect = Util.getConnect()) {
            try (Statement statement = connect.createStatement()) {
                statement.executeUpdate(sqlRemove);
//                System.out.println("Удален пользователь c id = " + id);
                connect.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connect.rollback();
            }
            connect.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sqlGetAll = "SELECT id, name, lastName, age FROM user_table";

        try (Connection connect = Util.getConnect()) {
            try (Statement statement = connect.createStatement()) {
                connect.setAutoCommit(false);
                ResultSet rs = statement.executeQuery(sqlGetAll);
                while (rs.next()) {
                    User user = new User(   );
                    user.setId(rs.getLong(1));
                    user.setName(rs.getString(2));
                    user.setLastName(rs.getString(3));
                    user.setAge(rs.getByte(4));
                    list.add(user);
                }
                connect.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connect.rollback();
            }
            connect.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE user_table";
        try (Connection conn = Util.getConnect()) {
            try (Statement statement = conn.createStatement()) {
                statement.execute(sqlClean);
//                System.out.println("Table clear");
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

