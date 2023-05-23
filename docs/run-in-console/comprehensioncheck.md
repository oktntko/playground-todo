# 理解度を確認しよう

意図通りであることを前提として

- 早いこと
  - 完成までが早い、処理が終わるまでが早い、
- 分かりやすいこと（想定する相手に）
  - ...
- ???
  - ...

## FizzBuzz問題

### ❔ 問題 (目標⏰: 10分)

1 から 30 までの数を順番に出力すること。  
ただし、以下のルールを満たすこと。

- 数が 3 の倍数の時はFizzと出力すること
- 数が 5 の倍数の時はBuzzと出力すること
- 数が 3 の倍数かつ 5 の倍数の時はFizzBuzzと出力すること

/// details | 🖨️ 出力結果

```bash
1
2
Fizz
4
Buzz
Fizz
7
8
Fizz
Buzz
11
Fizz
13
14
FizzBuzz
16
17
Fizz
19
Buzz
Fizz
22
23
Fizz
Buzz
26
Fizz
28
29
FizzBuzz
```

///

/// details | 🔰 ベースコード

```java title="FizzBuzzApp.java"
package playground.todo;

public class FizzBuzzApp {

  public static void main(String[] args) {
    System.out.println("fizzbuzz");
  }
}
```

///

/// details | ▶️ 実行方法

```bash
❯ cd ~/my-todo/todo-app
❯ java src/main/java/playground/todo/FizzBuzzApp.java
fizzbuzz
```

※ターミナルから実行する場合

///

### 🙋 解説

まずは、**1 から 30 までの数を順番に出力しましょう。**基本中の基本、`for`文を使います。

/// details | 🚫 サンプルコード

```java title="FizzBuzzApp.java" hl_lines="2-4"
  public static void main(String[] args) {
    for (int i = 1; i <= 30; i++) {
      System.out.println(i);
    }
  }
```

///

次に、**「 n の倍数のとき」をプログラムで書き表せる言葉に翻訳します。**
「 n の倍数のとき」を翻訳すると、「 n で割り切れるとき」になります。

では、「 n で割り切れるとき」をプログラム言語で書くとどうなるでしょうか？もう少し翻訳が必要ですね。
「 n で割り切れるとき」をさらに翻訳すると、**①「 n で割ったときの余りが 0 」** **②「のとき」**になります。

①「 n で割ったときの余りが 0 」という表現は、算術演算子「`A % B`（AをBで割った余り）」と、比較演算子「`==`（一致する）」と相性が良くなりました。  
②「のとき」は`if`文を使えばいいですね。  

では、コードに書いてみましょう。

/// details | 🚫 サンプルコード

```java title="FizzBuzzApp.java" hl_lines="3-9 11"
  public static void main(String[] args) {
    for (int i = 1; i <= 30; i++) {
      if (i % 3 == 0) {
        System.out.println("Fizz");
      } else if (i % 5 == 0) {
        System.out.println("Buzz");
      } else if (i % 15 == 0) {
        System.out.println("FizzBuzz");
      } else {
        System.out.println(i);
      }
    }
  }
```

///

ここまでかけたら、実際に動かして確認してみましょう。

---

15 と 30 が `FizzBuzz`にならずに、`Fizz`になってしまいます。
問題文が少し意地悪で、問題文の通りに`if`文を書くと、期待通りに動きません。
**プログラムは上から順場に処理する**ので、 15 が「 3 の倍数である」という条件に引っ掛かり、
`Fizz`が出力されます。なので、評価する順番を直しましょう。

---

完成したものがこちら。

/// details | 🚫 サンプルコード

```java title="FizzBuzzApp.java" hl_lines="3-5"
  public static void main(String[] args) {
    for (int i = 1; i <= 30; i++) {
      if (i % 15 == 0) {
        System.out.println("FizzBuzz");
      } else if (i % 3 == 0) {
        System.out.println("Fizz");
      } else if (i % 5 == 0) {
        System.out.println("Buzz");
      } else {
        System.out.println(i);
      }
    }
  }
```

///

## アナグラム(anagram)

