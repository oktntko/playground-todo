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
  </div>
  <RouterLink to="/add"><button type="button">追加</button></RouterLink>
</template>
