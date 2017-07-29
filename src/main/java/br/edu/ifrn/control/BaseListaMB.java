package br.edu.ifrn.control;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.model.Base;
import br.edu.ifrn.service.BaseService;
import com.github.adminfaces.template.exception.BusinessException;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static br.edu.ifrn.util.Utils.adicionaMensagem;

@Named
@ViewScoped
public class BaseListaMB implements Serializable {

    @Inject
    BaseService baseService;

    Integer id;

    LazyDataModel<Base> base;

    Filtro<Base> filtro = new Filtro<>(new Base());

    List<Base> selecaoBase; // bases selecionados na coluna do check-box  

    List<Base> valorFiltrado;  // datatable valorFiltrado attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        base = new LazyDataModel<Base>() {
            @Override
            public List<Base> load(int primeiro, int tamanhoPagina,
                                  String sortField, SortOrder sortOrder,
                                  Map<String, Object> filters) {
                br.edu.ifrn.infra.model.SortOrder order = null;
                if (sortOrder != null) {
                    order = sortOrder.equals(SortOrder.ASCENDING) ? br.edu.ifrn.infra.model.SortOrder.ASCENDING
                            : sortOrder.equals(SortOrder.DESCENDING) ? br.edu.ifrn.infra.model.SortOrder.DESCENDING
                            : br.edu.ifrn.infra.model.SortOrder.UNSORTED;
                }
                filtro.setPrimeiro(primeiro).setTamanhoPagina(tamanhoPagina)
                        .setSortField(sortField).setSortOrder(order)
                        .setParametros(filters);
                List<Base> lista = baseService.paginacao(filtro);
                setRowCount((int) baseService.count(filtro));
                return lista;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Base getRowData(String key) {
                return baseService.encontrarBaseId(new Integer(key));
            }
        };
    }

    public void clear() {
        filtro = new Filtro<Base>(new Base());
    }

    public List<String> completeMesAno(String query) {
        List<String> resultado = baseService.getMesAno(query);
        return resultado;
    }

    public void encontrarId(Integer id) {
        if (id == null) {
            throw new BusinessException("Informe código da base");
        }
        selecaoBase.add(baseService.encontrarBaseId(id));
    }

    public void delete() {
        int numBase = 0;
        for (Base baseSelecionada : selecaoBase) {
            numBase++;
            baseService.remover(baseSelecionada);
        }
        selecaoBase.clear();
        adicionaMensagem(numBase + " Base apagada com sucesso!");
    }

    public List<Base> getSelecaoBase() {
        return selecaoBase;
    }

    public List<Base> getValorFiltrado() {
        return valorFiltrado;
    }

    public void setValorFiltrado(List<Base> valorFiltrado) {
        this.valorFiltrado = valorFiltrado;
    }

    public void setSelecaoBase(List<Base> selecaoBase) {
        this.selecaoBase = selecaoBase;
    }

    public LazyDataModel<Base> getBase() {
        return base;
    }

    public void setBase(LazyDataModel<Base> base) {
        this.base = base;
    }

    public Filtro<Base> getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro<Base> filtro) {
        this.filtro = filtro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
