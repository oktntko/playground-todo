import { createApp } from "vue";
import App from "~/App.vue";
import "~/assets/main.css";
import router from "~/middleware/router";

const app = createApp(App);

app.use(router);

app.mount("#app");
