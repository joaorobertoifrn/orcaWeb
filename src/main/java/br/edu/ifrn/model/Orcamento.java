package br.edu.ifrn.model;

import java.io.Serializable;
import java.util.Date;

public class Orcamento implements Serializable, Comparable<Orcamento> {

    private Integer idOrcamento;
    private Integer idObra_FK;
    private Integer idBasePrecos_FK;
    private String descricao;
    private Date dataCriacao;
    private double total;
    
    public Orcamento() {
    }

    public Orcamento(Integer idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public Integer getIdOrcamento() {
        return idOrcamento;
    }

    public Integer getIdObra_FK() {
        return idObra_FK;
    }

    public Integer getIdBasePrecos_FK() {
        return idBasePrecos_FK;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public double getTotal() {
        return total;
    }

    public void setIdOrcamento(Integer idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public void setIdObra_FK(Integer idObra_FK) {
        this.idObra_FK = idObra_FK;
    }

    public void setIdBasePrecos_FK(Integer idBasePrecos_FK) {
        this.idBasePrecos_FK = idBasePrecos_FK;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public Orcamento idOrcamento(Integer idOrcamento) {
        this.idOrcamento = idOrcamento;
        return this;
    }

    public Orcamento idObra_FK(Integer idObra_FK) {
        this.idObra_FK = idObra_FK;
        return this;
    }

    public Orcamento idBase_FK(Integer idBase_FK) {
        this.idBasePrecos_FK = idBase_FK;
        return this;
    }

    public Orcamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Orcamento dataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public Orcamento total(double total) {
        this.total = total;
        return this;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idOrcamento == null) ? 0 : idOrcamento.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Orcamento other = (Orcamento) obj;
        if (idOrcamento == null) {
            if (other.idOrcamento != null) {
                return false;
            }
        } else if (!idOrcamento.equals(other.idOrcamento)) {
            return false;
        }
        return true;
    }

    public boolean temDescricao() {
        return descricao != null && !"".equals(descricao.trim());
    }

    @Override
    public int compareTo(Orcamento o) {
        return this.idOrcamento.compareTo(o.getIdOrcamento());
    }
}
