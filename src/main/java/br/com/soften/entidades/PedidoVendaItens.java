/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author Renata Rafa
 */
@Entity
@Table(name = "pedido_venda_itens")
public class PedidoVendaItens implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTRO_TABELA", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;

    @Column(name = "QTD_ITEM", nullable = false)
    private Integer quantidade = 1;

    @Digits(integer = 10, fraction = 4)
    @Column(name = "VAL_UNITARIO", nullable = false)
    private BigDecimal valorUnitario; // criado para pegar o valor unitario do produto no momento do pedido

    @Digits(integer = 10, fraction = 4)
    @Column(name = "VAL_TOTAL", nullable = false)
    private BigDecimal valorTotal;

    public PedidoVendaItens() {
    }

    public PedidoVendaItens(Produto produto, Integer quantidade, BigDecimal valorUnitario, BigDecimal valorTotal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    public void calculaTotal() {
        this.setValorTotal(this.getValorUnitario().multiply(new BigDecimal(this.getQuantidade())));
    }

    public void atualizaQuantidade(int qnt) {
        this.quantidade = qnt;
        calculaTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.produto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PedidoVendaItens other = (PedidoVendaItens) obj;
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        return true;
    }

}
