package soften.services;

import soften.exception.DaoException;
import java.util.List;
import java.util.Map;

public interface ServiceGenerico<Entidade> {

 public Entidade onSalvar(Entidade entidade, boolean update) throws DaoException;
 
  public void onSalvar(List<Entidade> entidades) throws DaoException;

public void onExcluir(Entidade entidade, Long id) throws DaoException;

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