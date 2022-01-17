/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.interceptors;

import br.com.soften.seguranca.Acao;
import br.com.soften.seguranca.AreaDoSistema;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author Renata Rafa
 */
 
@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface ChecarPermissao {

    @Nonbinding
    AreaDoSistema area();

    @Nonbinding
    Acao acao();

}
