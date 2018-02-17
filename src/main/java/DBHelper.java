import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBHelper {

    private final static String URL = "jdbc:mysql://localhost:3306/notesmanager";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    static List<Notes> loadDB() {
        Connection connection;
        PreparedStatement preparedStatement;
        List<Notes> listNotes = new ArrayList<>();

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String selectNote = "SELECT * FROM notes";
            preparedStatement = connection.prepareStatement(selectNote);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String value = resultSet.getString("value");
                Timestamp date = resultSet.getTimestamp("date");

                listNotes.add(new Notes(value, date));
            }
            connection.close();

            return listNotes;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static void addNoteToDB(Notes notes) {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String insertNow = "INSERT INTO notes(value, date) value(?,?)";
            preparedStatement = connection.prepareStatement(insertNow);

            preparedStatement.setString(1, notes.getValue());
            preparedStatement.setTimestamp(2, notes.getDate());

            preparedStatement.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
