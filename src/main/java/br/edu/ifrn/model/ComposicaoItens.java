package br.edu.ifrn.model;

import java.io.Serializable;

public class ComposicaoItens implements Serializable, Comparable<ComposicaoItens> {

    private Integer idComposicaoItens;
    private Integer idComposicao_FK;
    private String tipoItem;
    private String codigoItem;
    private String descricao;
    private String unidade;
    private double coeficiente;
    private double precoUnitario; 
    private double total;

    public ComposicaoItens() {
    }

    public ComposicaoItens(Integer id) {
        this.idComposicaoItens = id;
    }

    public Integer getIdComposicaoItens() {
        return idComposicaoItens;
    }

    public Integer getIdComposicao_FK() {
        return idComposicao_FK;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public String getCodigoItem() {
        return codigoItem;
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

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getTotal() {
        return total;
    }

    public void setIdComposicaoItens(Integer idComposicaoItens) {
        this.idComposicaoItens = idComposicaoItens;
    }

    public void setIdComposicao_FK(Integer idComposicao_FK) {
        this.idComposicao_FK = idComposicao_FK;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
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

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
        
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idComposicaoItens == null) ? 0 : idComposicaoItens.hashCode());
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
        
        ComposicaoItens other = (ComposicaoItens) obj;
        
        if (idComposicaoItens == null) {
            if (other.idComposicaoItens != null)
                return false;
        } else if (!idComposicaoItens.equals(other.idComposicaoItens))
            return false;
        return true;
    }

    public boolean temDescricao() {
        return descricao != null && !"".equals(descricao.trim());
    }
     
    @Override
    public int compareTo(ComposicaoItens o) {
        return this.idComposicaoItens.compareTo(o.getIdComposicaoItens());
    }
}
