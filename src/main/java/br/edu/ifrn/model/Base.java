package br.edu.ifrn.model;

import java.io.Serializable;
import java.util.Date;

public class Base implements Serializable, Comparable<Base> {

    private Integer idBase;
    private String mesAno;
    private String url;

    public Base() {
    }

    public Base(Integer id) {
        this.idBase = id;
    }

    public Integer getIdBase() {
        return idBase;
    }

    public String getMesAno() {
        return mesAno;
    }

    public String getUrl() {
        return url;
    }

    public void setIdBase(Integer idBase) {
        this.idBase = idBase;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Base mesAno(String mesAno) {
        this.mesAno = mesAno;
        return this;
    }

    public Base url(String url) {
        this.url = url;
        return this;
    }
      
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idBase == null) ? 0 : idBase.hashCode());
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
        Base other = (Base) obj;
        if (idBase == null) {
            if (other.idBase != null)
                return false;
        } else if (!idBase.equals(other.idBase))
            return false;
        return true;
    }

    public boolean temMesAno() {
        return mesAno != null && !"".equals(mesAno.trim());
    }

    public boolean temUrl() {
        return url != null && !"".equals(url.trim());
    }

    @Override
    public int compareTo(Base o) {
        return this.idBase.compareTo(o.getIdBase());
    }
}
