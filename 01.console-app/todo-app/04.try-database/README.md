https://www.postgresql.jp/

https://www.postgresql.jp/document/14/html/

## 実行環境の構築

`❯ docker compose up --detach database`

- Docker 上で PostgreSQL が実行されます
- ユーザ `todo`（パスワードは`todo`）が作成されます
- データベース `todo` が作成されます

## PostgreSQL の起動・停止

- 起動  
  `❯ docker compose start database`
- 停止  
  `❯ docker compose stop database`

## クライアントツールのインストール

- A5:SQL Mk-2
  - https://a5m2.mmatsubara.com/
  - GUI(Graphical User Interface)
  - 主要な RDB 全てで利用できる
  - Windows 向け
  - インストールが簡単
  - エクセルファイルへの出力や CSV ファイルのインポートなど機能が豊富
- pgcli (DBCLI)
  - https://www.pgcli.com/
  - CUI(Character User Interface)
  - インストールに一癖ある

## PostgreSQL への接続・切断

### A5 を使う場合

- 接続（初回）
  - データベースの追加と削除 > 追加 > PostgreSQL (直接接続)
  - 基本タブ >
    - サーバー名: localhost
    - ポート番号: ${DB_PORT}
    - データベース名: todo
    - ユーザ ID: todo
    - パスワード: todo
    - ✅ パスワードを保存する
  - OK
- 接続（２回目以降）
  - 左側のデータベースから接続するデータベースをダブルクリックする
- 切断
  - 左側のデータベースから切断するデータベースを右クリック > データベースを閉じる

### pgcli を使う場合

- 接続
  - `❯ pgcli --host=localhost --port=${DB_PORT} --username=todo --password`
  - `Password for todo: ***todo***`
- 切断
  - `todo@localhost:todo> \q` または `Ctrl` + `D`
