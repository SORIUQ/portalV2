package connections;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conector {

    @Getter
    @Setter
    Properties prop = new Properties();

    public Conector() {
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getMySqlConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(prop.getProperty(Constants.MYSQL_DRIVER));
            try {
                return DriverManager.getConnection(getUrl());
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getUrl() {
        // jdbc:mysql://localhost:3306/schema?user=user&password=password&useSSL=false;
        return new StringBuilder().append(prop.getProperty(Constants.MYSQL_PREFIX))
                .append(prop.getProperty(Constants.MYSQL_HOST)).append(":").append(prop.getProperty(Constants.MYSQL_PORT))
                .append("/").append(prop.getProperty(Constants.MYSQL_SCHEMA)).append("?user=")
                .append(prop.getProperty(Constants.MYSQL_USER)).append("&password=")
                .append(prop.getProperty(Constants.MYSQL_PASSWD)).append("&useSSL=")
                .append(prop.getProperty(Constants.MYSQL_SSL)).toString();
    }

}