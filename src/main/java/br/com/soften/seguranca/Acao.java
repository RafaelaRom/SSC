/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.seguranca;

/**
 * 
 *
 * @author Renata Rafa
 */
public enum Acao {

    SALVAR("Salvar"),
    EDITAR("Editar"),
    EXCLUIR("Excluir"),
    VISUALIZAR("Visualizar");

    private String label;

    private Acao(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
