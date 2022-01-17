/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.services;

import br.com.soften.entidades.Usuario;
import java.io.Serializable;

/**
 *
 *
 * @author Renata Rafa
 */
public interface ServiceUsuario extends Serializable, ServiceGenerico<Usuario> {

   
    public Usuario onLogar(Usuario usuario);

    public void onExcluir(Usuario usuario, Long id);

}
