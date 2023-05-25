# 🙋 厄年計算の解説

TODO: 振り返り解説

## 💡 ゴールまでの筋道を立ててコードに書いて動きを確認して見た目を整える

これもいきなりコードを書き始めるのではなく、ゴールまでの筋道を考えましょう。
しかし、日付の計算は頭がこんがらがりやすいです。さらに、"数え年"なる聞きなれない言葉も出てきました。

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

年齢の差分年が増えていっているだけですね。**"数え年"は起点を１歳とするかどうか**で、**生年月日は年の部分だけが重要**であることが分かりました。
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

1. "生年月日"から"年"を数値として抽出する＝`生年`とする
2. 性別によって加算するデータを切り替える＝`厄年齢リスト`とする
3. `厄年齢リスト`を繰り返し処理する＝一つのデータは`厄年齢`とする
   - `生年`＋`厄年齢`－１を出力する

変数名まで決められました。また、表を使って考えたことで、これをそのまま出力結果にするアイディアも生まれました。

あとはアナグラムのときと同様に、プログラミング言語に翻訳していきます。
言語仕様が分からないところは検索していきます。

/// admonition | サンプルコード

```java title="CalcYakudosiApp.java" hl_lines="7-28"

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String 生年月日 = args[0];
    String 性別 = args[1];

    int 生年 = Integer.valueOf(生年月日.substring(0, 4));

    List<Integer> 厄年齢リスト = find厄年齢リスト(性別);

    for (Integer 厄年齢 : 厄年齢リスト) {
      String 年齢 = String.format("%2s", String.valueOf(厄年齢));
      int 厄年 = 生年 + 厄年齢 - 1;
      System.out.println(年齢 + " 歳 : " + 厄年 + " 年");
    }
  }

  public static List<Integer> find厄年齢リスト(String 性別) {
    if (性別.equals("男")) {
      return List.of(4, 25, 42, 61); // 男性
    } else {
      return List.of(19, 33, 37, 61); // 女性
    }
  }
```

次のループ処理を共通化するために厄年齢リストを決める処理を関数化しています。見た目を整えていますね。

```java linenums="12"
    List<Integer> 厄年齢リスト = find厄年齢リスト(性別);
```

`String#format`で年齢をスペースで埋めて、"4 歳"を表示するときにずれないようにしています。見た目を整えていますね。

```java linenums="15"
      String 年齢 = String.format("%2s", String.valueOf(厄年齢));
```

///

/// admonition | 🖨️ 出力結果

```bash title="Windows Terminal"
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

/// admonition | 生年月日に４桁未満の文字を入力すると、エラーが発生してしまいます。
    type: failure

```bash title="Windows Terminal"
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "333" "男"       
Exception in thread "main" java.lang.StringIndexOutOfBoundsException: begin 0, end 4, length 3
        at java.base/java.lang.String.checkBoundsBeginEnd(String.java:4601)
        at java.base/java.lang.String.substring(String.java:2704)
        at playground.todo.CalcYakudosiApp.main(CalcYakudosiApp.java:16)
```

///

/// admonition | 生年月日に日付以外の文字を入力しても、エラーになりません。
    type: warning

```bash title="Windows Terminal"
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-TE-ST" "男"
 4 歳 : 2002 年
25 歳 : 2023 年
42 歳 : 2040 年
61 歳 : 2059 年
```

///

/// admonition | 性別で男の子を自称すると、女性扱いになります。
    type: warning

```bash title="Windows Terminal"
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "男の子"
19 歳 : 2017 年
33 歳 : 2031 年
37 歳 : 2035 年
61 歳 : 2059 年
```

///

これでは、"意図通りであること"を満たすことができません。
**プログラムは、正しい入力をしないと、意図通りに動かない**のです。

入力できるパターンが増えても、意図通りに動くようにしてあげる必要があります。
意図通りに動かすためには、

- ①どんな入力がありうるか？を列挙すること
- ②意図通りでない入力値をどう打ち返してあげるのが適切か？

を考えることが重要です。

入力できるのは２つで、"生年月日"と"性別"です。
まず、簡単な"性別"の入力パターンから考えます。

## 💡 "性別"の入力パターンを考える

期待していた入力値は"男""女"です。[^1]
"男性""女性"や"male""female"の入力も考えられますね。
しかし、システムの都合上、"男""女"以外だったら入力しなおしてもらいましょう。[^2]

[^1]: "無回答"や"その他"を考慮できた人は素晴らしいです。が、この問題の本意ではないので省きます。
[^2]: "男性""女性"を期待する入力値としてもOKです。

/// admonition | サンプルコード

```java title="CalcYakudosiApp.java" hl_lines="8 12 29-37"
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    String 生年月日 = args[0];
    Sex 性別 = 性別をチェックしてEnumに変換(args[1]);

    int 生年 = Integer.valueOf(生年月日.substring(0, 4));

    List<Integer> 厄年齢リスト = find厄年齢リスト(性別);

    for (Integer 厄年齢 : 厄年齢リスト) {
      String 年齢 = String.format("%2s", String.valueOf(厄年齢));
      int 厄年 = 生年 + 厄年齢 - 1;
      System.out.println(年齢 + " 歳 : " + 厄年 + " 年");
    }
  }

  public static List<Integer> find厄年齢リスト(Sex 性別) {
    if (性別 == 性別.男) {
      return List.of(4, 25, 42, 61); // 男性
    } else {
      return List.of(19, 33, 37, 61); // 女性
    }
  }

  enum Sex {
    男, 女,
  }

  public static Sex 性別をチェックしてEnumに変換(String input) {
    return Arrays.stream(Sex.values())
        .filter((sex) -> sex.name().equals(input))
        .findFirst().orElseThrow(); // NoSuchElementException
  }