### ❔ 問題 (目標⏰: 20分)

二つの文字列が同じ文字から構成されるかどうか判断し、同じなら`true`を違うなら`false`を出力すること。  
ただし、以下のルールを満たすこと。

- 半角スペースは二つの文字列からは無視すること。
- アルファベットの大文字小文字の違いは無視すること。

/// details | 🖨️ 出力結果

```bash
❯ java src/main/java/playground/todo/AnagramApp.java "Statue of Liberty" "built to stay free"
true
❯ java src/main/java/playground/todo/AnagramApp.java "anagrams" "ARS MAGNA"
true
❯ java src/main/java/playground/todo/AnagramApp.java "どいとうかつき" "かいとうきつど"
true
❯ java src/main/java/playground/todo/AnagramApp.java "古川大" "叶才三"
false
❯ java src/main/java/playground/todo/AnagramApp.java "tokiwa kanenari" "wakita kanenori"
true
❯ java src/main/java/playground/todo/AnagramApp.java "水無 怜奈" "Ms.007"
false
```

///

/// details | 🔰 ベースコード

```java title="AnagramApp.java"
package playground.todo;

public class AnagramApp {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String a = args[0];
    String b = args[1];

    System.out.println(a);
    System.out.println(b);
  }
}
```

///

/// details | ▶️ 実行方法

```bash
❯ cd ~/my-todo/todo-app
❯ java src/main/java/playground/todo/AnagramApp.java "a" "b"
a
b
❯ java src/main/java/playground/todo/AnagramApp.java
引数に２つの文字を入力してください。
```

※ターミナルから実行する場合

///

### 🙋 解説

時間内にゴールにたどり着けたでしょうか？
時間内にゴールまでたどり着けなかったとしたら、重要なのは、**なぜ時間内にゴールまでたどり着けなかったのか？を分析すること**です。  

実際の業務では、先輩からやり方を押してもらったり、作業自体を変わってもらったりすることがあると思います。
自分でできなかったからダメとか、やり方を教えてもらって答えが分かってよかったとか、かわりの誰かが問題が解決してくれてよかったで終わりではありません。

「なぜできなかったのか？」を考えることで、「できなかったこと」が

- 自分に今何が足りていないか
- 何を身に着ける必要があるか？

を教えてくれます。その小さな分析の積み重ねが、「できなかったこと」を「できること」に変えていくのです。

それでは解説をしていきましょう。

#### 💡 ゴールまでの筋道を立てる

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
を考えずにやり方を探すと（『文字列 一致』などの検索ワードで調べてしまうと）

> 文字列は "==" ではなく "equals" を使って比較しろ  

なんていう結果しか返ってきません。

ゴールまでの筋道が整理できなかった（もしくは今後の課題で、ここで詰まる傾向が自分に見られる）場合、
ロジカルに物事を考える能力・整理する能力が不足しています。

#### 💡 筋道をコードに書き起こす

筋道を考えられれば、あとは**それ（自然言語）をプログラミング言語に翻訳していく**だけです。

1. 『java 半角スペース 取り除く』で検索すれば…
   - `String#trim` や `String#replaceAll(" ", "")` が見つかるはず
2. 『java 大文字 小文字 変換』で検索すれば…
   - `String#toLowerCase` が見つかるはず
   - ついでに、 `String#toUpperCase` も見つかるはず

/// details | 🚫 サンプルコード

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

/// details | 🚫 サンプルコード

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

#### 💡 動きを確認する

コードが書けたら必ず動きを確認しましょう。すべて書けてからではなく、期待通りに動いているか？を都度確認しましょう。
はじめて`replaceAll`を使うならその時に。
はじめて`toLowerCase`を使うならその時に。

なぜプログラミング言語の学習は「Hello World」から始まるのか？  
それは、動きを確認するために、結果を出力する方法を学ぶのが最優先だからです。

#### 💡 見た目を整える

期待通りに動くことを確認したら、見た目を整えましょう。プロとして、期待通りに動くことは最低条件です。
見た目を整えることで、分かりやすくしましょう。

