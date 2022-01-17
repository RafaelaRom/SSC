/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.controle;

import br.com.soften.exception.DaoException;
import br.com.soften.entidades.PedidoVendaItens;
import br.com.soften.entidades.Produto;
import br.com.soften.services.ServiceGenerico;
import br.com.soften.services.impl.ServiceGenericoImpl;
import br.com.soften.table.GenericLazyTable;
import br.com.soften.util.FacesMensagensUtil;
import br.com.soften.util.FacesUtil;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Renata Rafa
 */
@Named
@ViewScoped
public class ProdutoMB implements ICrudManagedBean, Serializable {

    private ServiceGenerico<Produto> produtoService;
    private ServiceGenerico<PedidoVendaItens> itemVendaService;
    private Produto produto;
    private final String AREA = "Produto";
    private GenericLazyTable<Produto> tabela;

    @Inject
    private LogonMB sessaoMB;

    @PostConstruct
    private void init() {
        produto = new Produto();
        produtoService = new ServiceGenericoImpl<>(Produto.class);
        itemVendaService = new ServiceGenericoImpl<>(PedidoVendaItens.class);
        tabela = new GenericLazyTable<>(produtoService);
    }

    @Override
    public void onNovo() {
        produto = new Produto();
    }

    @Override
    public void onSalvar() {
        boolean editar = produto.getId() != null;

        try {
            produtoService.onSalvar(produto, editar);
            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);
            FacesUtil.mostraDialog("cadastroProduto", false);
            produto = new Produto();
        } catch (DaoException e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @Override
    public void onExcluir() {

        if (validaExclusao()) {
            produtoService.onExcluir(produto, produto.getId());
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        }
    }

    private boolean validaExclusao() {
        Map<String, Object> filtro = new HashMap<>();
        filtro.put("produto", produto);
        int countLinhas = itemVendaService.countLinhas(filtro, null);
        if (countLinhas <= 0) {
            return true;
        } else {
            FacesMensagensUtil.adcionarMensagemErro("Produto não pode ser excluído!");
            return false;
        }
    }

    @Override
    public void onEditar() {
    }

    
    public boolean isDesativaEditar() {
        return produto == null || produto.getId() == null;
    }

    public boolean isDesativaExcluir() {
        return isDesativaEditar() || !sessaoMB.getUsuarioLogado().isAdministrador();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public GenericLazyTable<Produto> getTabela() {
        return tabela;
    }

    public void setTabela(GenericLazyTable<Produto> tabela) {
        this.tabela = tabela;
    }

}
