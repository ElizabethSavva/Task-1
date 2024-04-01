package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
        // connection = Util.getConnection(); // Создать объект класса Connection и присвоить ему метод, который возвращает объект этого класса(getConnection)
    }

    public void createUsersTable() { // PreparedStatement (выполняет запросы) более универсальный
        // и наследуется от Statement, есть метод execute(String) или (executeUpdate(String) возвращает int))
        String SQL = """
               CREATE TABLE IF NOT EXISTS users(
                   id SERIAL PRIMARY KEY,
                   name VARCHAR NOT NULL,
                   lastName VARCHAR NOT NULL,
                   age INT NOT NULL
               )""";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String SQL =
                    "DROP TABLE IF EXISTS users";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = // Вставляем данные... Что именно будем вставлять(?, ?, ?)
                "INSERT INTO users (name, lastName, age) VALUES  (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString (1, name);
            preparedStatement.setString (2, lastName);
            preparedStatement.setByte (3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) { //  Удаляет пользователя из базы данных.
            String SQL =
                    "DELETE FROM users WHERE id=?"; // (id=?) - Указывает позицию параметра в предварительно скомпилированном SQL-запросе
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL =
                "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();// Хотим получить используем executeQuery()
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public void cleanUsersTable() {
        String SQL =
                "DELETE FROM users";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}