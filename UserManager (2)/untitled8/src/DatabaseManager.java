import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private final String url = "jdbc:postgresql://localhost:5433/userdb";
    private final String user = "postgres";
    private final String password = "@db52365236";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void createUserTable() {
        String SQL_CREATE = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY, "
                + "first_name VARCHAR(50) NOT NULL, "
                + "last_name VARCHAR(50) NOT NULL, "
                + "age INT NOT NULL, "
                + "profession VARCHAR(100), "
                + "address VARCHAR(150)"
                + ")";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL_CREATE)) {
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Error creating table: " + ex.getMessage());
        }
    }

    public void saveUser(User user) {
        String SQL_INSERT = "INSERT INTO users (first_name, last_name, age, profession, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4, user.getProfession());
            pstmt.setString(5, user.getAddress());
            pstmt.executeUpdate();
            System.out.println("User saved successfully.");
        } catch (SQLException ex) {
            System.out.println("Error saving user: " + ex.getMessage());
        }
    }

    public User getUserById(int userId) {
        String SQL_SELECT = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("profession"),
                        rs.getString("address")
                );
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving user: " + ex.getMessage());
        }

        return user;
    }

    public void updateUser(User user) {
        String SQL_UPDATE = "UPDATE users SET first_name = ?, last_name = ?, age = ?, profession = ?, address = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4, user.getProfession());
            pstmt.setString(5, user.getAddress());
            pstmt.setInt(6, user.getId());
            pstmt.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException ex) {
            System.out.println("Error updating user: " + ex.getMessage());
        }
    }

    public void deleteUser(int userId) {
        String SQL_DELETE = "DELETE FROM users WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            pstmt.setInt(1, userId);
            int deletedRows = pstmt.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException ex) {
            System.out.println("Error deleting user: " + ex.getMessage());
        }
    }

    public void getAllUsers() {
        String SQL_SELECT_ALL = "SELECT * FROM users";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("profession"),
                        rs.getString("address")
                );
                System.out.println(user);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving users: " + ex.getMessage());
        }
    }
}
