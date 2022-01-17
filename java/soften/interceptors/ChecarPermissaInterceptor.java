package soften.interceptors;

import soften.entidades.AcaoDoUsuario;
import soften.entidades.Usuario;
import soften.seguranca.Acao;
import soften.seguranca.AreaDoSistema;
import soften.util.FacesUtil;
import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.Serializable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;


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
