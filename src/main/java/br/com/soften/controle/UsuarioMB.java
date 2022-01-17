/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.controle;

import br.com.soften.exception.DaoException;
import br.com.soften.entidades.Usuario;
import br.com.soften.interceptors.ChecarPermissao;
import br.com.soften.seguranca.Acao;
import br.com.soften.seguranca.AreaDoSistema;
import br.com.soften.services.ServiceUsuario;
import br.com.soften.services.impl.UsuarioServiceImpl;
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
public class UsuarioMB implements ICrudManagedBean, Serializable {

    private ServiceUsuario usuarioService;
    private Usuario usuario;
    private final String AREA = "Usuário";
    private GenericLazyTable<Usuario> tabela;

    @Inject
    private LogonMB sessaoMB;

    @PostConstruct
    private void init() {
        usuarioService = new UsuarioServiceImpl();
        tabela = new GenericLazyTable<>(usuarioService);
    }

    @Override
    public void onNovo() {
        usuario = new Usuario();
    }

    
    @ChecarPermissao(acao = Acao.EDITAR, area = AreaDoSistema.CADASTROS_USUARIOS) //analisar permissões antes
    @Override
    public void onEditar() {

    }

    @Override
    public void onSalvar() {

        if (!onValidaUsuario()) { 
            FacesMensagensUtil.adicionarMensagemErro("Nome de usuário já existente", AREA);
            return;
        }

        boolean editar = usuario.getId() != null;

        try {
            usuarioService.onSalvar(usuario, editar);
            FacesMensagensUtil.adicionarMensagemSalvoSucesso(AREA, editar);
            FacesUtil.mostraDialog("cadastroUsuario", false);
            usuario = new Usuario();
        } catch (DaoException e) {
            FacesMensagensUtil.adicionarMensagemSalvarErro(AREA, editar);
        }
    }

    @Override
    public void onExcluir() {
        if (usuario.getId().equals(sessaoMB.getUsuarioLogado().getId())) {
            FacesMensagensUtil.adicionarMensagemErro("Não é possível excluir seu próprio usuário", AREA);
            return;
        }
        usuarioService.onExcluir(usuario, usuario.getId());
        FacesMensagensUtil.adicionarMensagemExcluir(AREA, true);
    }

    
    private boolean onValidaUsuario() {
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("login.login", usuario.getLogin().getLogin());
        Usuario userFind = usuarioService.buscaUnicaUnicaComParametros(filtros, null);
        return userFind == null || userFind.getId().equals(usuario.getId());
    }

   
    public boolean isDesativaEditar() {
        return usuario == null || usuario.getId() == null;
    }

    public boolean isDesativaExcluir() {
        return isDesativaEditar() || !sessaoMB.getUsuarioLogado().isAdministrador();
    }

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public GenericLazyTable<Usuario> getTabela() {
        return tabela;
    }

}
