# ❔ FizzBuzz問題 (目標⏰: 10分)

1 から 30 までの数を順番に出力すること。  
ただし、以下のルールを満たすこと。

- 数が 3 の倍数の時はFizzと出力すること
- 数が 5 の倍数の時はBuzzと出力すること
- 数が 3 の倍数かつ 5 の倍数の時はFizzBuzzと出力すること

## 🖨️ 出力結果

```bash title="Windows Terminal"
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

## 🔰 ベースコード

```java title="FizzBuzzApp.java"
package playground.todo;

public class FizzBuzzApp {

  public static void main(String[] args) {
    System.out.println("fizzbuzz");
  }
}
```

## ▶️ 実行方法

```bash title="Windows Terminal"
❯ cd ~/my-todo/todo-app
❯ java src/main/java/playground/todo/FizzBuzzApp.java
fizzbuzz
```
