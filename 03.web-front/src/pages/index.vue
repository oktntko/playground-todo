<script setup lang="ts">
import axios from "axios";
import { reactive } from "vue";
import { onMounted } from "vue";

type Todo = {
  id: number;
  yarukoto: string;
  datetime: Date;
};

const data = reactive<{ todoList: Todo[] }>({
  todoList: [],
});

onMounted(async () => {
  const res = await axios.get<Todo[]>("/api/todo");

  console.log(res);
  console.log(res.data);
  data.todoList = res.data;
});

async function handleDone(id: number) {
  if (window.confirm("完了しますか？")) {
    await axios.delete(`/api/todo/${id}`);
    window.alert("完了しました！");
    data.todoList = data.todoList.filter((todo) => todo.id != id);
  }
}
</script>

<template>
  <div>Hello World!</div>
  <div v-for="todo of data.todoList" :key="todo.id">
    |
    <span>{{ todo.id }}</span>
    |
    <span>{{ todo.yarukoto }}</span>
    |
    <span>{{ todo.datetime }}</span>
    |
    <RouterLink :to="`/edit/${todo.id}`">編集</RouterLink>
    |
    <button type="button" @click="handleDone(todo.id)">完了</button>
  </div>
  <RouterLink to="/add"><button type="button">追加</button></RouterLink>
</template>
