/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.controle;

import java.io.Serializable;

/**
 * Interface de modelo para todos ManagedBean Crud
 *
 *
 * @author Renata Rafa
 */
public interface ICrudManagedBean extends Serializable {

    public void onNovo();

    public void onSalvar();

    public void onExcluir();

    public void onEditar();

}
