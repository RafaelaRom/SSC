package soften.services.impl;

import soften.dao.AbstractDao;
import soften.entidades.Login;
import soften.entidades.Usuario;
import soften.exception.DaoException;
import soften.services.ServiceUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


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

}
