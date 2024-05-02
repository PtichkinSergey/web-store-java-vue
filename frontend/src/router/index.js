import { createRouter, createWebHistory } from 'vue-router'
import CatalogView from '@/views/CatalogView.vue'
import AuthView from '@/views/AuthView.vue'
import RegisterView from '@/views/RegisterView.vue'
import GoodView from '@/views/GoodView.vue'
import BasketView from '@/views/BasketView.vue'

const routes = [
  {
    path: '/',
    name: 'root',
    redirect: '/catalog'
  },
  {
    path: '/catalog',
    name: 'catalog',
    component: CatalogView,
    children: [
      {
        path: '/catalog/:id',
        name: 'category',
        component: CatalogView
      }
    ]
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
  },
  {
    path: '/basket',
    name: 'basket',
    component: BasketView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
