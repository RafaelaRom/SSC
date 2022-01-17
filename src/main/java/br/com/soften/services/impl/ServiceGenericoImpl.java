/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.services.impl;

import br.com.soften.dao.AbstractDao;
import br.com.soften.entidades.Cliente;
import br.com.soften.entidades.PedidoVenda;
import br.com.soften.entidades.Produto;
import br.com.soften.exception.DaoException;
import br.com.soften.services.ServiceGenerico;
import java.util.List;

/**
 *
 * @author Renata Rafa
 * @param <Entidade>
 */
public class ServiceGenericoImpl<Entidade> extends AbstractDao<Entidade> implements ServiceGenerico<Entidade> {

    public ServiceGenericoImpl(Class<Entidade> tipoGenerico) {
        super(tipoGenerico);
    }

    @Override
    public Entidade onSalvar(Entidade entidade, boolean update) throws DaoException {
        try {
            if (update) {
                return super.update(entidade);
            } else {
                return super.save(entidade);
            }
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    
    public void onExcluir(Entidade entidade, Long id) throws DaoException {

        try {
            super.delete(super.findOne(id));
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void onSalvar(List<Entidade> entidades) throws DaoException {
        try {

            super.save(entidades);

        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void onExcluir(Cliente cliente, Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onExcluir(PedidoVenda pedidoVenda, Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onExcluir(Produto produto, Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
