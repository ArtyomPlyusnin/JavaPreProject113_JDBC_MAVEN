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
        Session session = null;
        String createTable = "CREATE TABLE IF NOT EXISTS user"
                + "(id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                "name CHAR(45) NOT NULL, " +
                "lastName CHAR(45) NOT NULL," +
                "age tinyint," +
                "PRIMARY KEY (id))";
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана.");
        } catch (Exception e) {
            System.out.println("Таблица не создана.");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        String sqlDrop = "DROP TABLE IF EXISTS user";
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery(sqlDrop).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена.");
        } catch (Exception e) {
            System.out.println("Таблица не удалена.");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void cleanUsersTable() {

        String sqlClean = "TRUNCATE TABLE user";
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlClean);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена.");
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу.");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;

        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("Пользователь " + name + " " + lastName + " добавлен в базу данных.");
        } catch (Exception e) {
            System.out.println("Не удалось добавить указанного пользователя.");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя.");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            System.out.println("Список пользователей:");
            return session.createQuery("From User").list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return session.createQuery("From User").list();
    }

}