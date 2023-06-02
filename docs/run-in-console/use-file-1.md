# ファイルを使おう

アプリを起動しなおすとやることが消えてしまいます。
紙の方がマシです。それではこのToDoアプリは使いものになりません。

プログラム上のListオブジェクトですね。
Java が動く環境のメモリに保存されています。
パソコンのメモリは揮発性です。電源を落とすとデータが消えてしまいます。
同様に、Java のメモリもアプリを停止するとデータが消えてしまいます。

なので、アプリの外部に保存する必要があります。
パソコンにはメモリ（作業スペース）とストレージ（引き出し）がありました。

ここで、ファイルの登場です。
ファイルはアプリにとってのストレージ代わりになります。
ファイルにデータを保存するのは普通ですね。

では、ファイルを使ってみましょう。

## ゴールと道筋の整理

いきなりコードを書き始めてはいけません。
ゴールとゴールまでの道筋を整理するのが先です。

"システム"を少し分解すると、

1. UIの提供(`MyPrompt.java`)
2. 処理のコントロール(ループ、条件分岐、入力されたデータを保存したり、登録済みのデータから出力したり)
3. データの管理(`List<String> todoList`)

の３つの役割がありました。

今回のゴールは、やることが消えないようにするために、ファイルに"3. データの管理"をやってもらうことです。

ファイルが登場したので、シーケンス図に新しい仲間"ファイル"を加えましょう。
そして、"システム"という名前から"アプリ"という名前にしましょう。

/// admonition | "アプリ"と"ファイル"を合わせて"システム"という意味で。
///

```mermaid
sequenceDiagram
  autonumber
  actor ユーザ
  participant app as アプリ
  participant storage as ファイル

  loop ;;

    app ->> ユーザ: 登録済みのやることを表示する
    ユーザ ->> ユーザ: 入力したやることを見ることができる

    alt やることが増えた
      ユーザ ->> app: やることを入力する
      app ->> app: 入力されたやることを登録する

    else やることが完了した
      ユーザ ->> app: 入力したやることを選ぶ
      app ->> app: 登録済みのやることを削除する

    end
  end
```

では、どうすればデータの管理をファイルに任せることができるでしょうか？
"登録"という言葉があるところが、やることデータを管理しているところですね。
上の図では

①登録済みのやることを表示する  
④入力されたやることを登録する  
⑥登録済みのやることを削除する  

が該当します。ここをファイルに任せればいいのです。

```mermaid
sequenceDiagram
  autonumber
  actor ユーザ
  participant app as アプリ
  participant storage as ファイル

  loop ;;

    app ->> storage: ファイルを読み込む
    storage -->> app: データを返す
    app ->> ユーザ: 登録済みのやることを表示する
    ユーザ ->> ユーザ: 入力したやることを見ることができる

    alt やることが増えた
      ユーザ ->> app: やることを入力する
      app ->> storage: 入力されたやることをファイルに書き込む

    else やることが完了した
      ユーザ ->> app: 入力したやることを選ぶ
      app ->> storage: 削除するやることをファイルに書き込む

    end
  end
```

まず、①②でファイルに保存したやることを読み込みます。  
次に、⑥⑧でファイルに入力されたやることを書き込みましょう。

あとは読み込み・書き込みの処理をどうやって書くのか？
ということを調べれば十分ですね。

コードに書いていきましょう。

---

## ファイルに書き込む

処理の順番は読み込み➡書き込みですが、ないものを読み込めません。[^1]
なので、書き込みから始めましょう。

[^1]: ファイルを作ってもいいです。ただ、どう保存するかわかっている必要があります。

『java ファイル 書き込み』で調べるといろいろ出てきて困ったと思います。
正解も不正解もないですが、現在は下に示す`java.nio.file.Files`が一番簡単に書けると思います。

/// admonition | サンプルコード

```java title="FirstApp.java"
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FirstApp {

  // main メソッドは省略しています

  static final String FILE_PATH = "todo-list.txt";

  public static void save(List<String> todoList) throws IOException {
    Path path = Paths.get(FILE_PATH);

    Files.write(path, todoList,
        StandardCharsets.UTF_8,
        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
  }
}
```

///

