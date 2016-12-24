package sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLite {
    private static Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public SQLite() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:SQLite.db");
        statement = connection.createStatement();
        initialize();
    }

    public ResultSet runQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void runQueryWithoutReturn(String query) throws SQLException {
        statement.execute(query);
    }

    public PreparedStatement preparedStatement(String query) 
        throws SQLException {
        return connection.prepareStatement(query);
    }

    private void initialize() throws ClassNotFoundException, SQLException {
        if (!checkTable().next())
            createData();
    }

    public void createData() {
        createTables();
        addUser("John", "McNeil");
        addUser("Paul", "Smith");
    }

    public ResultSet checkTable() throws ClassNotFoundException, SQLException {
        return runQuery("SELECT name FROM sqlite_master " +
            "WHERE type='table' AND name='user'");
    }

    public void createTables() throws ClassNotFoundException, SQLException {
        runQueryWithoutReturn("CREATE TABLE user(" +
            "id integer, " + 
            "firstName varchar(60), " + 
            "lastName varchar(60), " + 
            "primary key(id));"
        );
    }

    public void addUser(String firstName, String lastName) 
        throws ClassNotFoundException, SQLException {
        setPreparedStatement("INSERT INTO user values(?, ?, ?);");
        preparedStatementSetString(2, firstName);
        preparedStatementSetString(3, lastName);
        preparedStatementExecute();
    }

    public void setPreparedStatement(String query) 
        throws ClassNotFoundException, SQLException {
        preparedStatement = preparedStatement(query);
    }

    public void preparedStatementSetString(int columnIndex, String value)
        throws SQLException {
        preparedStatement.setString(columnIndex, value);
    }

    public void preparedStatementExecute() throws SQLException {
        preparedStatement.execute();
    }
}