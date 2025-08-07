<template>
  <!-- Botão fixo do carrinho -->
  <button class="botao-carrinho" @click="mostrarCarrinho = true">
    🛒 Carrinho ({{ totalItens }})
  </button>

  <!-- Popup do carrinho -->
  <CarrinhoPopup
    v-if="mostrarCarrinho"
    ref="carrinhoPopupRef"
    :itens="itensCarrinho"
    @fechar="mostrarCarrinho = false"
    
  />

  <router-view />
</template>

<script setup>
import { ref, computed } from 'vue';
import CarrinhoPopup from './components/CarrinhoPopup.vue';

const itensCarrinho = ref([]);
const mostrarCarrinho = ref(false);
const carrinhoPopupRef = ref([]);


const totalItens = computed(() => {
  return itensCarrinho.value.reduce((total, item) => total + item.quantidade, 0);
});

function adicionarAoCarrinho(produto) {
  // Adiciona diretamente ao array (solução mais simples)
  const itemExistente = itensCarrinho.value.find(item => item.id === produto.id);
  
  if (itemExistente) {
    itemExistente.quantidade++;
  } else {
    itensCarrinho.value.push({
      id: produto.id,
      nome: produto.nome,
      preco: produto.preco,
      quantidade: 1
    });
  }
  
  // Feedback visual
  const button = event.target;
  button.textContent = '✔ Adicionado';
  setTimeout(() => {
    button.textContent = 'Adicionar ao Carrinho';
  }, 1000);
}

function atualizarItens(novosItens) {
  itensCarrinho.value = novosItens;
}
</script>

<style scoped>
.botao-carrinho {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000;
  padding: 10px 15px;
  background: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>