/// admonition | 文字コード UTF-8
ファイルや次に出てくるデータベースを扱う上で、データがへんな感じになったら大体こいつのせいです。
ちょうどいいくらいの解説をしているサイトがみつからなかったので、自分で調べましょう。
///

/// admonition | StandardOpenOption
[StandardOpenOptionの説明](https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/StandardOpenOption.html)
を読んでもわからないものはわからないと思うので挙動を試してみましょう
///

ファイルの保存メソッドを使ってmainメソッドを書き換えます。

/// admonition | サンプルコード

```java title="FirstApp.java" hl_lines="21 26"
  public static void main(String[] args) throws IOException {
    AnsiConsole.systemInstall();

    System.out.println("📝 Welcome Back, My To-Do!");

    List<String> todoList = new ArrayList<>();

    final String MENU_ADD = "➕ やることが増えた";

    for (;;) {
      List<String> menu = new ArrayList<>();
      menu.addAll(todoList);
      menu.add(MENU_ADD);

      int index = MyPrompt.select("あなたのやることリスト", menu);
      var todo = menu.get(index);

      if (todo == MENU_ADD) {
        String newToDo = MyPrompt.input("やることを入力してください >");
        todoList.add(newToDo);
        save(todoList);

      } else if (MyPrompt.confirm("「" + todo + "」は完了しましたか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        todoList.remove(index);
        save(todoList);
      }
    }
  }
```

///

/// admonition | 相対パスと絶対パス
ファイルはどこにできましたか？サンプルコードのようにパスを書いた人はソースコードがあるフォルダにできたのではないでしょうか。
詳しい解説は[絶対パスと相対パスの違いは？メリット・デメリット、使い分け方を解説](https://and-engineer.com/articles/Y1fGpxAAAAt07ZlQ)
もう知っておくべきは`.(dot)`が現在のディレクトリ、`..(dot dot)`が一つうえのディレクトリを示すということです。
少しパスを変えて遊んでみましょう。
///

ファイルの操作を調べるのは手こずったかもしれません。
でも、処理はとても簡単でしたね。

## ファイルから読み込む

『java ファイル 読み込み』で調べるといろいろ出てきて困ったと思います。
正解も不正解もないですが、現在は下に示す`java.nio.file.Files`が一番簡単に書けると思います。

<figure markdown>
  ![Déjà-vu.png](Déjà-vu.png){ width="480" }
</figure>

/// admonition | サンプルコード

```java title="FirstApp.java"

public class FirstApp {

  // main メソッドは省略しています

  static final String FILE_PATH = "todo-list.txt";

  public static List<String> load() throws IOException {
    Path path = Paths.get(FILE_PATH);
    if (Files.notExists(path)) {
      return new ArrayList<>();
    }

    return Files.readAllLines(path, StandardCharsets.UTF_8);
  }
}
```

///

ファイルがないときに`Files#readAllLines`を実行すると
`java.nio.file.NoSuchFileException`が発生するため、
ファイルがなかったら空のListを返すようにしています。

ファイルの読み込みメソッドを使ってmainメソッドを書き換えます。

/// admonition | サンプルコード

```java title="FirstApp.java" hl_lines="6"
  public static void main(String[] args) throws IOException {
    AnsiConsole.systemInstall();

    System.out.println("📝 Welcome Back, My To-Do!");

    List<String> todoList = load();

    final String MENU_ADD = "➕ やることが増えた";

    for (;;) {
      List<String> menu = new ArrayList<>();
      menu.addAll(todoList);
      menu.add(MENU_ADD);

      int index = MyPrompt.select("あなたのやることリスト", menu);
      var todo = menu.get(index);

      if (todo == MENU_ADD) {
        String newToDo = MyPrompt.input("やることを入力してください >");
        todoList.add(newToDo);
        save(todoList);

      } else if (MyPrompt.confirm("「" + todo + "」は完了しましたか？",
          ConfirmChoice.ConfirmationValue.YES) == ConfirmChoice.ConfirmationValue.YES) {
        todoList.remove(index);
        save(todoList);
      }
    }
  }
```

///

アプリを停止してもToDoはなくなりません。
かなりいい感じになったのではないでしょうか？
