package adminfaces.bean;

import adminfaces.infra.model.Filtro;
import adminfaces.model.Insumos;
import adminfaces.service.InsumosService;
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

import static adminfaces.util.Utils.adicionaMensagem;

@Named
@ViewScoped
public class InsumoListaMB implements Serializable {

    @Inject
    InsumosService insumosService;

    Integer id;

    LazyDataModel<Insumos> insumo;

    Filtro<Insumos> filtro = new Filtro<>(new Insumos());

    List<Insumos> selecaoInsumos; // insumos selecionados na coluna do check-box  

    List<Insumos> valorFiltrado;  // datatable valorFiltrado attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        insumo = new LazyDataModel<Insumos>() {
            @Override
            public List<Insumos> load(int first, int pageSize,
                                  String sortField, SortOrder sortOrder,
                                  Map<String, Object> filters) {
                adminfaces.infra.model.SortOrder order = null;
                if (sortOrder != null) {
                    order = sortOrder.equals(SortOrder.ASCENDING) ? adminfaces.infra.model.SortOrder.ASCENDING
                            : sortOrder.equals(SortOrder.DESCENDING) ? adminfaces.infra.model.SortOrder.DESCENDING
                            : adminfaces.infra.model.SortOrder.UNSORTED;
                }
                filtro.setFirst(first).setPageSize(pageSize)
                        .setSortField(sortField).setSortOrder(order)
                        .setParams(filters);
                List<Insumos> list = insumosService.paginate(filtro);
                setRowCount((int) insumosService.count(filtro));
                return list;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Insumos getRowData(String key) {
                return insumosService.findById(new Integer(key));
            }
        };
    }

    public void clear() {
        filtro = new Filtro<Insumos>(new Insumos());
    }

    public List<String> completeModel(String query) {
        List<String> result = insumosService.getDescricao(query);
        return result;
    }

    public void findById(Integer id) {
        if (id == null) {
            throw new BusinessException("Informe Insumo ID para carregar");
        }
        selecaoInsumos.add(insumosService.findById(id));
    }

    public void delete() {
        int numInsumo = 0;
        for (Insumos insumoSelecionado : selecaoInsumos) {
            numInsumo++;
            insumosService.remover(insumoSelecionado);
        }
        selecaoInsumos.clear();
        adicionaMensagem(numInsumo + " Insumo apagado com sucesso!");
    }

    public List<Insumos> getSelecaoInsumos() {
        return selecaoInsumos;
    }

    public List<Insumos> getValorFiltrado() {
        return valorFiltrado;
    }

    public void setValorFiltrado(List<Insumos> valorFiltrado) {
        this.valorFiltrado = valorFiltrado;
    }

    public void setSelecaoInsumos(List<Insumos> selecaoInsumos) {
        this.selecaoInsumos = selecaoInsumos;
    }

    public LazyDataModel<Insumos> getInsumo() {
        return insumo;
    }

    public void setInsumo(LazyDataModel<Insumos> insumo) {
        this.insumo = insumo;
    }

    public Filtro<Insumos> getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro<Insumos> filtro) {
        this.filtro = filtro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
