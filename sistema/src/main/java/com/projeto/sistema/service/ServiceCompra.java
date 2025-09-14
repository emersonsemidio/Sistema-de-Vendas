package com.projeto.sistema.service;

import com.projeto.sistema.Enum.StatusPedido;
import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.dto.CompraRegisterDto;
import com.projeto.sistema.dto.ItemVendaDto;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.model.Compra;
import com.projeto.sistema.model.ItemVenda;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.model.Usuario;
import com.projeto.sistema.repo.RepoCompra;
import com.projeto.sistema.repo.RepoProduto;
import com.projeto.sistema.repo.RepoCliente;
import com.projeto.sistema.repo.RepoUsuario;

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
    private RepoUsuario repoUsuario;

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

    public Compra salvar(Compra compra) {
        boolean clienteExiste = repoCliente.existsById(compra.getCliente().getId());
        boolean usuarioExiste = repoUsuario.existsById(compra.getUsuario().getId());

        if (!clienteExiste && !usuarioExiste) {
            throw new IllegalArgumentException("Cliente com ID " + compra.getCliente().getId() + " e Usuário com ID " + compra.getUsuario().getId() + " não existem.");
        } else if (!clienteExiste) {
            throw new IllegalArgumentException("Cliente com ID " + compra.getCliente().getId() + " não existe.");
        } else if (!usuarioExiste) {
            throw new IllegalArgumentException("Usuário com ID " + compra.getUsuario().getId() + " não existe.");
        }

        return repoCompra.save(compra);
    }


    public Optional<Compra> atualizar(Long id, Compra novaCompra) {
        return repoCompra.findById(id).map(compraExistente -> {
            compraExistente.setCliente(novaCompra.getCliente());
            compraExistente.setTotal(novaCompra.getTotal());
            compraExistente.setUsuario(novaCompra.getUsuario());
            compraExistente.setFormaPagamento(novaCompra.getFormaPagamento());
            compraExistente.setStatus(novaCompra.getStatus());
            return repoCompra.save(compraExistente);
        });
    }

    public Compra convertRegisterDtoToEntity(CompraRegisterDto dto) {
        Compra compra = new Compra();
        Cliente cliente = repoCliente.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));        
        Usuario usuario = repoUsuario.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        // Apenas seta os campos que vieram no DTO
        compra.setCliente(cliente);
        compra.setUsuario(usuario);
        compra.setFormaPagamento(dto.getFormaPagamento());
        compra.setItens(converterItensDtoParaEntidade(dto.getItens()));
        compra.setTotal(calcularTotalCompra(dto.getItens()));
        compra.setStatus(StatusPedido.PENDENTE);
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
            Produto produto =   repoProduto.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + itemDto.getProdutoId() + " não encontrado"));
            item.setProduto(produto);
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
