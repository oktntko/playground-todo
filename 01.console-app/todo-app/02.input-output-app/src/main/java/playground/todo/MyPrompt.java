package playground.todo;

import java.io.IOException;
import java.util.HashSet;

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

  public static String input(String message) throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt(); // #2
    InputValueBuilder builder = prompt
        .getPromptBuilder()
        .createInputPrompt()
        .name(name)
        .message(message);

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (InputResult) map.get(name);

    return result.getInput();
  }

  public static String select(String message, String... options) throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt();
    ListPromptBuilder builder = prompt
        .getPromptBuilder()
        .createListPrompt()
        .name(name)
        .message(message);

    for (var option : options) {
      builder.newItem().text(option).add();
    }

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (ListResult) map.get(name);

    return result.getSelectedId();
  }

  public static HashSet<String> selectMultiple(String message, String... options)
      throws IOException {
    final var name = "name";

    ConsolePrompt prompt = new ConsolePrompt();
    CheckboxPromptBuilder builder = prompt
        .getPromptBuilder()
        .createCheckboxPrompt()
        .name(name)
        .message(message);

    for (var option : options) {
      builder.newItem().text(option).add();
    }

    var map = prompt.prompt(builder.addPrompt().build());
    var result = (CheckboxResult) map.get(name);

    return result.getSelectedIds();
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
