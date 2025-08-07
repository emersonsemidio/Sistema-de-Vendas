<template>
  <div class="login-container">
    <h1>Login</h1>

    <form @submit.prevent="handleLogin">
      <div>
        <label for="email">Email:</label>
        <input id="email" v-model="email" type="email" required />
      </div>

      <div>
        <label for="senha">Senha:</label>
        <input id="senha" v-model="senha" type="password" required />
      </div>

      <button type="submit">Entrar</button>

      <p v-if="erro" class="erro">{{ erro }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const email = ref('')
const senha = ref('')
const erro = ref('')

const router = useRouter()

const handleLogin = async () => {
  try {
    const response = await fetch('http://localhost:8080/usuarios/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: email.value, senha: senha.value })
    })

    if (!response.ok) {
      const body = await response.text()
      throw new Error(body || 'Falha no login')
    }

    const data = await response.json()
    console.log('Login realizado com sucesso:', data)
    erro.value = ''

    // Redireciona para a tela de lista de usuários
    router.push('/usuarios')

  } catch (e) {
    erro.value = e.message
  }
}
</script>


<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  margin: 10vh auto;
  padding: 2rem;
  background-color: #f8eded;
  border-radius: 12px;
  border: 1px solid #ddd;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 1.5rem;
  font-size: 1.8rem;
  color: #333;
  width: 100%;
}

form {
  width: 100%;
}

form div {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

label {
  margin-bottom: 0.4rem;
  color: #444;
  font-weight: 500;
}

input {
  padding: 0.6rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.3s;
}

input:focus {
  outline: none;
  border-color: #007BFF;
}

button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

.erro {
  color: red;
  margin-top: 1rem;
  text-align: center;
  font-weight: bold;
}
</style>