今回、変数`a`と`b`に対して同じ処理（文字列を一定のルールで整形すること）を実行しています。
かつ、**同じ処理を実行する必要があります。**
例えば、変数`a`はスペースを取り除くけど、変数`b`は取り除かなかった場合、
入力する順番を変えるだけで、異なる結果が返ってきてしまいます。

```bash
❯ java src/main/java/playground/todo/AnagramApp.java "ARS MAGNA" "anagrams"
true
❯ java src/main/java/playground/todo/AnagramApp.java "anagrams" "ARS MAGNA"
false # "(-""-)"
```

同じ処理をすべき場合、同じ処理にします。これを共通化といいます。
同じ処理を１つの関数（メソッド）にしてしまうことで実現できます。

/// details | 🚫 サンプルコード

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

## 厄年計算

### ❔ 問題 (目標⏰: 30分)

※ここで取り扱う日付はすべて西暦とする。  
「生年月日」と「性別」の二つの入力値から、厄年を出力するプログラムを作ること。

- 出力するのは本厄の年のみとすること。本厄は男性が数え年で4歳、25歳、42歳、61歳、女性が19歳、33歳、37歳、61歳のときとする。
  - ※諸説あり。
- 数え年は、生まれた時点で1歳とし、1月1日を迎えるたびに1歳ずつ加えていくこととする。
  - ※諸説あり。

/// details | 🖨️ 出力結果

WEB上の厄年計算サイトを参考にしましょう。

///

/// details | 🔰 ベースコード

```java title="CalcYakudosiApp.java"
package playground.todo;

public class CalcYakudosiApp {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String a = args[0];
    String b = args[1];

    System.out.println(a);
    System.out.println(b);
  }
}
```

///

/// details | ▶️ 実行方法

```bash
❯ cd ~/my-todo/todo-app
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "a" "b"
a
b
❯ java src/main/java/playground/todo/CalcYakudosiApp.java
引数に２つの文字を入力してください。
```

※ターミナルから実行する場合

///


### 🙋 解説

#### 💡 ゴールまでの筋道を立ててコードに書いて動きを確認して見た目を整える

これもいきなりコードを書き始めるのではなく、ゴールまでの筋道を考えましょう。
しかし、日付の計算は頭がこんがらがりやすいです。さらに、「数え年」なる聞きなれない言葉も出てきました。

こういうときは、**サンプルデータを使って考えましょう。**

| 項目     | 値         |
| -------- | ---------- |
| 生年月日 | 1999-12-12 |
| 性別     | 男         |

この人はいつ厄年になるでしょうか？地道にみていきます。

|  年齢 | 年      |
| ----: | ------- |
|  1 歳 | 1999 年 |
|  4 歳 | 2002 年 |
| 25 歳 | 2023 年 |
| 42 歳 | 2040 年 |
| 61 歳 | 2059 年 |

年齢の差分年が増えていっているだけですね。**「数え年」は起点を１歳とするかどうか**で、**生年月日は年の部分だけが重要**であることが分かりました。
性別が女だったとしたらどうでしょうか？

|  年齢 | 年      |
| ----: | ------- |
|  1 歳 | 1999 年 |
| 19 歳 | 2017 年 |
| 33 歳 | 2031 年 |
| 37 歳 | 2035 年 |
| 61 歳 | 2059 年 |

加算する数字が違うだけですね。
これでゴールまでの筋道が立てられそうです。

1. 「生年月日」から「年」を数値として抽出する＝`生年`とする
2. 性別によって加算するデータを切り替える＝`厄年齢リスト`とする
3. `厄年齢リスト`を繰り返し処理する＝一つのデータは`厄年齢`とする
   - `生年`＋`厄年齢`－１を出力する

変数名まで決められました。また、表を使って考えたことで、これをそのまま出力結果にするアイディアも生まれました。

あとはアナグラムのときと同様に、プログラミング言語に翻訳していきます。
言語仕様が分からないところは検索していきます。

/// details | 🚫 サンプルコード

