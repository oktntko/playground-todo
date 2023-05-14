package playground.todo;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.fusesource.jansi.AnsiConsole;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import kong.unirest.GenericType;
import kong.unirest.Unirest;

public class App {

  public static final String ADD_TODO = "＋ADD TODO";
  public static final String BASE_URL = "http://localhost:8080";

  public static void main(String[] args) throws InterruptedException, IOException, SQLException {
    AnsiConsole.systemInstall();

    System.out.println(ansi()
        .fgGreen()
        .a("Welcome back!")
        .reset());

    List<Todo> todoList = Unirest.get(BASE_URL + "/api/todo")
        .header("Content-Type", "application/json")
        .asObject(new GenericType<List<Todo>>() {
        })
        .getBody();

    for (;;) {
      // メニュー表示
      List<Todo> menu = new ArrayList<>();
      menu.addAll(todoList);
      menu.add(new Todo(null, ADD_TODO, null));
      final var selectIndex = MyPrompt.selectFromTodo(
          ansi()
              .a("You have ")
              .fgCyan()
              .bold()
              .a(todoList.size() > 0 ? todoList.size() : "no")
              .reset()
              .a(" todo.")
              .toString(),
          menu);

      // 追加
      if (selectIndex == menu.size() - 1) {
        final var newTodo = MyPrompt.input("Enter your todo.");

        System.out.println(ansi()
            .fgGreen()
            .a("  => ")
            .a("CREATED new todo. ")
            .reset()
            .a(String.format("(%s)", newTodo))
            .reset());

        var todo = new Todo(null, newTodo, LocalDateTime.now());
        todoList.add(todo);

        Unirest.post(BASE_URL + "/api/todo")
            .header("Content-Type", "application/json")
            .body(todo)
            .asJson();

        // 完了
      } else if (MyPrompt.confirm(
          ansi()
              .a("Done? - ")
              .fgCyan()
              .bold()
              .a("Yes")
              .reset()
              .a(", I has done.")
              .a(" or ")
              .fgCyan()
              .a("No")
              .reset()
              .a(", I want to edit.")
              .reset().toString(),
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        System.out.println(ansi()
            .fgGreen()
            .a("  => ")
            .a("DONE!🎉 ")
            .reset()
            .a(String.format("(%s)", selectIndex))
            .reset());

        var todo = todoList.remove(selectIndex);

        Unirest.delete(BASE_URL + "/api/todo")
            .header("Content-Type", "application/json")
            .body(todo)
            .asJson();

        // 編集
      } else {
        var todo = todoList.get(selectIndex);
        final var yarukoto = MyPrompt.input("Edit your todo.", todo.yarukoto);

        System.out.println(ansi()
            .fgGreen()
            .a("  => ")
            .a("EDITED. ")
            .reset()
            .a(String.format("(%s -> %s)", todo.yarukoto, yarukoto))
            .reset());

        todo.yarukoto = yarukoto;

        Unirest.put(BASE_URL + "/api/todo")
            .header("Content-Type", "application/json")
            .body(todo)
            .asJson();

      }

      for (int i = 0; i < 5; i++) {
        Thread.sleep(100);
        System.out.print(".");
      }
      System.out.println();
    }
  }
}
