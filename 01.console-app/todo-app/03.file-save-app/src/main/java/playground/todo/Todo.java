package playground.todo;

import java.time.LocalDateTime;

public class Todo {

  public Todo() {
  }

  public Todo(String yarukoto, LocalDateTime datetime) {
    this.yarukoto = yarukoto;
    this.datetime = datetime;
  }

  public String yarukoto;
  public LocalDateTime datetime;
}
