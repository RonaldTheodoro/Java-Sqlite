package sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import sqlite.SQLite;


public class Main {
    public static void main(String[] args) {
        ResultSet resultSet;

        try {
            SQLite sqLite = new SQLite();
            resultSet = sqLite.runQuery(
                "SELECT firstName, lastName FROM user");

            while (resultSet.next()) {
                System.out.print(resultSet.getString("firstName"));
                System.out.println(resultSet.getString("lastName"));
            }
        } catch (ClassNotFoundException error) {
            error.printStackTrace();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}