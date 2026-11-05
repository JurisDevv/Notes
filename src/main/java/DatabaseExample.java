import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseExample {

    private static Connection conn;

    public static Connection getConnection() {
        try{

            Class.forName("org.postgresql.Driver");

            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/NoteApp", "USER", "PASSWORD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } return conn;
    }

}















