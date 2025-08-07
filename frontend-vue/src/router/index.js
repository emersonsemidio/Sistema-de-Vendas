// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/components/Login.vue'
import UsuarioList from '@/components/UsuarioList.vue'
import ProdutosList from '@/components/ProdutosList.vue'
import CarrinhoPopup from '@/components/CarrinhoPopup.vue'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/usuarios',
    name: 'usuarios',
    component: UsuarioList
  },
  {
    path: '/produtos',
    name: 'produtos',
    component: ProdutosList
  },
  {
    path: '/carrinho',
    name: 'carrinho',
    component: CarrinhoPopup
  },

  // outras rotas aqui futuramente
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
