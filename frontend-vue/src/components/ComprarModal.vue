<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal-content">
      <h2>Comprar: {{ produto.nome }}</h2>
      <p><strong>Preço unitário:</strong> R$ {{ produto.preco.toFixed(2) }}</p>

      <label>
        Quantidade:
        <input type="number" v-model.number="quantidade" min="1" />
      </label>

      <p><strong>Total:</strong> R$ {{ (quantidade * produto.preco).toFixed(2) }}</p>

      <div class="modal-buttons">
        <button @click="confirmarCompra">Confirmar Compra</button>
        <button class="cancelar" @click="fechar">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, watch } from 'vue'

const props = defineProps({
  show: Boolean,
  produto: Object
})

const emit = defineEmits(['fechar', 'comprar'])

const quantidade = ref(1)

watch(() => props.produto, () => {
  quantidade.value = 1 // resetar quando mudar de produto
})

function fechar() {
  emit('fechar')
}

function confirmarCompra() {
  emit('comprar', { produto: props.produto, quantidade: quantidade.value })
  fechar()
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  width: 90%;
  max-width: 400px;
  box-shadow: 0 4px 25px rgba(0, 0, 0, 0.2);
}

.modal-content h2 {
  margin-bottom: 1rem;
}

.modal-content input {
  margin-left: 0.5rem;
  width: 60px;
  padding: 0.3rem;
}

.modal-buttons {
  margin-top: 1.5rem;
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.modal-buttons button {
  flex: 1;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.modal-buttons button:first-child {
  background-color: #007bff;
  color: white;
}

.modal-buttons .cancelar {
  background-color: #ccc;
  color: black;
}
</style>