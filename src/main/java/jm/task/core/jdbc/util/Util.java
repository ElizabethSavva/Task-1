package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util { // Все утилитные классы должны быть final
    // реализуйте настройку соеденения с БД
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // Константы
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "1wsx!EDC24";

    private Util() { // Конструктор
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() { // создаем и возвращаем экземпляр SessionFactory
        try {
            Configuration configuration = new Configuration();// Создаем объект
            // Добавляется аннотированный класс User в конфигурацию Hibernate.
            configuration.addAnnotatedClass(User.class);
            configuration.setProperty(Environment.URL, URL);
            configuration.setProperty(Environment.USER, USERNAME);
            configuration.setProperty(Environment.PASS, PASSWORD);
            return configuration.buildSessionFactory(); // Создается и возвращается SessionFactory, который является основным интерфейсом Hibernate для создания сессий
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

}
