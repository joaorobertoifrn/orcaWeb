package br.edu.ifrn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Composicao implements Serializable, Comparable<Composicao> {

    private Integer idComposicao;
    private Base base;             
    private Especie especie;      // MATERIAL,  MAO_DE_OBRA, EQUIPAMENTOS
    private Tipo tipo;            // COMPOSICAO, INSUMO  
    private String classe;
    private String descricao;
    private String unidade;          
    private double valorUnitario; 
    
    private List<ComposicaoItens> itens = new ArrayList<ComposicaoItens>();

    public Composicao() {
    }

    public Integer getIdComposicao() {
        return idComposicao;
    }

    public void setIdComposicao(Integer idComposicao) {
        this.idComposicao = idComposicao;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public List<ComposicaoItens> getItens() {
        return itens;
    }

    public void setItens(List<ComposicaoItens> itens) {
        this.itens = itens;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idComposicao == null) ? 0 : idComposicao.hashCode());
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
        if (idComposicao == null) {
            if (other.idComposicao != null)
                return false;
        } else if (!idComposicao.equals(other.idComposicao))
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
        return this.idComposicao.compareTo(o.getIdComposicao());
    }
}