```

- [列挙型(Enum)に文字列や数値を利用する備忘録](https://qiita.com/hkusu/items/0996735553580bfabbdb)
- [Java Stream APIをいまさら入門](https://qiita.com/takumi-n/items/369dd3fcb9ccb8fcfa44)
- [Javaの例外の話(チェック例外と非チェック例外)](https://qiita.com/omix222/items/eb3bcdd2fd98794d8a35)

/// admonition | 性別に"男""女"以外を入力するとエラーになる🎉

```bash title="Windows Terminal"
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "男の子"
Exception in thread "main" java.util.NoSuchElementException: No value present
```

///

///

期待通りにエラーになってくれました。

ただ、このままではエラーになっただけで、中のつくりを知らない人はどう入力すればいいかわかりません。
そこで、**エラーメッセージ**を表示してあげましょう。

エラーメッセージについては["ユーザーに優しいエラーメッセージ"をデザインする方法 | アドビ UX 道場 ](https://blog.adobe.com/jp/publish/2022/07/25/cc-web-error-message-design-ux)にまとまっています。エラーメッセージを考えるうえで最も重要なのは、次のポイントです。

> **ユーザーに解決策を提示する**

エラーメッセージを表示するのは、ユーザに正しい操作や入力を行ってほしいからです。
"こうすればいいんだよ"と解決策を提示してあげましょう。

/// details | エラーメッセージを考えてみよう
    type: example

- ❌ `入力した値に誤りがあります。`
  - 😭 それはわかっているよ。`NoSuchElementException`と表示されるのと同レベル
- ❌ `男の子 は入力できません。`
  - 🥺 だからどう入力すればいいの？
- ✅ `性別には"男"か"女"と入力してください。`
  - 😀 "男の子"じゃだめで、"男"って入力すればいいんだね！

///

/// admonition | サンプルコード

```java title="CalcYakudosiApp.java" hl_lines="1 14-16"
    try {
      String 生年月日 = args[0];
      Sex 性別 = 性別をチェックしてEnumに変換(args[1]);

      int 生年 = Integer.valueOf(生年月日.substring(0, 4));

      List<Integer> 厄年齢リスト = find厄年齢リスト(性別);

      for (Integer 厄年齢 : 厄年齢リスト) {
        String 年齢 = String.format("%2s", String.valueOf(厄年齢));
        int 厄年 = 生年 + 厄年齢 - 1;
        System.out.println(年齢 + " 歳 : " + 厄年 + " 年");
      }
    } catch (NoSuchElementException e) {
      System.err.println("性別には\"男\"か\"女\"と入力してください。");
    }
