package com.projeto.sistema.service;

import com.projeto.sistema.Enum.StatusPedido;
import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.dto.CompraRegisterDto;
import com.projeto.sistema.dto.ItemVendaDto;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.model.Compra;
import com.projeto.sistema.model.ItemVenda;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.model.Mercado;
import com.projeto.sistema.repo.RepoCompra;
import com.projeto.sistema.repo.RepoProduto;
import com.projeto.sistema.repo.RepoCliente;
import com.projeto.sistema.repo.RepoMercado;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCompra {

    @Autowired
    private RepoCompra repoCompra;

    @Autowired
    private RepoMercado repoMercado;

    @Autowired
    private RepoCliente repoCliente;

    @Autowired
    private RepoProduto repoProduto;

    public Iterable<Compra> listarTodas() {
        return repoCompra.findAll();
    }

    public Optional<Compra> buscarPorId(Long id) {
        return repoCompra.findById(id);
    }

    @Transactional
    public Compra salvar(Compra compra) {
        return repoCompra.save(compra);
    }


    public Optional<Compra> atualizar(Long id, Compra novaCompra) {
        return repoCompra.findById(id).map(compraExistente -> {
            compraExistente.setFormaPagamento(novaCompra.getFormaPagamento());
            compraExistente.setStatus(novaCompra.getStatus());
            return repoCompra.save(compraExistente);
        });
    }

    @Transactional
    public Compra convertRegisterDtoToEntity(CompraRegisterDto dto) {
        Compra compra = new Compra();
        Cliente cliente = repoCliente.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Mercado mercado = repoMercado.findById(dto.getMercadoId())
                .orElseThrow(() -> new RuntimeException("Mercado não encontrado"));
        // Apenas seta os campos que vieram no DTO
        compra.setCliente(cliente);
        compra.setMercado(mercado);
        compra.setFormaPagamento(dto.getFormaPagamento());
        compra.setTotal(calcularTotalCompra(dto.getItens()));
        compra.setStatus(StatusPedido.PENDENTE);

        List<ItemVenda> itens = converterItensDtoParaEntidade(dto.getItens());
        for (ItemVenda item : itens) {
            compra.adicionarItem(item);
        }

        return compra;
    }

    private Double calcularTotalCompra(List<ItemVendaDto> itens) {
        return itens.stream()
                .mapToDouble(item -> {
                    // Buscar preço do produto
                    Produto produto = repoProduto.findById(item.getProdutoId())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getProdutoId()));
                    
                    return item.getQuantidade() * produto.getPreco();
                })
                .sum();
    }

    private List<ItemVenda> converterItensDtoParaEntidade(List<ItemVendaDto> itensDto) {
        List<ItemVenda> itens = new ArrayList<>();
        
        for (ItemVendaDto itemDto : itensDto) {
            ItemVenda item = new ItemVenda();
            Produto produto = repoProduto.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + itemDto.getProdutoId() + " não encontrado"));
            item.setProduto(produto);
            produto.setQuantidade(produto.getQuantidade() - itemDto.getQuantidade());
            repoProduto.save(produto); // Atualiza a quantidade no banco
            item.setQuantidade(itemDto.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            itens.add(item);
        }
        
        return itens;
    }

    public boolean deletar(Long id) {
        if (repoCompra.existsById(id)) {
            repoCompra.deleteById(id);
            return true;
        }
        return false;
    }
}
