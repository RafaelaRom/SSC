/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.interceptors;

import br.com.soften.entidades.AcaoDoUsuario;
import br.com.soften.entidades.Usuario;
import br.com.soften.seguranca.Acao;
import br.com.soften.seguranca.AreaDoSistema;
import br.com.soften.util.FacesUtil;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Renata Rafa
 */
@ChecarPermissao(acao = Acao.VISUALIZAR, area = AreaDoSistema.CADASTROS_USUARIOS)
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ChecarPermissaoInterceptor implements Serializable {

    @Inject
    private LogonMB sessaoMB;

    public ChecarPermissaoInterceptor() {

    }

    @AroundInvoke
    public Object verificar(InvocationContext context) throws Exception {

        if (!sessaoMB.getUsuarioLogado().isAdministrador()) {

            ChecarPermissao parametros = context.getMethod().getAnnotation(ChecarPermissao.class);

            AreaDoSistema areaDoSistema = parametros.area();
            Acao acaoRequerida = parametros.acao();

            Usuario usuario = sessaoMB.getUsuarioLogado();

            if (usuario.getPermissaoDeUsuario() == null || usuario.getPermissaoDeUsuario().getPermissoes() == null) {
                FacesUtil.addInfoMessage("Sem permissão para " + acaoRequerida + " na área " + areaDoSistema + ".");
                return null;
            }

            AcaoDoUsuario acoesLiberadas = usuario.getPermissaoDeUsuario().getPermissoes().get(areaDoSistema);

            if (acoesLiberadas == null) {
                FacesUtil.addInfoMessage("Sem permissão para " + acaoRequerida + " na área " + areaDoSistema + ".");
                return null;
            } else if (!acoesLiberadas.isPermitido(acaoRequerida)) {
                System.out.println("Sem permissão");
                FacesUtil.addInfoMessage("Sem permissão para " + acaoRequerida + " na área " + areaDoSistema + ".");
                return null;
            }
        }

        Object retorno = context.proceed();
        return retorno;
    }
}
