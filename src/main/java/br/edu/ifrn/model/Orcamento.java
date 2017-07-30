package br.edu.ifrn.model;

import java.io.Serializable;
import java.util.List;

public class Orcamento implements Serializable, Comparable<Orcamento> {

    private Integer idOrcamento;
    private Obra obra;
    private Base base;
    private String descricao;
    private double total;
    
    private List<OrcamentoItens> itens; 
    
    public Orcamento() {
    }

    public Orcamento(Integer idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public Integer getIdOrcamento() {
        return idOrcamento;
    }

    public Obra getObra() {
        return obra;
    }

    public Base getBase() {
        return base;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getTotal() {
        return total;
    }

    public List<OrcamentoItens> getOrcamentoItens() {
	return itens;
    }
    
    public void setIdOrcamento(Integer idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void setItens(List<OrcamentoItens> itens) {
		this.itens = itens;
    }
    
    // usado para criar demo ----------------
    public Orcamento Obra(Obra obra) {
        this.obra = obra;
        return this;
    }

    public Orcamento Base(Base base) {
        this.base = base;
        return this;
    }

    public Orcamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Orcamento total(double total) {
        this.total = total;
        return this;
    }
    // --------------------------------------
    
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
