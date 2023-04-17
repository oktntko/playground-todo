package playground.todo;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.fusesource.jansi.AnsiConsole;

import de.codeshelf.consoleui.elements.ConfirmChoice;

public class App {

  public static final String ADD_TODO = "＋ADD TODO";

  public static void main(String[] args) throws InterruptedException, IOException {
    AnsiConsole.systemInstall();

    System.out.println(ansi()
        .fgGreen()
        .a("Welcome back!")
        .reset());

    List<String> todoList = TodoList.load();

    for (;;) {
      // メニュー表示
      List<String> menu = new ArrayList<>();
      menu.addAll(todoList);
      menu.add(ADD_TODO);
      final var select = MyPrompt.select(
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
      if (Objects.equals(select, ADD_TODO)) {
        final var newTodo = MyPrompt.input("Enter your todo.");
        todoList.add(newTodo);
        TodoList.save(todoList);
        System.out.println(ansi()
            .fgGreen()
            .a("  => ")
            .a("CREATED new todo. ")
            .reset()
            .a(String.format("(%s)", newTodo))
            .reset());

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
        todoList.remove(select);
        TodoList.save(todoList);
        System.out.println(ansi()
            .fgGreen()
            .a("  => ")
            .a("DONE!🎉 ")
            .reset()
            .a(String.format("(%s)", select))
            .reset());

        // 編集
      } else {
        final var newTodo = MyPrompt.input("Edit your todo.", select);
        todoList.replaceAll(todo -> Objects.equals(todo, select) ? newTodo : todo);
        TodoList.save(todoList);
        System.out.println(ansi()
            .fgGreen()
            .a("  => ")
            .a("EDITED. ")
            .reset()
            .a(String.format("(%s -> %s)", select, newTodo))
            .reset());

      }
    }
  }
}
