# 厄年計算の解説

## 🙋 解説

### 💡 ゴールまでの筋道を立ててコードに書いて動きを確認して見た目を整える

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

/// admonition | 🚫 サンプルコード

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

/// admonition | 🖨️ 出力結果

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

/// admonition | 存在しない日付を入力すると、エラーが発生してしまいます。

```bash
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "1999-02-29" "男"
Exception in thread "main" java.time.format.DateTimeParseException: Text '1999-02-29' could not be parsed: Invalid date 'February 29' as '1999' is not a leap year
        at java.base/java.time.format.DateTimeFormatter.createError(DateTimeFormatter.java:2023)
        at java.base/java.time.format.DateTimeFormatter.parse(DateTimeFormatter.java:1958)
        at java.base/java.time.LocalDate.parse(LocalDate.java:430)
        at playground.todo.CalcYakudosiApp.main(CalcYakudosiApp.java:19)
```

///

/// admonition | 男の子を自称すると、女性扱いになります。

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

### 💡 入力パターンを考える

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
