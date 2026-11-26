import java.sql.*;
import java.util.*;

public class StudentDAO {

    public void insert(String nim, String name) throws SQLException {
        String sql = "INSERT INTO students(nim, name) VALUES(?, ?)";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);              // transaction
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, nim);
                ps.setString(2, name);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Student(rs.getInt("id"),
                        rs.getString("nim"), rs.getString("name")));
            }
        }
        return list;
    }

    public void update(int id, String nim, String name) throws SQLException {
        String sql = "UPDATE students SET nim=?, name=? WHERE id=?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, nim);
                ps.setString(2, name);
                ps.setInt(3, id);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }
}