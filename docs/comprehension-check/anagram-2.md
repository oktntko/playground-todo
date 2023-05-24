# アナグラム問題の解説

## 🙋 解説

時間内にゴールにたどり着けたでしょうか？
時間内にゴールまでたどり着けなかったとしたら、重要なのは、**なぜ時間内にゴールまでたどり着けなかったのか？を分析すること**です。  

時間内にゴールまでたどり着けそうにない場合、実際の業務では、先輩からやり方を押してもらったり、作業自体を変わってもらったりすることがあると思います。
自分でできなかったからダメとか、やり方を教えてもらって答えが分かってよかったとか、かわりの誰かが問題が解決してくれてよかったで終わりではありません。

**「なぜできなかったのか？」を考えましょう**。そうすると、「できなかったこと」が

- 自分に今何が足りていないか
- 何を身に着ける必要があるか？

を教えてくれます。その小さな分析の積み重ねが、「できなかったこと」を「できること」に変えていくのです。

それでは解説をしていきましょう。

### 💡 ゴールまでの筋道を立てる

まず、**いきなりコードを書き始めるのではなく、ゴールまでの筋道を立てましょう。**

例えば、こんな感じ

1. 半角スペース を取り除く
   - a: `"Statue of Liberty"` => `"StatueofLiberty"`
   - b: `"built to stay free"` => `"builttostayfree"`
2. アルファベットの大文字をすべて小文字に変換する
   - a: `"StatueofLiberty"` => `"statueofliberty"`
   - b: `"builttostayfree"` => `"builttostayfree"`
3. 文字列を一定のルールで並び替える
   - a: `"statueofliberty"` => `"abeefilorstttuy"`
   - b: `"builttostayfree"` => `"abeefilorstttuy"`
4. 文字列を比較する

どうすれば「二つの文字列が同じ文字から構成されるかどうか判断」できるか？
を考えずにやり方を探すと（『java 文字列 一致』などの検索ワードで調べてしまうと）

> 文字列は "==" ではなく "equals" を使って比較しろ[^1]  

なんていう結果しか返ってきません。

[^1]: これはこれで重要

ゴールまでの筋道が整理できなかった（もしくは今後の課題で、ここで詰まる傾向が自分に見られる）場合、
ロジカルに物事を考える能力・整理する能力が不足しています。

ゲーム感覚で、ロジックパズルに挑戦しましょう。

- [クイズ大陸](http://quiz-tairiku.com/logic/index.html)
- [幼女の論理クイズ](https://sist8.com/logic)

### 💡 筋道をコードに書き起こす

筋道を考えられれば、あとは**それ（自然言語）をプログラミング言語に翻訳していく**だけです。

1. 『java 半角スペース 取り除く』で検索すれば…
   - `String#trim` や `String#replaceAll(" ", "")` が見つかるはず
2. 『java 大文字 小文字 変換』で検索すれば…
   - `String#toLowerCase` が見つかるはず
   - ついでに、 `String#toUpperCase` も見つかるはず

/// admonition | サンプルコード

```java title="AnagramApp.java" hl_lines="10-20"
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String a = args[0];
    String b = args[1];

    // 1. 半角スペース を取り除く
    a = a.replaceAll(" ", "");
    b = b.replaceAll(" ", "");

    // 2. アルファベットの大文字をすべて小文字に変換する
    a = a.toLowerCase();
    b = b.toLowerCase();

    // 3. 文字列を一定のルールで並び替える

    // 4. 文字列を比較する

    System.out.println(a);
    System.out.println(b);
  }
```

///

「文字を一定のルールで並び替える」はどうでしょうか？
これを１行でコードに書き表す方法はなかなか見つからないです。

そこでまた、**ゴールまでの筋道を立て**ましょう。

- 文字を一定のルールで並び替える
  1. 文字列を１文字ずつに分割する
  2. １文字ずつを並び替える
  3. 分割して並び替えた文字を結合する

このように小さい処理に分解していくことで、知らないことも調べやすくなります。

1. 『java 文字列 １文字 分割』で検索すれば…
   - `String#split("")` が見つかるはず
2. 『java 文字列 配列 並び替え』で検索すれば…
   - `Collections#sort` や `Arrays#sort` が見つかるはず
3. 『java 文字列 配列 結合』で検索すれば…
   - `StringJoiner` や `String#join` が見つかるはず

/// admonition | サンプルコード

```java title="AnagramApp.java" hl_lines="19-29 32"
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String a = args[0];
    String b = args[1];

    // 1. 半角スペース を取り除く
    a = a.replaceAll(" ", "");
    b = b.replaceAll(" ", "");

    // 2. アルファベットの大文字をすべて小文字に変換する
    a = a.toLowerCase();
    b = b.toLowerCase();

    // 3. 文字列を一定のルールで並び替える
    // - 3.1. 文字列を１文字ずつに分割する
    String[] aarray = a.split("");
    String[] barray = b.split("");

    // - 3.2. 並び替える
    Arrays.sort(aarray);
    Arrays.sort(barray);

    // - 3.3. 分割して並び替えた文字を結合する
    a = String.join("", aarray);
    b = String.join("", barray);

    // 4. 文字列を比較する
    System.out.println(a.equals(b));
  }
```

///

### 💡 動きを確認する

コードが書けたら必ず動きを確認しましょう。すべて書けてからではなく、期待通りに動いているか？を都度確認しましょう。
はじめて`replaceAll`を使うならその時に。
はじめて`toLowerCase`を使うならその時に。

なぜプログラミング言語の学習は「Hello World」から始まるのか？  
それは、結果を出力する方法を知らないと、動きを確認することができないからです。

### 💡 見た目を整える

期待通りに動くことを確認したら、見た目を整えましょう。プロとして、期待通りに動くことは最低条件です。
見た目を整えることで、分かりやすくしましょう。

今回、変数`a`と`b`に対して**同じ処理（文字列を一定のルールで整形すること）を実行する必要があります。**
たまたま同じ処理を実行しているわけではありません。
例えば、変数`a`はスペースを取り除くけど、変数`b`は取り除かなかった場合、
入力する順番を変えるだけで、異なる結果が返ってきてしまいます。

```bash
❯ java src/main/java/playground/todo/AnagramApp.java "ARS MAGNA" "anagrams"
true
❯ java src/main/java/playground/todo/AnagramApp.java "anagrams" "ARS MAGNA"
false # 😮
```

同じ処理をすべき場合、同じ処理にします。これを**共通化**といいます。
同じ処理を１つの関数（メソッド）にしてしまうことで実現できます。

/// admonition | サンプルコード

```java title="AnagramApp.java" hl_lines="7 8 13-31"
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String a = AnagramApp.sort(args[0]);
    String b = AnagramApp.sort(args[1]);

    System.out.println(a.equals(b));
  }

  public static String sort(String str) {
    // 1. 半角スペース を取り除く
    str = str.replaceAll(" ", "");

    // 2. アルファベットの大文字をすべて小文字に変換する
    str = str.toLowerCase();

    // 3. 文字列を一定のルールで並び替える
    // - 3.1. 文字列を１文字ずつに分割する
    String[] arr = str.split("");

    // - 3.2. 並び替える
    Arrays.sort(arr);

    // - 3.3. 分割して並び替えた文字を結合する
    str = String.join("", arr);

    return str;
  }
```

///

見た目を整えたら、再度動きを確認して完成です。