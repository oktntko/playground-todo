package playground.todo;

import java.time.LocalDateTime;

public class Todo {

  public Todo() {
  }

  public Todo(Integer id, String yarukoto, LocalDateTime datetime) {
    this.id = id;
    this.yarukoto = yarukoto;
    this.datetime = datetime;
  }

  public Integer id;
  public String yarukoto;
  public LocalDateTime datetime;
}
