package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("SomeName", "SomeLastName", (byte)30);
        userService.saveUser("SomeName1", "SomeLastName1", (byte)31);
        userService.saveUser("SomeName2", "SomeLastName2", (byte)32);
        userService.saveUser("SomeName3", "SomeLastName3", (byte)33);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
