<template>
  <div class="produtos-container">
    <h1>Lista de Produtos</h1>
    <div class="produtos-grid">
      <div v-for="produto in produtos" :key="produto.id" class="card">
        <h2>{{ produto.nome }}</h2>
        <p><strong>Descrição:</strong> {{ produto.descricao }}</p>
        <p><strong>Preço:</strong> R$ {{ produto.preco.toFixed(2) }}</p>
        
        <div class="botoes">
          <button @click="comprar(produto)">Comprar</button>
          <button @click="adicionarAoCarrinho(produto)">Adicionar ao Carrinho</button>

        </div>
      </div>
    </div>
  </div>

  <ComprarModal
    v-if="exibirModal"
    :show="exibirModal"
    :produto="produtoSelecionado"
    @fechar="fecharModal"
    @comprar="confirmarCompra"
  />

</template>

<script setup>
import { ref, onMounted } from 'vue'
import ComprarModal from './ComprarModal.vue'
import CarrinhoPopup from './CarrinhoPopup.vue'

const carrinhoPopupRef = ref(null)
const produtoSelecionado = ref(null)
const exibirModal = ref(false)

function comprar(produto) {
  produtoSelecionado.value = produto
  exibirModal.value = true
}

function fecharModal() {
  exibirModal.value = false
}

function confirmarCompra({ produto, quantidade }) {
  alert(`Compra confirmada de ${quantidade}x ${produto.nome} (R$ ${(produto.preco * quantidade).toFixed(2)})`)
}

const produtos = ref([])

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/produtos')
    if (!response.ok) throw new Error('Erro ao carregar produtos')
    produtos.value = await response.json()
  } catch (error) {
    console.error('Erro:', error)
  }
})

function adicionarAoCarrinho(produto) {
  if (!carrinhoPopupRef.value) return;
  
  // Chama a função exposta pelo CarrinhoPopup
  carrinhoPopupRef.value.adicionarItem(produto);
  
  alert(`${produto.nome} adicionado ao carrinho!`);
}
</script>

<style scoped>
.produtos-container {
  margin-top: 100px;
  padding: 2rem;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f0f2f5;
}

.produtos-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: center;
}

.card {
  width: 100%;
  max-width: 320px;
  height: 380px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.15);
}

.card h2 {
  font-size: 1.4rem;
  margin-bottom: 0.5rem;
  color: #333;
}

.card p {
  font-size: 1rem;
  color: #555;
  margin: 0.4rem 0;
  line-height: 1.4;
}

.botoes {
  margin-top: 1rem;
  display: flex;
  justify-content: space-between;
  gap: 0.5rem;
}

.botoes button {
  flex: 1;
  padding: 0.6rem;
  border: none;
  border-radius: 8px;
  background-color: #007bff;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.botoes button:hover {
  background-color: #0056b3;
}

</style>
