package playground.todo;

import java.io.IOException;
import java.util.List;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.CheckboxResult;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.CheckboxPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.ConfirmPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.InputValueBuilder;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;

public class MyPrompt {

  public static void showDemo() throws IOException {

    var input = MyPrompt.input("文字列の入力を受け付ける. >", "");
    System.out.println("入力された文字列は[" + input + "]です。");

    var select = MyPrompt.select("ひとつだけ選択できる. 上下で移動、Enterで決定.", List.of("a", "b", "c"));
    System.out.println("選択されたListのindexは[" + select + "]です。");

    var selectMultiple = MyPrompt.selectMultiple("複数選択できる. 上下で移動、Spaceで選択、Enterで決定.",
        List.of("α", "β", "γ"));
    System.out.println("選択されたListのindexは" + selectMultiple + "です。");

    var confirm = MyPrompt.confirm("確認する. (YESかNOか)", ConfirmChoice.ConfirmationValue.NO);
    System.out.println("確認結果は[" + confirm + "]です。");
  }

  public static String input(String message) throws IOException {
    return input(message, "");
  }

  public static String input(String message, String defaultValue) throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt();
    InputValueBuilder builder = prompt
        .getPromptBuilder()
        .createInputPrompt()
        .name(name)
        .message(message);

    if (defaultValue != null && !defaultValue.isEmpty()) {
      builder.defaultValue(defaultValue);
    }

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (InputResult) map.get(name);

    var input = result.getInput();
    return input == null ? "" : input;
  }

  public static int select(String message, List<String> options) throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt();
    ListPromptBuilder builder = prompt
        .getPromptBuilder()
        .createListPrompt()
        .name(name)
        .message(message);

    for (int i = 0; i < options.size(); i++) {
      var option = options.get(i);
      builder.newItem(String.valueOf(i)).text(option).add();
    }

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (ListResult) map.get(name);

    return Integer.parseInt(result.getSelectedId());
  }

  public static List<Integer> selectMultiple(String message, List<String> options)
      throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt();
    CheckboxPromptBuilder builder = prompt
        .getPromptBuilder()
        .createCheckboxPrompt()
        .name(name)
        .message(message);

    for (int i = 0; i < options.size(); i++) {
      var option = options.get(i);
      builder.newItem(String.valueOf(i)).text(option).add();
    }

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (CheckboxResult) map.get(name);

    return result.getSelectedIds().stream().map(Integer::parseInt).toList();
  }

  public static ConfirmChoice.ConfirmationValue confirm(
      String message, ConfirmChoice.ConfirmationValue defaultValue)
      throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt();
    ConfirmPromptBuilder builder = prompt
        .getPromptBuilder()
        .createConfirmPromp()
        .name(name)
        .message(message)
        .defaultValue(defaultValue);

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (ConfirmResult) map.get(name);

    return result.getConfirmed();
  }
}
