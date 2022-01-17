/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.controle;

import br.com.soften.entidades.Estados;
import br.com.soften.services.ServiceGenerico;
import br.com.soften.services.impl.ServiceGenericoImpl;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Renata Rafa
 */
@Named
@ApplicationScoped 
public class EstadoMB {

    private List<Estados> estados;
    private ServiceGenerico<Estados> estadoService;

    @PostConstruct
    private void init() {
        estadoService = new ServiceGenericoImpl<>(Estados.class);
        onAtualizar();
    }

    public void onAtualizar() { 
        estados = estadoService.findAll();
    }

    public List<Estados> getEstados() {
        return estados;
    }

    public void setEstados(List<Estados> estados) {
        this.estados = estados;
    }

}