```java title="CalcYakudosiApp.java" hl_lines="7-28"
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String 生年月日 = args[0];
    String 性別 = args[1];

    // https://docs.oracle.com/javase/jp/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE
    int 生年 = LocalDate.parse(生年月日, DateTimeFormatter.ISO_LOCAL_DATE).getYear(); // yyyy-MM-dd

    var 厄年齢リスト = return厄年齢リスト(性別);

    for (var 厄年齢 : 厄年齢リスト) {
      System.out.println(
          String.format("%2s", String.valueOf(厄年齢)) + " 歳 : "
              + (生年 + 厄年齢 - 1) + " 年");
    }
  }

  public static List<Integer> return厄年齢リスト(String 性別) {
    if (性別.equals("男")) {
      return List.of(4, 25, 42, 61); // 男性
    } else {
      return List.of(19, 33, 37, 61); // 女性
    }
  }
```

ちょっと解説。

日付を扱うのに`LocalDate`クラスを使いました。`LocalDate`はJava8から生まれたクラスです。
既存の`Date`や`Calendar`の使いづらいところを解決するために爆誕しました。
が、どのクラスを使うかは重要ではありません。ここで押さえておいてほしかったのは、次の２点です。

1. どのクラスを使っても日付を扱うのは面倒だということ
2. 日付のフォーマットはいろんなものがあるといこと

```java linenums="10"
    // https://docs.oracle.com/javase/jp/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE
    int 生年 = LocalDate.parse(生年月日, DateTimeFormatter.ISO_LOCAL_DATE).getYear(); // yyyy-MM-dd
```

次のループ処理を共通化するために厄年齢リストを決める処理を関数化しています。見た目を整えていますね。

```java linenums="13"
    var 厄年齢リスト = return厄年齢リスト(性別);
```

`String#format`で年齢をスペースで埋めて、「4 歳」を表示するときにずれないようにしています。見た目を整えていますね。

```java linenums="17"
          String.format("%2s", String.valueOf(厄年齢)) + " 歳 : "
```

///

/// details | 🖨️ 出力結果

```bash
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "男"
 4 歳 : 2002 年
25 歳 : 2023 年
42 歳 : 2040 年
61 歳 : 2059 年
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "女"
19 歳 : 2017 年
33 歳 : 2031 年
37 歳 : 2035 年
61 歳 : 2059 年
```

///

これで完成！と言いたいところですが、完成していません。少し入力を変えると思った通りに動きません。

/// details | 存在しない日付を入力すると、エラーが発生してしまいます。

```bash
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-02-29" "男"
Exception in thread "main" java.time.format.DateTimeParseException: Text '1999-02-29' could not be parsed: Invalid date 'February 29' as '1999' is not a leap year
        at java.base/java.time.format.DateTimeFormatter.createError(DateTimeFormatter.java:2023)
        at java.base/java.time.format.DateTimeFormatter.parse(DateTimeFormatter.java:1958)
        at java.base/java.time.LocalDate.parse(LocalDate.java:430)
        at playground.todo.CalcYakudosiApp.main(CalcYakudosiApp.java:19)
```

///

/// details | 男の子を自称すると、女性扱いになります。

```bash
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "男の子"
19 歳 : 2017 年
33 歳 : 2031 年
37 歳 : 2035 年
61 歳 : 2059 年
```

///

これでは、「意図通りであること」を満たすことができません。
**プログラムは、正しい入力をしないと、意図通りに動かない**のです。
入力のパターンを増やすと必ずエラーが発生します。
意図通りに動かすためには、入力パターンを考えることが重要です。

#### 💡 入力パターンを考える

入力できるのは２つで、「生年月日」と「性別」です。
まず、「性別」の入力パターンから考えます。

期待していたのは「男」「女」です。[^1]
「男性」「女性」や「male」「female」の入力が考えられますが、
「男」「女」以外だったら入力しなおしてもらいましょう。

[^1]: 「無回答」や「その他」を考慮できた人は素晴らしいです。が、この問題の本意ではないので省きます。

次に「生年月日」の入力パターンを考えます。
まず、正しさを整理します。正しく「生年月日」を入力するには、

- 存在する日付であること
- 期待する日付フォーマットであること
