# 厄年計算

## ❔ 問題 (目標⏰: 30分)

※ここで取り扱う日付はすべて西暦とする。  
「生年月日」と「性別」の二つの入力値から、厄年を出力するプログラムを作ること。

- 出力するのは本厄の年のみとすること。本厄は男性が数え年で4歳、25歳、42歳、61歳、女性が19歳、33歳、37歳、61歳のときとする。
  - ※諸説あり。
- 数え年は、生まれた時点で1歳とし、1月1日を迎えるたびに1歳ずつ加えていくこととする。
  - ※諸説あり。

### 🖨️ 出力結果

WEB上の厄年計算サイトを参考にしましょう。

### 🔰 ベースコード

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

### ▶️ 実行方法

```bash
❯ cd ~/my-todo/todo-app
❯ java src/main/java/playground/todo/CalcYakudosiApp.java "a" "b"
a
b
❯ java src/main/java/playground/todo/CalcYakudosiApp.java
引数に２つの文字を入力してください。
```
