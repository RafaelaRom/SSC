package soften.interceptors;

import soften.seguranca.Acao;
import soften.seguranca.AreaDoSistema;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface ChecarPermissao {

    @Nonbinding
    AreaDoSistema area();

    @Nonbinding
    Acao acao();

}
