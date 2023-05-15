<script setup lang="ts">
import axios from "axios";
import { onMounted } from "vue";
import { reactive } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const id = route.params.id;

onMounted(async () => {
  await get();
});

const todo = reactive({
  yarukoto: "",
  datetime: null,
});

async function get() {
  const res = await axios.get(`/api/todo/${id}`);
  todo.yarukoto = res.data.yarukoto;
  todo.datetime = res.data.datetime;
}

const router = useRouter();
async function update() {
  const res = await axios.put(`/api/todo/${id}`, {
    yarukoto: todo.yarukoto,
    datetime: new Date(),
  });
  console.log(res);
  console.log(res.data);

  router.push("/");
}
</script>

<template>
  <form @submit.prevent="update">
    <lable>やること</lable>
    <input v-model="todo.yarukoto" />
    <button type="submit">登録</button>
    {{ todo.yarukoto }}
  </form>
</template>
