package playground.todo;

import java.io.IOException;
import de.codeshelf.consoleui.elements.ConfirmChoice;

import org.fusesource.jansi.AnsiConsole;

public class App {

  public static void main(String[] args) throws InterruptedException, IOException {
    AnsiConsole.systemInstall();

    var input = MyPrompt.input("input");
    System.out.println(input);

    var select = MyPrompt.select("select", "a", "b", "c");
    System.out.println(select);

    var selectMultiple = MyPrompt.selectMultiple("selectMultiple", "1", "2", "3");
    System.out.println(selectMultiple);

    var confirm = MyPrompt.confirm("confirm", ConfirmChoice.ConfirmationValue.NO);
    System.out.println(confirm);
  }
}
