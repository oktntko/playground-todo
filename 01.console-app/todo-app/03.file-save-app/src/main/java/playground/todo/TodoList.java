package playground.todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoList {
  private static final String FILE_PATH = "todo-list.csv";

  public static List<Todo> load() throws IOException {
    List<Todo> todoList = new ArrayList<>();

    var path = Paths.get(FILE_PATH);

    if (Files.notExists(path)) {
      return todoList;
    }

    var lines = Files.readAllLines(path, StandardCharsets.UTF_8);
    for (var line : lines) {
      String[] data = line.split(",");
      if (data.length == 2) {
        var todo = new Todo();
        todo.yarukoto = data[0];
        todo.datetime = LocalDateTime.parse(data[1]);
        todoList.add(todo);

      } else {
        System.err.println("Skip invalid line, "
            + "expected=[yarukoto: String, datetime: LocalDateTime] "
            + "actual=[" + line + "]");
      }
    }

    return todoList;
  }

  public static void save(List<Todo> list) throws IOException {
    var path = Paths.get(FILE_PATH);

    Files.write(path, list.stream()
        .map(todo -> todo.yarukoto + "," + todo.datetime.toString()).toList(),
        StandardCharsets.UTF_8,
        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
  }
}
