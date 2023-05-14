<script setup lang="ts">
import axios from "axios";
import { reactive } from "vue";
import { useRouter } from "vue-router";

const todo = reactive({
  yarukoto: "",
});

const router = useRouter();
async function insert() {
  const res = await axios.post("/api/todo", {
    yarukoto: todo.yarukoto,
    datetime: new Date(),
  });
  console.log(res);
  console.log(res.data);

  router.push("/");
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
