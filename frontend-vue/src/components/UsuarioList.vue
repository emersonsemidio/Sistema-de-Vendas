<template>
  <div>
    <h2>Lista de Usuários</h2>
    <table >
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Email</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="usuario in usuarios" :key="usuario.id">
          <td>{{ usuario.id }}</td>
          <td>{{ usuario.nome }}</td>
          <td>{{ usuario.email }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

const usuarios = ref([])

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/usuarios')
    console.log('Usuários recebidos:', response.data)
    usuarios.value = response.data
  } catch (error) {
    console.error('Erro ao buscar usuários:', error)
  }
})
</script>
