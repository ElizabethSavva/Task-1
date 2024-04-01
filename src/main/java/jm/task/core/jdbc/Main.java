package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Michael", "Jackson", (byte) 50);
        userService.saveUser("Alan", "Rickman", (byte) 69);
        userService.saveUser("Freddie", "Mercury", (byte) 45);
        userService.saveUser("Elizabeth", "Alexandra Mary Windsor", (byte) 96);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
