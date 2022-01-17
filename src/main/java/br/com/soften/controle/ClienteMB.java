/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.controle;

import br.com.soften.exception.DaoException;
import br.com.soften.entidades.Cidades;
import br.com.soften.entidades.Cliente;
import br.com.soften.entidades.Estados;
import br.com.soften.entidades.PedidoVenda;
import br.com.soften.services.ServiceGenerico;
import br.com.soften.services.impl.ServiceGenericoImpl;
import br.com.soften.table.GenericLazyTable;
import br.com.soften.util.FacesMensagensUtil;
import br.com.soften.util.FacesUtil;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
public class ClienteMB implements ICrudManagedBean, Serializable {

    private ServiceGenerico<Cliente> clienteService;
    private ServiceGenerico<Cidades> cidadeService;
    private ServiceGenerico<PedidoVenda> pedidoVendaService;
    private Cliente cliente;
    private final String AREA = "Cliente";
    private GenericLazyTable<Cliente> tabela;
    private List<Estados> estados;
    private Estados estadoSelecionado;

    @Inject
    private EstadoMB estadoMB;

    @Inject
    private LogonMB sessaoMB;

    @PostConstruct
    private void init() {
        cliente = new Cliente();
        clienteService = new ServiceGenericoImpl<>(Cliente.class);
        pedidoVendaService = new ServiceGenericoImpl<>(PedidoVenda.class);
        cidadeService = new ServiceGenericoImpl<>(Cidades.class);
        tabela = new GenericLazyTable<>(clienteService);
        estados = estadoMB.getEstados();
    }

    @Override
    public void onNovo() {
        cliente = new Cliente();
    }

    public void onBuscarCidades() {
        if (estadoSelecionado != null) {
            Map<String, Object> filtro = new HashMap<>();
            filtro.put("estado", estadoSelecionado);
            List<Cidades> cidadesEncontradas = (List<Cidades>) cidadeService.buscaComParametros(filtro, null);
            estadoSelecionado.setCidades(cidadesEncontradas);
        } else {
            estadoSelecionado = new Estados();
        }

    }

    @Override
    public void onSalvar() {
        boolean editar = cliente.getId() != null;

        try {
            cliente.getEndereco().setEstado(estadoSelecionado.getNome());
            clienteService.onSalvar(cliente, editar);
            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);
            FacesUtil.mostraDialog("cadastroCliente", false);
            cliente = new Cliente();
            estadoSelecionado = new Estados();
        } catch (DaoException e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @Override
    public void onExcluir() {
        if (validaExclusao()) {
            clienteService.onExcluir(cliente, cliente.getId());
            FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
        }
    }

    private boolean validaExclusao() {
        Map<String, Object> filtro = new HashMap<>();
        filtro.put("cliente", cliente);
        int countLinhas = pedidoVendaService.countLinhas(filtro, null);
        if (countLinhas <= 0) {
            return true;
        } else {
            FacesMensagensUtil.adcionarMensagemErro("Cliente não pode ser excluído!");
            return false;
        }
    }

    @Override
    public void onEditar() {
        Estados estado = new Estados(cliente.getEndereco().getEstado());
        estadoSelecionado = estados.get(estados.indexOf(estado));
        onBuscarCidades();
    }

    
    public boolean isDesativaEditar() {
        return cliente == null || cliente.getId() == null;
    }

    public boolean isDesativaExcluir() {
        return isDesativaEditar() || !sessaoMB.getUsuarioLogado().isAdministrador();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public GenericLazyTable<Cliente> getTabela() {
        return tabela;
    }

    public Estados getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Estados estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

}
