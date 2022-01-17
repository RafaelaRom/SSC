/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.controle;

import br.com.soften.exception.DaoException;
import br.com.soften.services.ServiceGenerico;
import br.com.soften.services.impl.ServiceGenericoImpl;
import br.com.soften.table.GenericLazyTable;
import br.com.soften.util.FacesMensagensUtil;
import br.com.soften.util.FacesUtil;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.soften.entidades.PedidoVenda;
import br.com.soften.entidades.PedidoVendaItens;
import java.util.List;

/**
 *
 * @author Renata Rafa
 */
@Named
@ViewScoped
public class PedidoMB implements ICrudManagedBean, Serializable {

    private ServiceGenerico<PedidoVenda> pedidoService;
    private PedidoVenda pedidoVenda;
    private final String AREA = "Pedido";
    private GenericLazyTable<PedidoVenda> tabela;
    private PedidoVendaItens item;

    @Inject
    private LogonMB sessaoMB;

    @PostConstruct
    private void init() {
        pedidoVenda = new PedidoVenda();
        pedidoService = new ServiceGenericoImpl<>(PedidoVenda.class);
        tabela = new GenericLazyTable<>(pedidoService);
    }

    @Override
    public void onNovo() {
        pedidoVenda = new PedidoVenda();
        item = new PedidoVendaItens();
    }

    @Override
    public void onSalvar() {
        boolean editar = pedidoVenda.getId() != null;

        try {
            if (validaPedido()) {
                pedidoService.onSalvar(pedidoVenda, editar);
                FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);

                FacesUtil.mostraDialog("cadastroPedido", false);
                onNovo();
            }
        } catch (DaoException e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    private boolean validaPedido() {
        if (pedidoVenda.getCliente().getId() == null) {
            FacesMensagensUtil.adcionarMensagemErro("Selecione o cliente para salvar");
            return false;
        }

        if (pedidoVenda.getItens().isEmpty()) {
            FacesMensagensUtil.adcionarMensagemErro("Selecione pelo menos um item ao pedido");
            return false;
        }

        return true;
    }

    @Override
    public void onExcluir() {
        pedidoService.onExcluir(pedidoVenda, pedidoVenda.getId());
        FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
    }

    @Override
    public void onEditar() {
        List<PedidoVendaItens> itensVenda = (List<PedidoVendaItens>) pedidoService.loadLazyRelationship(pedidoVenda, "itens");
        pedidoVenda.setItens(itensVenda);

    }

    public void onNovoItem() {
        item = new PedidoVendaItens();
    }

    public void onSelecionaProduto() {
        item.setValorUnitario(item.getProduto().getValorVenda());
        item.calculaTotal();
    }

    public void onAlteraQuantidade() {
        item.calculaTotal();
    }

    public void onIncluirItem() {
        try {
            int indexItem = pedidoVenda.getItens().indexOf(item);
            if (indexItem < 0) {
                pedidoVenda.getItens().add(item);

            } else {
                PedidoVendaItens itemJaAdicionado = pedidoVenda.getItens().get(indexItem);
                itemJaAdicionado.atualizaQuantidade(item.getQuantidade());
            }
            pedidoVenda.atualizaValorTotal();

            FacesMensagensUtil.adcionarMensagem("Item adicionado com sucesso");
            FacesUtil.mostraDialog("consultaProduto", false);
        } catch (Exception e) {
            FacesMensagensUtil.adcionarMensagemErro("Falha ao incluir item, tente novamente mais tarde");

        }
    }

    public void onRemoverItem() {
        boolean remove = pedidoVenda.getItens().remove(item);
        if (remove) {
            pedidoVenda.atualizaValorTotal();
            FacesMensagensUtil.adcionarMensagem("Item removido com sucesso");
        } else {
            FacesMensagensUtil.adcionarMensagemErro("Falha ao remover item");
        }
    }

  
    public boolean isDesativaEditar() {
        return pedidoVenda == null || pedidoVenda.getId() == null;
    }

    public boolean isDesativaExcluir() {
        return isDesativaEditar() || !sessaoMB.getUsuarioLogado().isAdministrador();
    }

    public PedidoVenda getPedidoVenda() {
        return pedidoVenda;
    }

    public void setPedidoVenda(PedidoVenda pedidoVenda) {
        this.pedidoVenda = pedidoVenda;
    }

    public GenericLazyTable<PedidoVenda> getTabela() {
        return tabela;
    }

    public void setTabela(GenericLazyTable<PedidoVenda> tabela) {
        this.tabela = tabela;
    }

    public PedidoVendaItens getItem() {
        return item;
    }

    public void setItem(PedidoVendaItens item) {
        this.item = item;
    }

}