```

/// admonition | メッセージも出ました🎉

```bash title="Windows Terminal"
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "男の子"
性別には"男"か"女"と入力してください。
```

///

///

## 💡 "生年月日"の入力パターンを考える

次に"生年月日"の入力パターンを考えます。

**まず、正しさを整理します。**分かることから考えるとそれ以外のことが見えてきます。

"正しい生年月日"とは何でしょうか？
難しく考える必要はありません。
"存在する日付であること"が必要です。

では、存在する日付を入力してもらえば十分でしょうか？

![Image title](https://2.bp.blogspot.com/-nMjuko9tXGc/Ut0BV0jJHkI/AAAAAAAAdW0/tRb8t3PEY28/s800/kangaeruhito.png){ width="120" }

常識の範囲内でも、日付を表現するいろんな形式があります。
問題文にもあった"**西暦**"、役所の書類で使われがちな"**和暦**"、少なくとも２つの表現方法は知っていますね。
出力は西暦で行うので、入力も西暦で行ってもらいましょう。

"正しい生年月日"といえるためには、

- ①存在する日付であること
- ②西暦表記で入力されていること

が必要である、と定義しましょう。

正しさが定義出来たら、**どんな入力がありうるか？**も考えられますね。

❌ 存在しない日付が入力される  
❌ 西暦以外の表記が入力される  

/// admonition | 実は②西暦表記が重要
    type: tip

例にあった"333"や"1999-TE-ST"も、何かの暦の表現としては生年月日を示すのに適切なのかもしれません。
ただ、西暦表記で入力するのが正しい、と定義してしまえば、
西暦以外にどんな表記があるか？を知る必要がなくなります💡

```bash title="Windows Terminal"
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "333" "男"       
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-TE-ST" "男"
```

///

では、西暦表記で存在する日付が入力されなかったらエラーメッセージを出しましょう。

<p style="font-size: 24px; text-align: center; font-weight: bold;">ちょっと待ってください！</p>

<figure markdown>
  <figcaption>まだあわてるような時間じゃない</figcaption>
  ![yakudosi-sendou.png](yakudosi-sendou.png){ width="480" }
</figure>

西暦表記ってなんですか？`1999年12月12日`も`1999/12/12`も`12.12.1999`も西暦表記です。
月と日を0埋めするパターン、0埋めしないパターンもあります（`1999/01/02` か `1999/1/2`）。
どの形式が正しいか、きちんと定めたらエラーメッセージを出しましょう。

/// admonition | 知っておこう ISO 8601

[ISO 8601は、日付と時刻の表記に関するISOの国際規格である。この規格の主眼は、日付と時刻の記述順序が国や文化によってまちまちである[注 1][1]ものを、大→小の順序（ビッグエンディアン big-endian）を貫徹して、日付・時刻の記述順序をただ一種類に標準化していることにある[2]。また、時刻表現は24時制だけに限定している。](https://ja.wikipedia.org/wiki/ISO_8601)

wikipediaが丁寧で分かりやすかった。

|          | 日付のみ   | 日付時刻               | 日付時刻＋タイムゾーン    |
| -------- | ---------- | ---------------------- | ------------------------- |
| 基本形式 | 20170717   | 20170717T170717.77     | 20230512T183614+0900      |
| 拡張形式 | 2017-07-17 | 2017-07-17T17:07:17.77 | 2023-05-12T18:36:14+09:00 |

システム作りの救世主です。パターンが無限にありうる日付表記の標準規格を定義してくれています。

///

/// admonition | サンプルコード

```java title="CalcYakudosiApp.java" hl_lines="8 18-19 25-28"
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に二つの文字列を入力してください。");
      return;
    }

    try {
      int 生年 = 生年月日をチェックして生年に変換(args[0]);
      Sex 性別 = 性別をチェックしてEnumに変換(args[1]);

      List<Integer> 厄年齢リスト = find厄年齢リスト(性別);

      for (Integer 厄年齢 : 厄年齢リスト) {
        String 年齢 = String.format("%2s", String.valueOf(厄年齢));
        int 厄年 = 生年 + 厄年齢 - 1;
        System.out.println(年齢 + " 歳 : " + 厄年 + " 年");
      }
    } catch (DateTimeParseException e) {
      System.err.println("生年月日はyyyy-MM-dd形式で入力してください。(例: 2017-07-17)");
    } catch (NoSuchElementException e) {
      System.err.println("性別には\"男\"か\"女\"と入力してください。");
    }
  }

  public static int 生年月日をチェックして生年に変換(String input) {
    // https://docs.oracle.com/javase/jp/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE
    return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE).getYear(); // yyyy-MM-dd
  }
```

///

もう少しです！動きを確認して完成です。

/// admonition | メッセージも出ました🎉

```bash title="Windows Terminal"
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "男" # (生年月日) 正しい入力
 4 歳 : 2002 年
25 歳 : 2023 年
42 歳 : 2040 年
61 歳 : 2059 年
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-02-29" "男" # (生年月日) 存在しない日付
生年月日はyyyy-MM-dd形式で入力してください。(例: 2017-07-17)
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-2-28" "男" # (生年月日) 形式誤り
生年月日はyyyy-MM-dd形式で入力してください。(例: 2017-07-17)
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "女" # (性別) 正しい入力
19 歳 : 2017 年
33 歳 : 2031 年
37 歳 : 2035 年
61 歳 : 2059 年
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "女子" # (性別) 選択肢にない入力
性別には"男"か"女"と入力してください。
```

///

<p style="font-size: 24px; text-align: center; font-weight: bold;">完成！🎉</p>

/// admonition | 正常系 と 準正常系 と 異常系
    type: tip
動きを確認することをシステム開発の世界では"試験"と言います。

試験の世界では、システムの目的の通りに動くことの確認を**正常系テスト**（厄年を出力するパターン）、  
目的通りに動くために必要な条件がそろっていないときでも適切に動くことの確認を**準正常系テスト**と言います。（エラーメッセージを出すパターン）  
今回扱っていませんが、動くわけないじゃんという状態でもパニックにならないことの確認を**異常系テスト**と言います。（パソコンが爆発したとか）
///
