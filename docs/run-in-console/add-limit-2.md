# 期日も管理しようの解説

今回、ゴールは明確でした。

道筋を立てるうえで重要なのは、現在地点を把握することです。
そして、ゴールとの違いをリストアップすることです。

現在地点とゴールとの違いを一つずつ埋めていけば必ずゴールにたどり着けます。

## 期日を管理するってどうやって？

今、やることは`String`型で持っています。
`"期日 | やること"`みたいに文字列として結合しましょうか？
それだと、２つ目のゴールであるデータの変更に対応するのが難しくなりそうです。

やることを管理する別のクラスを作って、その中に

- やること
- 期日

の２つの項目を持たせましょう。

/// admonition | サンプルコード

`record`を使っていますが、普通にclassで宣言するのとあんまり変わらないはずです。

```java title="ToDo.java"
package playground.todo;

import java.time.LocalDate;

public record ToDo(String yarukoto, LocalDate kizitu) {
}
```

///

`String`からこのデータクラス`ToDo`に置き換えていくことで、
自然とゴールまで行けそうです。

```diff title="FirstApp.java"
-    List<String> todoList = load();
+    List<ToDo> todoList = load();
```

とっつきやすそうなところから順番に対応していきます。

## どうやってファイルへ書き込んで読み込むか？

**CSVファイル形式 or TSVファイル形式**を使います。
気づくか気づかないかというより、知っているか知らないかです。
ただ、エンジニアとしては知らないと恥ずかしいです。

- CSVファイル
  - `Comma Separated Value`. カンマ区切りのファイルです。
- TSVファイル
  - `Tab Separated Value`. タブ区切りのファイルです。

見たこともない人はファイルを見たほうが早いでしょう。

どちらも特殊な書き込み・読み込みの必要はなく、
区切りたい位置にカンマかタブを加えるだけです。

/// admonition | サンプルコード

```java title="FirstApp.java" hl_lines="1 7-10 13 16-19"
  public static List<ToDo> load() throws IOException {
    Path path = Paths.get(FILE_PATH);
    if (Files.notExists(path)) {
      return new ArrayList<>();
    }

    return Files.readAllLines(path, StandardCharsets.UTF_8).stream().map(line -> {
      String[] data = line.split("\t", -1);
      return new ToDo(data[0], LocalDateUtils.converToLocalDate(data[1]));
    }).collect(Collectors.toList());
  }

  public static void save(List<ToDo> todoList) throws IOException {
    Path path = Paths.get(FILE_PATH);

    Files.write(path, todoList.stream()
        .map(todo -> {
          return todo.yarukoto() + "\t" + LocalDateUtils.converToString(todo.kizitu());
        }).toList(),
        StandardCharsets.UTF_8,
        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
  }
```

書き込むときは`\t(タブ)`か`,(カンマ)`を項目の間に入れます。

```java
          return todo.yarukoto() + "\t" + LocalDateUtils.converToString(todo.kizitu());
```

読み込むときは区切り文字で分割して、オブジェクトに変換します。

```java
      String[] data = line.split("\t", -1);
      return new ToDo(data[0], LocalDateUtils.converToLocalDate(data[1]));
```

- タブ文字
- イミュータブルリスト
- LocalDateUtils nullの扱い
///

## 型が合わなくなったメニュー「追加」をどうするか？

やることも`String`、追加するためのメニューも`String`だったのでおなじListに入れられました。
その関係が崩れてしまいました。型が違うといわれてしまいます。

難しく考える必要はありません。合わせればいいだけです。

/// admonition | サンプルコード

```java title="FirstApp.java" hl_lines="8-9 12"
  public static void main(String[] args) throws IOException {
    AnsiConsole.systemInstall();

    System.out.println("📝 Welcome Back, My To-Do!");

    List<ToDo> todoList = load();

    final String MENU_LABEL = "➕ やることが増えた";
    final ToDo MENU_ADD = new ToDo(MENU_LABEL, null);

    for (;;) {
      List<ToDo> menu = new ArrayList<>();
      menu.addAll(todoList);
      menu.add(MENU_ADD);

      int index = MyPrompt.select("あなたのやることリスト", menu);
      var todo = menu.get(index);

      if (todo == MENU_ADD) {
```

///

## 完了を確認するメッセージをどうするか？

```diff
-      } else if (MyPrompt.confirm("「" + todo + "」は完了しましたか？",
+      } else if (MyPrompt.confirm("「" + todo.yarukoto() + "」は完了しましたか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
```

## やることを選ぶ UI (`MyPrompt#select`) をどうするか？

まず、`MyPrompt#select` です。素直にエラーを消していけばいいだけですね。

/// admonition | サンプルコード

```java title="FirstApp.java" hl_lines="1 13-14"
  public static int select(String message, List<ToDo> options) throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt();
    ListPromptBuilder builder = prompt
        .getPromptBuilder()
        .createListPrompt()
        .name(name)
        .message(message);

    for (int i = 0; i < options.size(); i++) {
      var option = options.get(i);
      String kizitu = String.format("%10s", LocalDateUtils.converToString(option.kizitu()));
      builder.newItem(String.valueOf(i)).text(kizitu + " | " + option.yarukoto()).add();
    }

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (ListResult) map.get(name);

    return Integer.parseInt(result.getSelectedId());
  }
```

///

あとは期日の入力だけです！

## 期日の入力 (`MyPrompt#input`) をどうするか？

まずはコンパイルエラーだけ解消しましょう。

/// admonition | サンプルコード

```java title="ToDo.java" hl_lines="2 3"
      if (todo == MENU_ADD) {
        String yarukoto = MyPrompt.input("やることを入力してください >");
        ToDo newToDo = new ToDo(yarukoto, null);
        todoList.add(newToDo);
        save(todoList);

        /// else if
      } 
```

///

次に、期日の入力は"厄年計算"でやりましたね。
しかし、入力値のチェックとかいろいろありました。

"厄年計算"のまま入れるとどうなるでしょうか？

/// admonition | サンプルコード

```java title="ToDo.java" hl_lines="4-8 12-14"
      if (todo == MENU_ADD) {
        String yarukoto = MyPrompt.input("やることを入力してください >");

        try {
          String 期日 = MyPrompt.input("期日を入力してください。(例: 2017-07-17)");
          LocalDate kizitu = LocalDate.parse(期日, DateTimeFormatter.ISO_LOCAL_DATE);

          ToDo newToDo = new ToDo(yarukoto, kizitu);
          todoList.add(newToDo);
          save(todoList);

        } catch (DateTimeParseException e) {
          System.err.println("期日はyyyy-MM-dd形式で入力してください。(例: 2017-07-17)");
        }

        /// else if
      } 
```

///

コードの見通しは悪くなりましたが、ほぼそのまま使うことができました。
さっそく経験が生きてきました。

このように自分で書いたコードは自分のためになるのです。

しかし、期日を入力しなかったり、`yyyy-MM-dd形式`に沿わない入力してしまった場合、
"やること"の入力からやり直しになってしまいます。

とはいえ、"やることの期日を管理すること"ができたので、いったん

<p style="font-size: 24px; text-align: center; font-weight: bold;">完成！🎉</p>

## 変更できるようにするってどうやって？

登録と完了(削除)ができているので、それを転用します。
