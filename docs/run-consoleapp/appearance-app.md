# 見た目にこだわろう

## コードの見た目は整っている？

/// admonition | サンプルコード

```java title="FirstApp.java"
public class FirstApp {

  public static void main(String[] args) throws IOException, SQLException {
    AnsiConsole.systemInstall();

    System.out.println("📝 Welcome Back, My To-Do!");

    List<ToDo> todoList = TryDatabase.select();

    final String MENU_LABEL = "➕ やることが増えた";
    final ToDo MENU_ADD = new ToDo(null, MENU_LABEL, null);

    for (;;) {
      List<ToDo> menu = new ArrayList<>();
      menu.addAll(todoList);
      menu.add(MENU_ADD);

      int index = MyPrompt.select("あなたのやることリスト", menu);
      var todo = menu.get(index);

      if (todo == MENU_ADD) {
        ToDo newToDo = MyPrompt.form("", "");
        int id = TryDatabase.insert(newToDo.yarukoto(), newToDo.kizitu());
        todoList.add(new ToDo(id, newToDo.yarukoto(), newToDo.kizitu()));

      } else if (MyPrompt.confirm("「" + todo.yarukoto() + "」を変更しますか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        ToDo newToDo = MyPrompt.form(todo.yarukoto(), LocalDateUtils.toString(todo.kizitu()));
        TryDatabase.update(newToDo.yarukoto(), newToDo.kizitu(), todo.id());
        todoList.set(index, newToDo);

      } else if (MyPrompt.confirm("「" + todo.yarukoto() + "」は完了しましたか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        TryDatabase.delete(todo.id());
        todoList.remove(index);
      }
    }
  }
}
```

///

## 使い勝手は整っている？

ウェブサイトでは、何かするとボタンの色が変わったり何かが動いたり「操作した感」を得られることが多いです。
一方、コンソールアプリでは、アプリからのリアクションの少なさが気になってしまいます。

テキストのメッセージを入れましょう。

/// admonition | サンプルコード

```java title="FirstApp.java" hl_lines="5 12 18"
      if (todo == MENU_ADD) {
        ToDo newToDo = MyPrompt.form("", "");
        int id = TryDatabase.insert(newToDo.yarukoto(), newToDo.kizitu());
        todoList.add(new ToDo(id, newToDo.yarukoto(), newToDo.kizitu()));
        System.out.println(newToDo.yarukoto() + "を追加しました。");

      } else if (MyPrompt.confirm("「" + todo.yarukoto() + "」を変更しますか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        ToDo newToDo = MyPrompt.form(todo.yarukoto(), LocalDateUtils.toString(todo.kizitu()));
        TryDatabase.update(newToDo.yarukoto(), newToDo.kizitu(), todo.id());
        todoList.set(index, newToDo);
        System.out.println(todo.yarukoto() + "を変更しました。");

      } else if (MyPrompt.confirm("「" + todo.yarukoto() + "」は完了しましたか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        TryDatabase.delete(todo.id());
        todoList.remove(index);
        System.out.println(todo.yarukoto() + "を完了しました。");
      }
```

///

物足りません。

## 見た目を整える

/// admonition | サンプルコード

```java title="FirstApp.java"

public class FirstApp {

  public static void main(String[] args) throws IOException, SQLException, InterruptedException {
    AnsiConsole.systemInstall();

    System.out.println("📝 Welcome Back, My To-Do!");

    List<ToDo> todoList = TryDatabase.select();

    final String MENU_LABEL = "➕ やることが増えた";
    final ToDo MENU_ADD = new ToDo(null, MENU_LABEL, null);

    for (;;) {
      List<ToDo> menu = new ArrayList<>();
      menu.addAll(todoList);
      menu.add(MENU_ADD);

      int index = MyPrompt.select("あなたのやることリスト", menu);
      var todo = menu.get(index);

      if (todo == MENU_ADD) {
        ToDo newToDo = MyPrompt.form("", "");
        int id = TryDatabase.insert(newToDo.yarukoto(), newToDo.kizitu());
        todoList.add(new ToDo(id, newToDo.yarukoto(), newToDo.kizitu()));

        sleep(200);
        printMessage("🆕", newToDo.yarukoto(), "を追加しました。");

      } else if (MyPrompt.confirm("「" + todo.yarukoto() + "」を変更しますか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        ToDo newToDo = MyPrompt.form(todo.yarukoto(), LocalDateUtils.toString(todo.kizitu()));
        TryDatabase.update(newToDo.yarukoto(), newToDo.kizitu(), todo.id());
        todoList.set(index, newToDo);

        sleep(200);
        printMessage("📝", todo.yarukoto(), "を変更しました。");

      } else if (MyPrompt.confirm("「" + todo.yarukoto() + "」は完了しましたか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        TryDatabase.delete(todo.id());
        todoList.remove(index);

        sleep(200);
        printMessage("✅", todo.yarukoto(), "を完了しました。");
      }

      System.out.println();
      sleep(100);
    }
  }

  private static void printMessage(String icon, String yarukoto, String message) {
    System.out.println(Ansi.ansi()
        .a(icon + " ")
        .fgGreen()
        .bold()
        .a(yarukoto)
        .reset()
        .a(" " + message)
        .toString());
  }

  private static void sleep(long millis) throws InterruptedException {
    for (int i = 0; i < 5; i++) {
      Thread.sleep(millis);
      System.out.print(".");
    }
    System.out.println();
  }
}

```

///

まあよしとする。
