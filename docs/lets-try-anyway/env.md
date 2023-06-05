# 🧑‍💻 開発環境を作ろう

## WSL <small>(Linux 用 Windows サブシステム)</small> をインストールする

[WSL を使用して Windows に Linux をインストールする](https://learn.microsoft.com/ja-jp/windows/wsl/install)

1\. コマンドを実行する

```sh title="PowerShell(管理者モード)"
wsl --install
```

/// admonition

```sh title="🖨️ 出力結果"
 インストール中: 仮想マシン プラットフォーム
 仮想マシン プラットフォーム はインストールされました。
 インストール中: Linux 用 Windows サブシステム
 Linux 用 Windows サブシステム  はインストールされました。
 インストール中: Linux 用 Windows サブシステム
 Linux 用 Windows サブシステム  はインストールされました。
 インストール中: Ubuntu
 Ubuntu はインストールされました。
 要求された操作は正常に終了しました。変更を有効にするには、システムを再起動する必要があります。
```

///

2\. メッセージが出力されたら 再起動する

3\. **再起動したら自動で Ubuntu のウィンドウが開くので、しばらく操作をしない**

4\. Ubuntu のウィンドウが開いたら自動で処理が始まるのでしばらく待つ

/// admonition

```sh title="Ubuntu"
 Ubuntu は既にインストールされています。
 Ubuntu を起動しています...
 Installing, this may take a few minutes...
```

///

5\. Linux のユーザ名を聞かれるので **Windows のユーザ名と同じ名前を入力する**

```sh title="Ubuntu"
 Please create a default UNIX user account. The username does not need to match your Windows username.
 For more information visit: https://aka.ms/wslusers
 Enter new UNIX username: # **ここでWindows のユーザ名と同じ名前を入力してください**
 New password: # パスワードは簡単なものでいい
 Retype new password:
 passwd: password updated successfully
 Installation successful!
```

6\. "Ubuntu"のウィンドウを閉じて"Windows Terminal"を開く

7\. ++ctrl++ + `,(カンマ)`で設定を開く

8\. スタートアップ＞既定のプロファイルを"Ubuntu"にする

/// admonition | Windows のユーザ名の確認方法

```sh title="PowerShell"
echo $Env:USERNAME
```

```sh title="コマンド プロンプト"
echo %USERNAME%
```

///

### WSLって何？

[Linux 用 Windows サブシステムとは](https://learn.microsoft.com/ja-jp/windows/wsl/about)

簡単に書くと、`Windows` 上で `Linux` が動かせるよ、ということ。

### Linux って何？

OSの一種。Windows や Mac と同列の存在。

/// admonition | 正しくはないけどイメージだけつかみたい OS の歴史

```mermaid
flowchart LR
  Unix --> macOS

  MS-DOS --> Windows

  Linux --> id1[Ubuntu,Fedora,Arch Linux,Android,ChromeOS...]
```

| OS     | 生まれ | 作った人たち                       |
| ------ | ------ | ---------------------------------- |
| Unix   | 1969年 | ケン・トンプソン、デニス・リッチー |
| MS-DOS | 1981年 | Microsoft                          |
| Linux  | 1991年 | リーナス・トーバルズ               |

Linux が生まれてからたった 30年ちょっと。今から30年後、世界はどうなっているのだろうか？

* 最も古いOSは？ 1964年 IBM が発表した "OS/360"および"DOS/360"

///

### OS の上で OS が動くって何？

[WSL2の構成](https://qiita.com/na-777/items/7ead86b723c683346eba#wsl2%E3%81%AE%E6%A7%8B%E6%88%90)

深く理解する必要はありません。

大切なことは**どうつながっているか？**です。

- Windows から Linux を見るには？
  - どうやってつながっている？
    - **ネットワーク**
  - ネットワーク？
    - ファイルエクスプローラーのアドレスバーに `\\wsl$\` または `\\wsl.localhost\` を入力してみる
    - これが、 `Windows` <=> `Linux` をつなぐネットワーク
- Linux から Windows を見る
  - どうやってつながっている？
    - **マウント**
  - マウント？
    - Windows に USB を差し込むと USBの中身が見られるよね。
    - それと同じ。Linux のPCに Windows のUSBがぶっ刺さっている状態。
  - どうやってみる？
    - Linux からみた `/mnt/c/`ディレクトリが Windows の`C:\`ディレクトリ

### なんで Windows 上で Linux を動かすの？

1. 本番環境で、ウェブアプリが動く場所（＝ウェブサーバ）の OS が Linux だから
   - OS 差分は結構ある。本番環境で実行してみたら挙動が変わっちゃう、ということを防げる
2. 各種ツールを Windows に直にインストールすると、環境の変更が難しいから
   - Linux なら Docker をそのまま動かせる
     - 例えば案件Ａでは v1.0.0 を使う。だけど、案件Ｂでは v2.0.0 使うんだよね
       - 二つ一緒にインストールできない･･･
     - Docker なら、別バージョンでも共存できる
   - Linux なら パッケージ管理ツールも充実している
     - Docker と同様に環境汚染が防げる
3. Linux なら環境を作り直すのが楽
   - Windows をリセットして再設定する場合、ネットワークの設定、ユーザ作成、･･･すべて画面から操作する必要があるので再構築に時間がかかる
   - Linux ならコマンドを用意しておけば一発で再構築ができる（時間はかかるけど操作は少ない）

### Ubuntu って何？ Linux じゃないの？

Linux の子孫みたいなものだ。
子孫はいっぱいいる。

[Linuxディストリビューション](https://ja.wikipedia.org/wiki/Linux%E3%83%87%E3%82%A3%E3%82%B9%E3%83%88%E3%83%AA%E3%83%93%E3%83%A5%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3)

---

## <small>WSL がインストールできたら</small> Ubuntu に dotfiles を入れる

1\. 上から順番にコマンドを実行する

```bash title="Ubuntu"
sudo apt-get update && sudo apt-get upgrade
sudo apt-get install -y lsb-release build-essential curl file git procps curl git vim wget zsh
sudo apt-get install -y build-essential curl libbz2-dev libffi-dev liblzma-dev libncursesw5-dev libreadline-dev libsqlite3-dev libssl-dev libxml2-dev libxmlsec1-dev llvm make tk-dev wget xz-utils zlib1g-dev
curl -fsSL https://raw.githubusercontent.com/oktntko/dotfiles/main/install.zsh | zsh
# 最後までいくとたしかパスワードを聞かれるので、パスワードを入力する
```

2\. "Ubuntu" のウィンドウを閉じる

### dotfiles ってなに？

Windows と違い、Linux には隠しファイル・隠しフォルダという属性がない。
かわりに、`.(ドット)`で始まるファイルを隠しファイル・隠しフォルダとして扱う。

Windows における隠しファイル・隠しフォルダはアプリのデータや設定など、
Windows に保存せざるを得ないがユーザが直接見る必要のないデータだ。

ここでいう dotfiles はアプリの設定をまとめたもの。
アプリって何かというと、 Linux で使う開発ツール類。
私の開発ツールの設定を共有したのだ。

ただこの設定、私の趣味・趣向が多分に含まれるので、
自立したら私の設定をすべて消して、君だけのオリジナル dotfiles リポジトリを作ってほしい。

### ツールの使い方がわからない

[oktntko/dotfiles](https://github.com/oktntko/dotfiles)にまとめているけど、
詳細は各ツールのREADMEを読んでくれ。

---

## <small>dotfiles が入ったら</small> Ubuntu の systemd を有効化する

1. Ubuntu の設定ファイルを書き換える

```sh title="Windows Terminal(Ubuntu)"
sudo vim /etc/wsl.conf # エディタが起動する
```

2\. `i` キーを押す (入力モードに注意。半角英数で入力すること)

3\. 次のテキストを張り付ける

```
[boot]
systemd=true
```

4\. ++esc++キーを押す

5\. `:wq`と入力する（コピペではなく入力する）

6\. PowerShell かコマンドプロンプトから、WSL をシャットダウンする  
※Windows Terminal を開けば再起動します。

```sh title="PowerShell"
wsl --shutdown
```

---

## <small>systemd を有効化できたら</small> Ubuntu に Docker を入れる

1\. [Install using the apt repository](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository)の章で次の二つを実施する

* Set up the repository
* Install Docker Engine

2\. 管理者権限なしで操作を実行できるようにする

```sh title="Windows Terminal(Ubuntu)"
sudo usermod -aG docker $USER
```

3\. ネットワーク周りがちゃんと動くように変更する

```sh title="Windows Terminal(Ubuntu)"
sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
```

4\. PowerShell かコマンドプロンプトから、WSL をシャットダウンする  
※Windows Terminal を開けば再起動します。

```sh title="PowerShell"
wsl --shutdown
```

5\. Docker サービスを起動する

```sh title="Windows Terminal(Ubuntu)"
sudo systemctl enable docker
systemctl status docker
```

### Docker って何？

TODO

---

## <small>作成済みのリポジトリをクローンして</small> サンプルアプリを動かす

TODO
