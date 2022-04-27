package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS user"
                + "(id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                "name CHAR(45) NOT NULL, " +
                "lastName CHAR(45) NOT NULL," +
                "age tinyint," +
                "PRIMARY KEY (id))";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана.");
        } catch (Exception e) {
            System.out.println("Таблица не создана.");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE IF EXISTS user";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlDrop).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена.");
        } catch (Exception e) {
            System.out.println("Таблица не удалена.");
            e.printStackTrace();
        }
    }

    @Override
    public void cleanUsersTable() {

        String sqlClean = "TRUNCATE TABLE user";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlClean);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена.");
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу.");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("Пользователь " + name + " " + lastName + " добавлен в базу данных.");
        } catch (Exception e) {
            System.out.println("Не удалось добавить указанного пользователя.");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя.");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUsers = null;
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Список пользователей:");
            listUsers = session.createQuery("From User").list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listUsers;
    }

}