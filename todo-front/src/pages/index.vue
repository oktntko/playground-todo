<script setup lang="ts">
import { ref } from "vue";
import { onMounted } from "vue";

const list = ref<string[]>([]);

onMounted(() => {
  list.value = ["Hello", "World", "!"];
});
</script>

<template>
  <div>{{ list.join(" ") }}</div>
</template>
