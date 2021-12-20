package service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbFeatures {
    private static DbFeatures instance;
    private final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/db_weather";
    private final String DB_USERNAME = ""; //SET USERNAME
    private final String DB_PASSWORD = ""; //SET PASSWORD
    private final String DB_CLASS_NAME = "org.postgresql.Driver";
    private final String DB_FIELD_WEATHER = "weather_value";
    private final String DB_SQL_REQUEST = "SELECT weather_value FROM weather_history WHERE date = ";
    private final String DATE_FORMAT = "yyyy/MM/dd";

    private Connection connection;
    private Statement statement;
    private Date curDate;
    private SimpleDateFormat sdFormat;

    private DbFeatures() {
       connect();
       this.curDate = new Date();
       this.sdFormat = new SimpleDateFormat(DATE_FORMAT);
    }
    public static DbFeatures getInstance() {
        if (instance == null) {
            instance = new DbFeatures();
        }
        return instance;
    }
    private void connect() {

        try {
            Class.forName(DB_CLASS_NAME);
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            this.statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            this.statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public String getTodayWeatherFromDatabase() {
        try {
            String currentDate = this.sdFormat.format(this.curDate);
            ResultSet rs = this.statement.executeQuery(DB_SQL_REQUEST + "'" + currentDate + "'");
            if (rs.next()) {
                return rs.getString(DB_FIELD_WEATHER);
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void setTodayWeatherToDatabase(String text) {
        String currentDate = this.sdFormat.format(this.curDate);
        final String SQL_REQ_INSERT = "INSERT INTO weather_history (date, weather_value) VALUES('"
                + currentDate + "', '" + text + "')";
        try {
            this.statement.executeUpdate(SQL_REQ_INSERT);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
