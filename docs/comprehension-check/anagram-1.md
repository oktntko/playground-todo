# アナグラム問題

## ❔ 問題 (目標⏰: 20分)

二つの文字列が同じ文字から構成されるかどうか判断し、同じなら`true`を違うなら`false`を出力すること。  
ただし、以下のルールを満たすこと。

- 半角スペースの有無の違いは無視すること。
- アルファベットの大文字小文字の違いは無視すること。

### 🖨️ 出力結果

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

### 🔰 ベースコード

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

### ▶️ 実行方法

```bash
❯ cd ~/my-todo/todo-app
❯ java src/main/java/playground/todo/AnagramApp.java "a" "b"
a
b
❯ java src/main/java/playground/todo/AnagramApp.java
引数に２つの文字を入力してください。
```
