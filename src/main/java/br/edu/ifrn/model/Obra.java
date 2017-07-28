package br.edu.ifrn.model;

import java.io.Serializable;

public class Obra implements Serializable, Comparable<Obra> {

    private Integer idObra;
    private String nomeObra;
    private double BDI;

    public Obra() {
    }

    public Obra(Integer id) {
        this.idObra = id;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public double getBDI() {
        return BDI;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public void setBDI(double BDI) {
        this.BDI = BDI;
    }
    
    public Obra nomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
        return this;
    }

    public Obra BDI(double BDI) {
        this.BDI = BDI;
        return this;
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idObra == null) ? 0 : idObra.hashCode());
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
        Obra other = (Obra) obj;
        if (idObra == null) {
            if (other.idObra != null)
                return false;
        } else if (!idObra.equals(other.idObra))
            return false;
        return true;
    }

    public boolean temNomeObra() {
        return nomeObra != null && !"".equals(nomeObra.trim());
    }

    @Override
    public int compareTo(Obra o) {
        return this.idObra.compareTo(o.getIdObra());
    }
}
