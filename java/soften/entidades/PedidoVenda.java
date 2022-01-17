package soften.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "pedido_venda")
public class PedidoVenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTRO_TABELA", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @Digits(integer = 10, fraction = 4)
    @Column(name = "VAL_TOTAL_PEDIDO", nullable = false)
    private BigDecimal valorTotalPedido;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PedidoVendaItens> itens;

    public PedidoVenda() {
        cliente = new Cliente();
        valorTotalPedido = new BigDecimal("0.0000");
    }

    public PedidoVenda(Cliente cliente, BigDecimal valorTotalPedido, List<PedidoVendaItens> itens) {
        this.cliente = cliente;
        this.valorTotalPedido = valorTotalPedido;
        this.itens = itens;
    }

    public void atualizaValorTotal() {
        Function<PedidoVendaItens, BigDecimal> totalMapper = item -> item.getValorUnitario().multiply(new BigDecimal(item.getQuantidade()));
        valorTotalPedido = itens.stream().map(totalMapper).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotalPedido() {
        return valorTotalPedido;
    }

    public void setValorTotalPedido(BigDecimal valorTotalPedido) {
        this.valorTotalPedido = valorTotalPedido;
    }

    public List<PedidoVendaItens> getItens() {
        if (itens == null) {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<PedidoVendaItens> itens) {
        this.itens = itens;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final PedidoVenda other = (PedidoVenda) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
