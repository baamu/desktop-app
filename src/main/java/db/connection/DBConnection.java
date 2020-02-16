package db.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author oshan
 */
public class DBConnection {
    private static DBConnection dbConnection = null;
    private Connection connection = null;

    private DBConnection() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nightwolf","root","root"
        );
    }

    public static DBConnection getInstance() throws ClassNotFoundException, SQLException, IOException {
        if(dbConnection == null) {
            dbConnection = new DBConnection();
        }

        return dbConnection;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
