package playground.todo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

  private static final String URL = "jdbc:postgresql://localhost:2319/todo";
  private static final String USER = "todo";
  private static final String PASS = "todo";

  public static List<Todo> load() throws IOException, SQLException {
    List<Todo> todoList = new ArrayList<>();

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement ps = conn.prepareStatement("""
            SELECT
                id
              , yarukoto
              , datetime
            FROM
              todo; """);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        var todo = new Todo(
            rs.getInt("id"),
            rs.getString("yarukoto"),
            rs.getTimestamp("datetime").toLocalDateTime());
        todoList.add(todo);
      }
    }

    return todoList;
  }

  public static int insert(Todo todo) throws IOException, SQLException {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement ps = conn.prepareStatement("""
            INSERT INTO todo (
                yarukoto
              , datetime
            ) VALUES (
                ?
              , ?
            ); """, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, todo.yarukoto);
      ps.setTimestamp(2, Timestamp.valueOf(todo.datetime));

      int count = ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        int id = rs.getInt("id");
        todo.id = id;
      }

      return count;
    }
  }

  public static int update(Todo todo) throws IOException, SQLException {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement ps = conn.prepareStatement("""
            UPDATE todo
            SET
                yarukoto = ?
              , datetime = ?
            WHERE
              id = ?; """)) {
      ps.setString(1, todo.yarukoto);
      ps.setTimestamp(2, Timestamp.valueOf(todo.datetime));
      ps.setInt(3, todo.id);

      int count = ps.executeUpdate();

      return count;
    }
  }

  public static int delete(Todo todo) throws IOException, SQLException {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement ps = conn.prepareStatement("""
            DELETE FROM todo
            WHERE
              id = ?; """)) {
      ps.setInt(1, todo.id);

      int count = ps.executeUpdate();

      return count;
    }
  }
}
