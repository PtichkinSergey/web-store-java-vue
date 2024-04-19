import { createRouter, createWebHistory } from 'vue-router'
import CatalogView from '@/views/CatalogView.vue'
import AuthView from '@/views/AuthView.vue'
import RegisterView from '@/views/RegisterView.vue'
import GoodView from '@/views/GoodView.vue'

const routes = [
  {
    path: '/',
    name: 'catalog',
    component: CatalogView
  },
  {
    path: '/auth',
    name: 'authentification',
    component: AuthView
  },
  {
    path: '/register',
    name: 'registration',
    component: RegisterView
  },
  {
    path: '/goods/:id',
    name: 'goods',
    component: GoodView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
