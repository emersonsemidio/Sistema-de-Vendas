<template>
  <div class="popup-overlay" @click.self="$emit('fechar')">
    <div class="popup-content">
      <h2>🛒 Seu Carrinho</h2>
      
      <div v-if="itens.length > 0" class="itens-lista">
        <div v-for="(item, index) in itens" :key="index" class="item">
          <span class="nome">{{ item.nome }}</span>
          <div class="controles">
            <button @click="diminuirQuantidade(index)">-</button>
            <span class="quantidade">{{ item.quantidade }}</span>
            <button @click="aumentarQuantidade(index)">+</button>
          </div>
          <span class="preco">R$ {{ formatarPreco(item.preco * item.quantidade) }}</span>
          <button @click="removerItem(index)" class="remover">🗑️</button>
        </div>
      </div>

      <div v-else class="carrinho-vazio">
        <p>Seu carrinho está vazio.</p>
      </div>

      <div class="total">
        <strong>Total: R$ {{ formatarPreco(total) }}</strong>
      </div>
      
      <div class="acoes">
        <button @click="$emit('fechar')" class="fechar">Continuar Comprando</button>
        <button 
          v-if="itens.length > 0" 
          @click="finalizarCompra" 
          class="finalizar"
        >
          Finalizar Compra
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  itens: Array
});

const emit = defineEmits(['fechar', 'atualizar-itens', 'finalizar-compra']);

// Ações do carrinho
function aumentarQuantidade(index) {
  const novosItens = [...props.itens];
  novosItens[index].quantidade++;
  emit('atualizar-itens', novosItens);
}

function removerItem(index) {
  const novosItens = [...props.itens];
  novosItens.splice(index, 1);
  emit('atualizar-itens', novosItens);
}

// Função segura para formatar preços
const formatarPreco = (valor) => {
  return (valor || 0).toFixed(2);
};

// Calcula o total seguro
const total = computed(() => {
  return props.itens.reduce((sum, item) => {
    return sum + ((item.preco || 0) * (item.quantidade || 0));
  }, 0);
});


function diminuirQuantidade(index) {
  const novosItens = [...props.itens];
  if (novosItens[index].quantidade > 1) {
    novosItens[index].quantidade--;
    emit('atualizar-itens', novosItens);
  }
}

function finalizarCompra() {
  alert('Compra finalizada!');
  emit('fechar');


  function adicionarProduto(produto) {
  const novosItens = [...props.itens];
  const itemExistente = novosItens.find(item => item.id === produto.id);
  
  if (itemExistente) {
    itemExistente.quantidade++;
  } else {
    novosItens.push({
      id: produto.id,
      nome: produto.nome,
      preco: produto.preco,
      quantidade: 1
    });
  }
  
  emit('atualizar-itens', novosItens);
}

// Exponha o método para o componente pai
defineExpose({
  adicionarProduto
});

}
</script>

<style scoped>
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.popup-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  max-height: 80vh;
  overflow-y: auto;
}

.item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.controles button {
  padding: 5px 10px;
  margin: 0 5px;
}

.total {
  margin-top: 20px;
  font-size: 1.2em;
  text-align: right;
}

.acoes {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
</style>