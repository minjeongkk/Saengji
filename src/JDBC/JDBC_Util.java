package JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class JDBC_Util {

    public static Connection getConnection() throws SQLException {
        Connection conn = null;

        String filePath = "db.properties";

        try (InputStream reader = JDBC_Util.class.getResourceAsStream(filePath)) {

            Properties pros = new Properties();
            pros.load(reader);

            String url = pros.getProperty("url");
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);

        } catch (IOException e) {

            System.out.println(e.getMessage());

        }
        return conn;
    }
}