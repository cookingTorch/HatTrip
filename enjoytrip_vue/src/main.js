import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { useUserStore } from './stores/user-store';
import 'bootstrap/dist/css/bootstrap.css' // Bootstrap CSS 파일 import
import 'bootstrap' // Bootstrap JavaScript 파일 import
import '@fortawesome/fontawesome-free/css/all.min.css' // Font Awesome CSS 파일 import

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())

const userStore = useUserStore()
app.use(router)

app.mount('#app')
