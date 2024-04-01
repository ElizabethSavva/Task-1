package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory(); // Вызывается метод getCurrentSession() у экземпляра SessionFactory, чтобы получить текущую сессию

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction(); //Метод начинает новую транзакцию для текущей сессии
            session.createSQLQuery("""
                          CREATE TABLE IF NOT EXISTS users(
                          id SERIAL PRIMARY KEY,
                          name VARCHAR NOT NULL,
                          lastName VARCHAR NOT NULL,
                          age INT NOT NULL)
                          """).
                    executeUpdate(); // Создается запрос Hibernate для удаления всех записей из таблицы "users". Метод executeUpdate() выполняет этот запрос и возвращает количество измененных строк в таблице
            session.getTransaction().commit();// Метод подтверждает транзакцию и фиксирует все изменения в базе данных
            session.close();
        }  catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            User user = new User(name, lastName, age); // Создание нового объекта пользователя, то есть User имеет конструктор, принимающий параметры name, lastName и age
            session.save(user); // Метод save() добавляет новую запись в таблицу базы данных
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            User user = session.get(User.class, id); // Получаем объект пользователя по его идентификатору (id)
            session.remove(user); // Объект который надо удалить
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            users = session.createQuery("FROM User").getResultList(); // Создаем запрос Hibernate для извлечения всех записей из таблицы/сущности User
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate(); // ?????
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }
}
