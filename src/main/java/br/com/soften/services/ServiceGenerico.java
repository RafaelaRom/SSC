/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.services;

import br.com.soften.entidades.Cliente;
import br.com.soften.entidades.PedidoVenda;
import br.com.soften.entidades.Produto;
import br.com.soften.exception.DaoException;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Renata Rafa
 * @param <Entidade>
 */
public interface ServiceGenerico<Entidade> {

  
     
    public Entidade onSalvar(Entidade entidade, boolean update) throws DaoException;

    
    public void onSalvar(List<Entidade> entidades) throws DaoException;

  
    List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR);

    
    List<Entidade> buscaPaginada(int inicio, int fim, Map<String, Object> filtrosOperadorAND,
            Map<String, Object[]> filtrosOperadorOR,
            String sortField,
            boolean ascendingOrder);

    
    int countLinhas(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

   
    Entidade buscaUnicaUnicaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

    
    List<Entidade> buscaComParametros(Map<String, Object> filtrosOperadorAND, Map<String, Object[]> filtrosOperadorOR);

   
    public List<Entidade> findAll();

   
    public Entidade findOne(Object id);

    public List<?> loadLazyRelationship(Object myEntity, String relacionamento);

    public void onExcluir(Cliente cliente, Long id);

    public void onExcluir(PedidoVenda pedidoVenda, Long id);

    public void onExcluir(Produto produto, Long id);

}
