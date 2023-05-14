<script setup lang="ts">
import axios from "axios";
import { reactive } from "vue";

const todo = reactive({
  yarukoto: "",
});

async function insert() {
  const res = await axios.post("/api/todo", {
    yarukoto: todo.yarukoto,
    datetime: new Date(),
  });
  console.log(res);
  console.log(res.data);
}
</script>

<template>
  <form @submit.prevent="insert">
    <lable>やること</lable>
    <input v-model="todo.yarukoto" />
    <button type="submit">登録</button>
    {{ todo.yarukoto }}
  </form>
</template>
