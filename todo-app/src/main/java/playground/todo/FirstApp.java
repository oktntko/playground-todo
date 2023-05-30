package playground.todo;

import java.io.IOException;

import org.fusesource.jansi.AnsiConsole;

public class FirstApp {

  public static void main(String[] args) throws IOException {
    AnsiConsole.systemInstall();

    System.out.println("📝 Welcome Back, My To-Do!");

    for (;;) {
      MyPrompt.showDemo();
      System.out.println("\"Ctrl + C\"で停止してください。");
    }
  }
}
