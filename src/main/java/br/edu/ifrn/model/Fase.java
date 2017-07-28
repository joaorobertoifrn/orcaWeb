package br.edu.ifrn.model;

import java.io.Serializable;

public class Fase implements Serializable, Comparable<Fase> {
 

    private Integer idFase;
    private String itemizacao;
    private String descricao;

    public Fase() {
    }

    public Fase(Integer idFase) {
        this.idFase = idFase;
    }

    public Integer getIdFase() {
        return idFase;
    }

    public String getItemizacao() {
        return itemizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Fase idFase(Integer idFase) {
        this.idFase = idFase;
        return this;
    }

    public Fase itemizacao(String itemizacao) {
        this.itemizacao = itemizacao;
        return this;
    }

    public Fase descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idFase == null) ? 0 : idFase.hashCode());
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
        Fase other = (Fase) obj;
        if (idFase == null) {
            if (other.idFase != null) {
                return false;
            }
        } else if (!idFase.equals(other.idFase)) {
            return false;
        }
        return true;
    }

    public boolean temDescricao() {
        return descricao != null && !"".equals(descricao.trim());
    }

    @Override
    public int compareTo(Fase o) {
        return this.idFase.compareTo(o.getIdFase());
    }
}
