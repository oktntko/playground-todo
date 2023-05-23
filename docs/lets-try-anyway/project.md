# プロジェクトを作ろう

## プロジェクトのディレクトリを作ろう

```bash
❯ cd ~ # ホームディレクトリに移動する.
❯ mkdir my-todo # プロジェクトディレクトを作る.
❯ ls --long # 作ったディレクトリを確認する.
drwxr-xr-x - username xx XXX 00:00 my-todo
```

/// admonition | コマンド解説
ホームディレクトリは Windows でいう`C:\Users\%USERNAME%`にあたる.  
`cd` = `change directory`. ディレクトリを移動する. `~(チルダ)` = ホームディレクトリの別名.  
`mkdir` = `make directory`. ディレクトリを作成する.  
`ls` = `list segments`. ファイルやディレクトリの一覧を表示する.  
///

コマンドで実行した操作を画面から実行します。

- エクスプローラーのアドレスバーに `\\wsl.localhost\Ubuntu\home\%USERNAME%` を入力する.
- 右クリック＞新規作成＞フォルダを選ぶ.
- ファイル名で「my-todo」を入力する.
- 画面を確認する.

開発の現場ではコマンドの操作で行うことが多くあります。  
なぜでしょうか？  

- コマンドライン
  - 複雑な操作でもテキストだけで手順を書ける。
  - 操作の履歴が残るので、ミスがあったときに原因が分かりやすい。
- 画面からの操作
  - 複雑な操作になると画像なども駆使する必要がある。
  - 操作の履歴が残らないので、画面キャプチャを取りながら進める必要がある。キャプチャを取らないとミスった原因が追えない。

TODO: `画像入れる`

そのため、コマンドの操作に慣れる必要がある。  
とはいえ、エクスプローラー（画面）から操作することも多いので、  
`\\wsl.localhost\Ubuntu\home\%USERNAME%` はクイックアクセスに追加しておこう。

## プロジェクトのディレクトリの Git を初期化しよう

```bash
❯ pwd # 現在いるディレクトリを確認する
/home/username
❯ cd my-todo # 作成した`my-todo`ディレクトリに移動する
❯ git init
❯ git config --local user.name "あなたの名前" && git config --local user.email "あなたのメールアドレス"
❯ # git config --local user.name "oktntko" && git config --local user.email "oktntko@gmail.com"
❯ git commit --allow-empty --message="first commit"
```

## プロジェクトのソースコードを持ってこよう

1. GitHubの[playground-todo](https://github.com/oktntko/playground-todo) をブラウザで表示する.
2. `.(ドット)`を入力する.
3. `todo-app`を右クリックする.
4. Download ボタンをクリックする.
5. エクスプローラーのアドレスバーに `\\wsl.localhost\Ubuntu\home\%USERNAME%\my-todo` を入力する.
6. 「フォルダの選択」をクリックする.
7. 「サイトにファイルの読み取りを許可しますか？」＞ファイルを表示する.
8. 「「my-todo」に変更を保存しますか？」＞変更を保存.

/// admonition | ソースコードを持ってくる
通常、ソースコードは バージョン管理ツール（Gitなど）で Clone します。  
この方法はこのリポジトリ特有のやり方なので、重要ではないです。  
///

## 不要なファイルを削除しよう

```bash
❯ pwd # 現在いるディレクトリを確認する
/home/username/my-todo
❯ rm --recursive --force */**/.*Zone.Identifier # `no matches found` と出ても問題ない
❯ rm --recursive --force */**/*Zone.Identifier
❯ git add .
❯ git commit -m "Download todo-app"
```

/// admonition | Zone.Identifier
あとからあとから生まれる…なぜ？
///

## Hello World をしよう

```bash
❯ pwd # 現在いるディレクトリを確認する
/home/username/my-todo
❯ code todo-app
```

1. `src/main/java/playground/todo/FirstApp.java` を開く.
2. `public static void main(String[] args) {` の上に表示されている `Run | Debug` から、`Run` をクリックする.
  - `TERMINAL`タブに`Hello World!`が表示される

```bash
❯ pwd
/home/username/my-todo/todo-app
❯ java src/main/java/playground/todo/FirstApp.java
Hello World!
```
