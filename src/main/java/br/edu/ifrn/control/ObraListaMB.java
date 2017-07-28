package br.edu.ifrn.control;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.model.Obra;
import br.edu.ifrn.service.ObraService;
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
public class ObraListaMB implements Serializable {

    @Inject
    ObraService obraService;

    Integer id;

    LazyDataModel<Obra> obra;

    Filtro<Obra> filtro = new Filtro<>(new Obra());

    List<Obra> selecaoObra; // obras selecionados na coluna do check-box  

    List<Obra> valorFiltrado;  // datatable valorFiltrado attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        obra = new LazyDataModel<Obra>() {
            @Override
            public List<Obra> load(int primeiro, int tamanhoPagina,
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
                List<Obra> lista = obraService.paginacao(filtro);
                setRowCount((int) obraService.count(filtro));
                return lista;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Obra getRowData(String key) {
                return obraService.encontarId(new Integer(key));
            }
        };
    }

    public void clear() {
        filtro = new Filtro<Obra>(new Obra());
    }

    public List<String> completeNomeObra(String query) {
        List<String> result = obraService.getNomeObra(query);
        return result;
    }

    public void encontrarId(Integer id) {
        if (id == null) {
            throw new BusinessException("Informe código da obra");
        }
        selecaoObra.add(obraService.encontarId(id));
    }

    public void delete() {
        int numObra = 0;
        for (Obra obraSelecionada : selecaoObra) {
            numObra++;
            obraService.remover(obraSelecionada);
        }
        selecaoObra.clear();
        adicionaMensagem(numObra + " Obra apagada com sucesso!");
    }

    public List<Obra> getSelecaoObra() {
        return selecaoObra;
    }

    public List<Obra> getValorFiltrado() {
        return valorFiltrado;
    }

    public void setValorFiltrado(List<Obra> valorFiltrado) {
        this.valorFiltrado = valorFiltrado;
    }

    public void setSelecaoObra(List<Obra> selecaoObra) {
        this.selecaoObra = selecaoObra;
    }

    public LazyDataModel<Obra> getObra() {
        return obra;
    }

    public void setObra(LazyDataModel<Obra> obra) {
        this.obra = obra;
    }

    public Filtro<Obra> getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro<Obra> filtro) {
        this.filtro = filtro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
