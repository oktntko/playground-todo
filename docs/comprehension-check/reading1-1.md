# 📖 リーディング１ (目標⏰: 5分)

次のコードの問題点を指摘せよ。[^1]

[^1]: [北ソフト工房 ＞ Java 練習問題集 ＞ 練習問題 4 - 6](http://kitako.tokyo/lib/JavaExercise.aspx?id=4)を参考にプログラムを書いた。

コードの意図は次の通り。

- 引数は対戦成績で、0 が負け、1 が勝ちを意味する
- 勝ちの総数、負けの総数を表示したい

```java title="Reading1.java"
  static final int zero = 0;
  static final int one = 1;

  public static void COUNT_VICTORY_OR_DEFEAT(int[] VictoryOrDefeat) {
    int a = 0; // 勝ちの数
    int b = 0; // 負けの数

  // 引数の VictoryOrDefeat の数だけループする
  for (int i = 0; i < VictoryOrDefeat.length; i++) {
    if (VictoryOrDefeat[i] == zero) {
        a = a + 1; // 勝ちの数に１加算する
      } else if (VictoryOrDefeat[i] == one) {
        b = b + 1; // 負けの数に１加算する
    }
  }

    System.out.println("Victory=" + a + ", Defeat=" + b);
}
```
