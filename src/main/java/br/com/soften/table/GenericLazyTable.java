/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.table;

import br.com.soften.services.ServiceGenerico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Renata Rafa
 * @param <Entidade>
 */
public class GenericLazyTable<Entidade> extends LazyDataModel<Entidade> {

    private List<Entidade> listModelos;
    private ServiceGenerico<Entidade> service;
    private Map<String, Object> filtrosOperadorAND = null;
    private Map<String, Object[]> filtrosOperadorOR = null;
    private int linhas;

    public GenericLazyTable(ServiceGenerico service) {
        this.service = service;
        filtrosOperadorAND = new HashMap<>();
        filtrosOperadorOR = new HashMap<>();
    }

    @Override
    public List<Entidade> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        if (!filtrosOperadorAND.isEmpty()) {
            filters.putAll(filtrosOperadorAND);
        }

        if (sortField != null) {
            boolean ascendingOrder = (sortOrder == SortOrder.ASCENDING);
            listModelos = service.buscaPaginada(first, first + pageSize, filters, filtrosOperadorOR, sortField, ascendingOrder);
        } else {
            listModelos = service.buscaPaginada(first, first + pageSize, filters, filtrosOperadorOR);
        }

        linhas = service.countLinhas(filtrosOperadorAND, filtrosOperadorOR);
        setRowCount(linhas);
        return listModelos;
    }

    public void limparFiltros() {
        filtrosOperadorAND.clear();
        filtrosOperadorOR.clear();
    }

    public void adicionarFiltro(String campo, Object object) {
        filtrosOperadorAND.put(campo, object);
    }

    public void removerFiltro(String campo) {
        filtrosOperadorAND.remove(campo);
    }

    public void adicionarFiltroOperadorOR(String nomeDoCampo, Object[] valorDoCampo) {

        filtrosOperadorOR.put(nomeDoCampo, valorDoCampo);
    }

    public boolean removerFiltroOperadorOR(String nomeDoCampo, Object campoAlterar) {
        Object[] campos = filtrosOperadorOR.get(nomeDoCampo);
        if (campos == null) {

            return false;
        }
        List<Object> camposToArray = new ArrayList(Arrays.asList(campos));
        Iterator<Object> camposIterator = camposToArray.iterator();

        while (camposIterator.hasNext()) {
            Object atual = camposIterator.next();

            if (atual.equals(campoAlterar)) {
                camposIterator.remove();
            }
        }

        filtrosOperadorOR.remove(nomeDoCampo);
        filtrosOperadorOR.put(nomeDoCampo, camposToArray.toArray());
        return true;
    }

    public void removerFiltroOperadorOR(String nomeDoCampo) {

        filtrosOperadorOR.remove(nomeDoCampo);
    }

    public List<Entidade> getModelos() {
        return listModelos;
    }

    @Override
    public Object getRowKey(Entidade entidade) {
        return entidade.hashCode();
    }

    /**
     *
     * @param rowKey
     * @return
     */
    @Override
    public Entidade getRowData(String rowKey) {
        List<Entidade> entidades = (List<Entidade>) getWrappedData();
        if (entidades == null) {
            return null;
        }
        Integer value = Integer.valueOf(rowKey);

        for (Entidade entidade : entidades) {
            if (Objects.equals(entidade.hashCode(), value)) {
                return entidade;
            }
        }
        return null;
    }

    public int getLinhas() {
        return linhas;
    }

}
