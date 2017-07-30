package br.edu.ifrn.model;

import java.io.Serializable;


public class OrcamentoItens implements Serializable, Comparable<OrcamentoItens> {

    private Integer idOrcaItens;
    private Orcamento orcamento;
    private Fase fase;
    private Tipo tipoItem;             
    private Integer codigoItem;
    private String descricao;
    private String unidade;
    private Double quantidade;
    private Double precoUnitario;

    public OrcamentoItens() {
    }

    public Double getTotal() {
        if(quantidade != null && precoUnitario != null);
	  return quantidade * precoUnitario;  
    }
    
    public Integer getIdOrcaItens() {
        return idOrcaItens;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public Fase getFase() {
        return fase;
    }

    public Tipo getTipoItem() {
        return tipoItem;
    }

    public Integer getCodigoItem() {
        return codigoItem;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setIdOrcaItens(Integer idOrcaItens) {
        this.idOrcaItens = idOrcaItens;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public void setTipoItem(Tipo tipoItem) {
        this.tipoItem = tipoItem;
    }

    public void setCodigoItem(Integer codigoItem) {
        this.codigoItem = codigoItem;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    //public void setCoeficiente(Double coeficiente) {
    //    this.coeficiente = coeficiente;
    //}

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
  
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idOrcaItens == null) ? 0 : idOrcaItens.hashCode());
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
        OrcamentoItens other = (OrcamentoItens) obj;
        if (idOrcaItens == null) {
            if (other.idOrcaItens != null) {
                return false;
            }
        } else if (!idOrcaItens.equals(other.idOrcaItens)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(OrcamentoItens o) {
        return this.idOrcaItens.compareTo(o.getIdOrcaItens());
    }
}
