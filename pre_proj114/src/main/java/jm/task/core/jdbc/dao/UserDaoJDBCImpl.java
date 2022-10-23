package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connect = Util.getConnect();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement state = connect.createStatement()) {
            state.executeUpdate("CREATE TABLE IF NOT EXISTS usertable " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age INT)");
        } catch (SQLException e) {
            System.out.println("Команда не выполнена.");
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement state = connect.createStatement()) {
            state.executeUpdate("DROP TABLE IF EXISTS usertable");
        } catch (SQLException e) {
            System.out.println("Команда не выполнена.");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepareState = connect.prepareStatement("INSERT INTO usertable (name, lastName, age) VALUES (?, ?, ?)")) {
            prepareState.setString(1, name);
            prepareState.setString(2, lastName);
            prepareState.setByte(3, age);
            prepareState.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Команда не выполнена.");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prepareState = connect.prepareStatement("DELETE FROM usertable WHERE id = ?")) {
            prepareState.setLong(1, id);
            prepareState.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Команда не выполнена.");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connect.createStatement().executeQuery("SELECT * FROM usertable")) {
            while(resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));

                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Команда не выполнена.");
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE usertable");
        } catch (SQLException e) {
            System.out.println("Команда не выполнена.");
            e.printStackTrace();
        }
    }
}
