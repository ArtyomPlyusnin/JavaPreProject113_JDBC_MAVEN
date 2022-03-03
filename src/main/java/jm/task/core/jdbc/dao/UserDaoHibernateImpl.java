package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String sqlCreate = "CREATE TABLE IF NOT EXISTS user_table " +
            "(id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(70) not NULL, " +
            "lastName VARCHAR(70) not NULL, age TINYINT, " +
            "PRIMARY KEY (id))";
    private static final String sqlDrop = "DROP TABLE IF EXISTS user_table";
    private static final String sqlClean = "TRUNCATE TABLE user_table";
    private static Session session;



    @Override
    public void createUsersTable() {
        operations(sqlCreate);
    }

    @Override
    public void dropUsersTable() {
        operations(sqlDrop);
    }

    @Override
    public void cleanUsersTable() {operations(sqlClean);}

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.save(user);
        tr.commit();
        session.close();
        System.out.println("User с именем "+ name + " добавлен в базу.");
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        List<User> list = session.createCriteria(User.class).list();
        tr.commit();
        session.close();
        return list;

    }

    public void operations  (String sql) {
        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createSQLQuery(sql);
        query.executeUpdate();
        transaction.commit();
        session.close();

    }
}