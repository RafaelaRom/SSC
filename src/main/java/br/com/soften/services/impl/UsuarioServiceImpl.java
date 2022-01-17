/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.services.impl;

import br.com.soften.dao.AbstractDao;
import br.com.soften.entidades.Cliente;
import br.com.soften.entidades.Login;
import br.com.soften.entidades.PedidoVenda;
import br.com.soften.entidades.Produto;
import br.com.soften.entidades.Usuario;
import br.com.soften.exception.DaoException;
import br.com.soften.services.ServiceUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Renata Rafa
 */
public class UsuarioServiceImpl extends AbstractDao<Usuario> implements ServiceUsuario {

    public UsuarioServiceImpl() {
        super(Usuario.class);
    }

    @Override
    public Usuario onSalvar(Usuario entidade, boolean update) {

        if (update) {
            this.update(entidade);
        } else {
            entidade = this.save(entidade);
        }
        return entidade;
    }

    @Override
    public void onExcluir(Usuario entidade, Long id) {
        entidade = this.findOne(entidade.getId());
        this.delete(entidade);
    }

    @Override
    public Usuario onLogar(Usuario usuario) {
        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();

        Root entidade = query.from(Login.class);
        query.select(entidade);
        Path pathLogin = entidade.get("login");
        List<Predicate> queryPredicates = new ArrayList<>();
        queryPredicates.add(cb.equal(pathLogin, usuario.getLogin().getLogin()));
        query.where(queryPredicates.toArray(new Predicate[]{}));

        TypedQuery<Login> typedQuery = getEm().createQuery(query);
        try {
            Login loginBanco = typedQuery.getSingleResult();
            if (usuario.getLogin().getSenhaDescrip().equals(loginBanco.getSenhaDescrip())) {
                return loginBanco.getUsuario();
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void onSalvar(List<Usuario> entidades) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
