# 対話型アプリを試そう

対話型(interactive)アプリとは、１回の入力・１回の出力で終わらないアプリです。
難しくありません。一般的なアプリです。

地図アプリを開き、条件を入力して、結果が表示された後、アプリは終了しますか？
終了するどころか、表示された結果が次の入力のサジェストにつながっていたりします。
当然、終了せずに別の条件を入力しなおせるようになっていますね。

では、理解度の確認で作成した厄年計算アプリはどうだったでしょうか？
入力しなおすときに`java`コマンドでアプリを起動する必要がありました。

ここでは、厄年計算アプリを対話型にします。

/// admonition | 🔰 サンプルコード

これはサンプルです。自分で作った厄年計算アプリを改造しましょう。

```java title="CalcYakudosiApp.java"
package playground.todo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class CalcYakudosiApp {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に２つの文字列を入力してください。");
      return;
    }

    try {
      int 生年 = 生年月日をチェックして生年に変換(args[0]);
      Sex 性別 = 性別をチェックしてEnumに変換(args[1]);

      List<Integer> 厄年齢List = find厄年齢List(性別);

      for (Integer 厄年齢 : 厄年齢List) {
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

  public static List<Integer> find厄年齢List(Sex 性別) {
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
}
```

///

## 前提条件の確認

対話型アプリを実行は、VSCode の Run から実行するようにしてください。
コマンドラインからの実行だとうまくいきません。
VSCode の Run から実行するとその結果が残るので、java コマンドに置き換えて実行することもできる。

VSCode で Java の拡張機能とか諸々がうまく機能しないので、WSL（Windows Terminal）のコマンドから起動してください。
Windows のメニュー・アプリから起動するのはNG。
つまり、`code`コマンドから起動する。

## 完成系のイメージ

TODO: 動画

厄年計算アプリを対話型(interactive)アプリにするための条件を確認しましょう。

> 対話型(interactive)アプリとは、１回の入力・１回の出力で終わらないアプリ

でしたね。つまり、何度でも入力できればいいのです。

## 何度でも入力できるようにする

何度でも入力できるようにするにはどうすればいいでしょうか？  

簡単です。ループするだけです。正確には無限ループです。

/// admonition | 🔰 サンプルコード

```java title="CalcYakudosiApp.java" linenums="12" hl_lines="7 24"
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("引数に２つの文字列を入力してください。");
      return;
    }

    for (;;) {
      try {
        int 生年 = 生年月日をチェックして生年に変換(args[0]);
        Sex 性別 = 性別をチェックしてEnumに変換(args[1]);

        List<Integer> 厄年齢List = find厄年齢List(性別);

        for (Integer 厄年齢 : 厄年齢List) {
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
  }
```

///

ただ、このままだと、

①VSCode の Run から実行すると実行時引数を入力できないので、引数のエラーメッセージ`引数に２つの文字列を入力してください。`が表示されて終わり

②コマンドライン`java src/main/java/playground/todo/CalcYakudosiApp.java "1999-12-12" "男"`から実行すると、無限に同じ結果を表示し続けて終わらない

/// admonition | 無限ループにはまったら++ctrl+c++で中断させましょう
    type: tip
///

原因は何でしょうか？

入力値が実行時引数だからですね。
入力値が実行時に決まってしまいます。そして繰り返し行っても変わることはありません。

つまり、１回の入力・１回の出力で終わらないようにするためには、
入力の方法を変える必要がありますね。

## アプリ内で入力を行おう

現在、実行時の引数を取得している個所を変更しましょう。

/// admonition

```java title="CalcYakudosiApp.java" linenums="19"
      int 生年 = 生年月日をチェックして生年に変換(args[0]);
      Sex 性別 = 性別をチェックしてEnumに変換(args[1]);
```

///

どうやって入力すればいいでしょうか？  

『java コンソール 入力』で調べると、`InputStreamReader`クラスや`Scanner`クラスの利用で実現する方法が紹介されています。
ただ、使いづらく記載が冗長になります。
こういうときはライブラリの力を借ります。

