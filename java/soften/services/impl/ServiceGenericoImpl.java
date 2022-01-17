package soften.services.impl;

import soften.dao.AbstractDao;
import soften.exception.DaoException;
import soften.services.ServiceGenerico;
import java.util.List;


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

    @Override
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

}
