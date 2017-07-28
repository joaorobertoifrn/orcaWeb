package br.edu.ifrn.model;

import java.io.Serializable;


public class OrcamentoItens implements Serializable, Comparable<OrcamentoItens> {

    private Integer idOrcamentoItens;
    private Integer idOrcamento_FK;
    private Integer idFase_FK;
    private String tipoItem;            //  C-Composição  I-Insumo
    private Integer codigoItem;
    private String descricao;
    private String unidade;
    private String coeficiente;
    private Double quantidade;
    private Double precoUnitario;
    private Double total;

    public OrcamentoItens() {
    }

    public Integer getIdOrcamentoItens() {
        return idOrcamentoItens;
    }

    public Integer getIdOrcamento_FK() {
        return idOrcamento_FK;
    }

    public Integer getIdFase_FK() {
        return idFase_FK;
    }

    public String getTipoItem() {
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

    public String getCoeficiente() {
        return coeficiente;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public Double getTotal() {
        return total;
    }

    public void setIdOrcamentoItens(Integer idOrcamentoItens) {
        this.idOrcamentoItens = idOrcamentoItens;
    }

    public void setIdOrcamento_FK(Integer idOrcamento_FK) {
        this.idOrcamento_FK = idOrcamento_FK;
    }

    public void setIdFase_FK(Integer idFase_FK) {
        this.idFase_FK = idFase_FK;
    }

    public void setTipoItem(String tipoItem) {
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

    public void setCoeficiente(String coeficiente) {
        this.coeficiente = coeficiente;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public OrcamentoItens idOrcamento_FK(Integer idOrcamento_FK) {
        this.idOrcamento_FK = idOrcamento_FK;
        return this;
    }

    public OrcamentoItens idFase_FK(Integer idFase_FK) {
        this.idFase_FK = idFase_FK;
        return this;
    }

    public OrcamentoItens tipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
        return this;
    }

    public OrcamentoItens codigoItem(Integer codigoItem) {
        this.codigoItem = codigoItem;
        return this;
    }

    public OrcamentoItens descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public OrcamentoItens unidade(String unidade) {
        this.unidade = unidade;
        return this;
    }

    public OrcamentoItens coeficiente(String coeficiente) {
        this.coeficiente = coeficiente;
        return this;
    }

    public OrcamentoItens quantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public OrcamentoItens precoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
        return this;
    }

    public OrcamentoItens total(Double total) {
        this.total = total;
        return this;
    }
    
      
  
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idOrcamentoItens == null) ? 0 : idOrcamentoItens.hashCode());
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
        if (idOrcamentoItens == null) {
            if (other.idOrcamentoItens != null) {
                return false;
            }
        } else if (!idOrcamentoItens.equals(other.idOrcamentoItens)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(OrcamentoItens o) {
        return this.idOrcamentoItens.compareTo(o.getIdOrcamentoItens());
    }
}
