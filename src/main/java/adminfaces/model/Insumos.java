/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminfaces.model;

import java.io.Serializable;

public class Insumos implements Serializable, Comparable<Insumos> {

    private Integer id;
    private String descricao;
    private String unidade;
    private Double preco;

    public Insumos() {
    }

    public Insumos(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Integer getId() {
        return id;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Insumos model(String model) {
        this.descricao = model;
        return this;
    }

    public Insumos price(Double price) {
        this.preco = price;
        return this;
    }

    public Insumos name(String name) {
        this.unidade = name;
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
        Insumos other = (Insumos) obj;
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
    public int compareTo(Insumos o) {
        return this.id.compareTo(o.getId());
    }
}
