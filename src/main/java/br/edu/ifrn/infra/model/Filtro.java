package br.edu.ifrn.infra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Filtro<T extends Serializable> {
    private T entidade;
    private int primeiro;
    private int tamanhoPagina;
    private String sortField;
    private SortOrder sortOrder;
    private Map<String, Object> parametros = new HashMap<String, Object>();


    public Filtro() {
    }

    public Filtro(T entidade) {
        this.entidade = entidade;
    }

    public Filtro setPrimeiro(int primeiro) {
        this.primeiro = primeiro;
        return this;
    }

    public int getPrimeiro() {
        return primeiro;
    }

    public Filtro setTamanhoPagina(int pageSize) {
        this.tamanhoPagina = pageSize;
        return this;
    }

    public int getTamanhoPagina() {
        return tamanhoPagina;
    }

    public Filtro setSortField(String sortField) {
        this.sortField = sortField;
        return this;
    }

    public String getSortField() {
        return sortField;
    }

    public Filtro setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public Filtro setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
        return this;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public T getEntidade() {
        return entidade;
    }

    public Filtro setEntidade(T entidade) {
        this.entidade = entidade;
        return this;
    }

    public Filtro adicionaParametros(String key, Object value) {
        getParametros().put(key, value);
        return this;
    }

    public boolean temParamentro(String key) {
        return getParametros().containsKey(key) && getParametro(key) != null;
    }

    public Object getParametro(String key) {
        return getParametros().get(key);
    }
}