/// admonition | ライブラリ と OSS と パッケージマネージャー
[consoleui](https://github.com/awegmann/consoleui)

・・・
///

`consoleui`の機能のうち利用しやすいものを抜粋した簡単にしたものが`MyPrompt.java`です。
このうち、`MyPrompt#input`メソッドが自由な入力を行えます。
実行時引数のかわりに使えそうですね。やってみましょう。

/// admonition

```java title="CalcYakudosiApp.java" linenums="19" hl_lines="1 2 6-9"
  public static void main(String[] args) throws IOException {
    AnsiConsole.systemInstall();

    for (;;) {
      try {
        String 生年月日 = MyPrompt.input("生年月日を入力してください。(例: 2017-07-17)");
        int 生年 = 生年月日をチェックして生年に変換(生年月日);
        String str性別 = MyPrompt.input("性別を入力してください。(男/女)");
        Sex 性別 = 性別をチェックしてEnumに変換(str性別);

        List<Integer> 厄年齢List = find厄年齢List(性別);

        for (Integer 厄年齢 : 厄年齢List) {
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
  }
```

- `IOException`はハンドリングしないので`throws IOException`宣言しておきます。
- `AnsiConsole#systemInstall`は`consoleui`を使うために実行する必要があります。

```java title="CalcYakudosiApp.java" linenums="19"
  public static void main(String[] args) throws IOException {
    AnsiConsole.systemInstall();
```

///

/// admonition | ライブラリのラッパー
    type: tip
[ワイ「なに！？ライブラリをラップするやと！？」](https://qiita.com/Yametaro/items/111ce26637d6a3c2e763)
///

```bash title="🖨️ 出力結果"
? 生年月日を入力してください。(例: 2017-07-17) 2017-07-17
? 性別を入力してください。(男/女) 男
 4 歳 : 2020 年
25 歳 : 2041 年
42 歳 : 2058 年
61 歳 : 2077 年
? 生年月日を入力してください。(例: 2017-07-17) 2018-07-17
? 性別を入力してください。(男/女) 女
19 歳 : 2036 年
33 歳 : 2050 年
37 歳 : 2054 年
61 歳 : 2078 年
? 生年月日を入力してください。(例: 2017-07-17) 2018-7a-17
生年月日はyyyy-MM-dd形式で入力してください。(例: 2017-07-17)
? 生年月日を入力してください。(例: 2017-07-17) 2018-07-17
? 性別を入力してください。(男/女) 犬
性別には"男"か"女"と入力してください。
? 生年月日を入力してください。(例: 2017-07-17)
```

なんとも `interactive`な感じになりました。

## 💡 見た目を整える

対話型にしてみると、

- 唐突に始まる感じ
- ループの切れ目が分かりにくい感じ

が気になりました。見た目を整えましょう。

/// admonition

```java title="CalcYakudosiApp.java" linenums="22" hl_lines="2 12 20"
    for (;;) {
      System.out.println("厄年になる年齢と西暦を出力します。");

      try {
        String 生年月日 = MyPrompt.input("生年月日を入力してください。(例: 2017-07-17)");
        int 生年 = 生年月日をチェックして生年に変換(生年月日);
        String str性別 = MyPrompt.input("性別を入力してください。(男/女)");
        Sex 性別 = 性別をチェックしてEnumに変換(str性別);

        List<Integer> 厄年齢List = find厄年齢List(性別);

        System.out.println("あなたの厄年は...");

        for (Integer 厄年齢 : 厄年齢List) {
          String 年齢 = String.format("%2s", String.valueOf(厄年齢));
          int 厄年 = 生年 + 厄年齢 - 1;
          System.out.println(年齢 + " 歳 : " + 厄年 + " 年");
        }

        System.out.println("");

      } catch (DateTimeParseException e) {
        System.err.println("生年月日はyyyy-MM-dd形式で入力してください。(例: 2017-07-17)");
      } catch (NoSuchElementException e) {
        System.err.println("性別には\"男\"か\"女\"と入力してください。");
      }
    }
```

///

<p style="font-size: 24px; text-align: center; font-weight: bold;">完成！🎉</p>

```bash title="🖨️ 出力結果"
厄年になる年齢と西暦を出力します。
? 生年月日を入力してください。(例: 2017-07-17) 2017-07-17
? 性別を入力してください。(男/女) 男
あなたの厄年は...
 4 歳 : 2020 年
25 歳 : 2041 年
42 歳 : 2058 年
61 歳 : 2077 年

厄年になる年齢と西暦を出力します。
? 生年月日を入力してください。(例: 2017-07-17) 2018-07-17
? 性別を入力してください。(男/女) 女
あなたの厄年は...
19 歳 : 2036 年
33 歳 : 2050 年
37 歳 : 2054 年
61 歳 : 2078 年
```

/// admonition | さらに見た目を整える場合

- 結果を出力した後に、続けるか？やめるか？の選択肢が出てもいいですね。
- 「あなたの厄年は」を出力した後に、"タメ"があってもいいですね。

///

## ToDoアプリを作りはじめる前に

`MyPrompt#showDemo` に使い方をまとめています。
下記のように呼び出して試してみてください。

```java
    AnsiConsole.systemInstall();

    MyPrompt.showDemo();
```
