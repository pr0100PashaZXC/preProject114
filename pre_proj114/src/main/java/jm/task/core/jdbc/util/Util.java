package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mytestdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1488";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";



    public static Connection getConnect() {

        Connection connect = null;
        try {
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static SessionFactory getSession() {
        SessionFactory sessionFactory = null;

        Configuration config = new Configuration()
                .setProperty("hibernate.connection.driver_class", DRIVER)
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.connection.username", USERNAME)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.dialect", DIALECT)
                .setProperty("hibernate.c3p0.min_size","5")
                .setProperty("hibernate.c3p0.max_size","200")
                .setProperty("hibernate.c3p0.max_statements","200")
                .addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();

        sessionFactory = config.buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }


    public static void closeConnect() {
        Connection connect = Util.getConnect();
        try {
            connect.close();
        } catch (SQLException e) {
            System.out.println("Не удалось закрыть соединение");
        }
    }

    public static void closeSession() {
        SessionFactory sessionFactory = Util.getSession();
        sessionFactory.close();
    }

}