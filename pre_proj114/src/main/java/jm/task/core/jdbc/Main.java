package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class Main {

    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws SQLException {
        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 14);
        userService.saveUser("Sergey", "Sergiev", (byte) 54);
        userService.saveUser("Nikita", "Nikitov", (byte) 99);
        userService.saveUser("Oleg", "Olegov", (byte) 12);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeSession();

    }
}
