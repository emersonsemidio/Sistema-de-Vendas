<template>
  <!-- Botão do carrinho -->
  <button class="botao-carrinho" @click="mostrarCarrinho = true">
    🛒 Carrinho ({{ totalItens }})
  </button>

  <!-- Popup do carrinho -->
  <CarrinhoPopup
    v-if="mostrarCarrinho"
    :itens="itensCarrinho"
    @fechar="mostrarCarrinho = false"
    @atualizar-itens="atualizarItens"
    @finalizar-compra="finalizarCompra"
  />

  <!-- Páginas -->
  <router-view @adicionar-ao-carrinho="adicionarAoCarrinho" />
</template>

<script setup>
import { ref, computed } from 'vue';

// Estado do carrinho
const itensCarrinho = ref([]);
const mostrarCarrinho = ref(false);

const emit = defineEmits(['adicionar-ao-carrinho']); // Certifique-se que está declarado


// Computed
const totalItens = computed(() => {
  return itensCarrinho.value.reduce((total, item) => total + item.quantidade, 0);
});

// Métodos
function adicionarAoCarrinho(produto) {
  // Emite o evento para o App.vue
  emit('adicionar-ao-carrinho', produto);
  
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

async function finalizarCompra() {
  try {
    const response = await fetch('http://localhost:8080/pedidos', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        itens: itensCarrinho.value
      })
    });
    
    if (!response.ok) throw new Error('Erro ao finalizar pedido');
    
    alert('Pedido realizado com sucesso!');
    itensCarrinho.value = [];
    mostrarCarrinho.value = false;
    
  } catch (error) {
    console.error('Erro:', error);
    alert('Falha ao finalizar pedido: ' + error.message);
  }
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