package br.edu.ifrn.model;

import java.io.Serializable;

public class Composicao implements Serializable, Comparable<Composicao> {

    private Integer id;
    private String classe;
    private String tipo;
    private String descricao;
    private String unidade;
    private double coeficiente;
    private double total;

    public Composicao() {
    }

    public Composicao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getClasse() {
        return classe;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public double getCoeficiente() {
        return coeficiente;
    }

    public double getTotal() {
        return total;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }

    public void setTotal(double total) {
        this.total = total;
    }
//
    public Composicao classe(String classe) {
        this.classe = classe;
        return this;
    }

    public Composicao tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public Composicao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Composicao unidade(String unidade) {
        this.unidade = unidade;
        return this;
    }

    public Composicao coeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
        return this;
    }

    public Composicao total(double total) {
        this.total = total;
        return this;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Composicao other = (Composicao) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public boolean temDescricao() {
        return descricao != null && !"".equals(descricao.trim());
    }

    public boolean temUnidade() {
        return unidade != null && !"".equals(unidade.trim());
    }

    @Override
    public int compareTo(Composicao o) {
        return this.id.compareTo(o.getId());
    }
}
