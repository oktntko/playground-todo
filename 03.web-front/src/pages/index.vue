<script setup lang="ts">
import axios from "axios";
import { onMounted } from "vue";

type Todo = {
  id: number;
  yarukoto: string;
  datetime: Date;
};

onMounted(async () => {
  const res = await axios.get<Todo[]>("/api/todo");

  console.log(res);
  console.log(res.data);
});
</script>

<template>
  <div>Hello World!</div>
</template>
