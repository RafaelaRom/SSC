package com.github.adminfaces.starter.infra.security;

import br.com.soften.exception.DaoException;
import br.com.soften.entidades.Login;
import br.com.soften.entidades.Usuario;
import br.com.soften.services.ServiceUsuario;
import br.com.soften.services.impl.UsuarioServiceImpl;
import br.com.soften.tipos.TipoUsuario;
import br.com.soften.util.FacesMensagensUtil;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 *
 * Created by rmpestano on 06/12/21.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login
 * page or not. By default AdminSession isLoggedIn always resolves to true so it
 * will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user
 * must be redirect to initial page or logon you can skip this class.
 */
//Classe apenas alterada para o uso
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    private Usuario usuarioLogado;

    private ServiceUsuario usuarioService;

    public LogonMB() {

    }

    @PostConstruct
    private void init() {
        usuarioService = new UsuarioServiceImpl();
        usuarioLogado = new Usuario();
    }

    private Usuario cadastrarAdmin() {

        Map<String, Object> filtro = new HashMap<>();
        filtro.put("login.login", "Administrador");

        Usuario usuarioAdmin = usuarioService.buscaUnicaUnicaComParametros(filtro, null);
        if (usuarioAdmin != null) {
            return usuarioAdmin;
        }

        Usuario usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.USUARIO_ADMINISTRADOR);
        Login login = new Login(usuario);
        login.setAtivo(true);
        login.setLogin("Administrador");
        login.setSenha("@Admin2020");
        usuario.setLogin(login);
        usuario.setNome("Administrador Sistema");
        System.out.println("Gravando no banco");
        try {
            return usuarioService.onSalvar(usuario, false);
        } catch (DaoException ex) {
            ex.printStackTrace();
            FacesMensagensUtil.adcionarMensagemErro("Falha ao cadastrar usuário padrão");
            return null;
        }

    }

    public void onEntrar() {
        try {

            if (usuarioLogado.getLogin().getLogin().equals("Administrador") && usuarioLogado.getLogin().getSenhaDescrip().equals("@Admin2020")) {
                usuarioLogado = cadastrarAdmin();
                Faces.redirect("sistema/inicio.soften");
                return;
            }

            usuarioLogado = usuarioService.onLogar(usuarioLogado);
            if (usuarioLogado != null) {
                Faces.redirect("sistema/inicio.soften");
            } else {
                usuarioLogado = new Usuario();
                FacesMensagensUtil.adcionarMensagemErro("Usuário e/ou Senha inválida(s)");
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesMensagensUtil.adcionarMensagemErro("Ocorreu um erro ao realizar o login, tente novamente mais tarde.");
        }
    }

    @Override
    public boolean isLoggedIn() {
        return usuarioLogado.getId() != null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado; 
    }

}
