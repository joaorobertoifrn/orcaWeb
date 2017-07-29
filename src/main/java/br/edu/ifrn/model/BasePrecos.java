package br.edu.ifrn.model;

import java.io.Serializable;
import java.util.Date;

public class BasePrecos implements Serializable, Comparable<BasePrecos> {

    private Integer idBasePrecos;
    private String mesAno;
    private Date dataImportacao;
    private String url;

    public BasePrecos() {
    }

    public BasePrecos(Integer id) {
        this.idBasePrecos = id;
    }

    public Integer getIdBasePrecos() {
        return idBasePrecos;
    }

    public String getMesAno() {
        return mesAno;
    }

    public Date getDataImportacao() {
        return dataImportacao;
    }

    public String getUrl() {
        return url;
    }

    public void setId(Integer idBasePrecos) {
        this.idBasePrecos = idBasePrecos;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public void setDataImportacao(Date dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BasePrecos mesAno(String mesAno) {
        this.mesAno = mesAno;
        return this;
    }

    public BasePrecos dataImportacao(Date dataImportacao) {
        this.dataImportacao = dataImportacao;
        return this;
    }

    public BasePrecos url(String url) {
        this.url = url;
        return this;
    }
      
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idBasePrecos == null) ? 0 : idBasePrecos.hashCode());
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
        BasePrecos other = (BasePrecos) obj;
        if (idBasePrecos == null) {
            if (other.idBasePrecos != null)
                return false;
        } else if (!idBasePrecos.equals(other.idBasePrecos))
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
    public int compareTo(BasePrecos o) {
        return this.idBasePrecos.compareTo(o.getIdBasePrecos());
    }
}
