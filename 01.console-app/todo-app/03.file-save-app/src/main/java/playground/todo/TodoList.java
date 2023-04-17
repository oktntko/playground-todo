package playground.todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TodoList {
  private static final String FILE_PATH = "todo-list.csv";

  public static List<String> load() throws IOException {
    var path = Paths.get(FILE_PATH);

    if (Files.notExists(path)) {
      return new ArrayList<>();
    }

    return Files.readAllLines(path, StandardCharsets.UTF_8);
  }

  public static void save(List<String> list) throws IOException {
    var path = Paths.get(FILE_PATH);
    System.out.println(list);

    Files.write(path, list, StandardCharsets.UTF_8,
        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
  }
}
