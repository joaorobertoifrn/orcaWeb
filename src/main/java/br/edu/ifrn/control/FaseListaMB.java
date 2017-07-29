package br.edu.ifrn.control;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.model.Fase;
import br.edu.ifrn.service.FaseService;
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
public class FaseListaMB implements Serializable {

    @Inject
    FaseService faseService;

    Integer id;

    LazyDataModel<Fase> fase;

    Filtro<Fase> filtro = new Filtro<>(new Fase());

    List<Fase> selecaoFase; // fases selecionados na coluna do check-box  

    List<Fase> valorFiltrado;  // datatable valorFiltrado attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        fase = new LazyDataModel<Fase>() {
            @Override
            public List<Fase> load(int primeiro, int tamanhoPagina,
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
                List<Fase> lista = faseService.paginacao(filtro);
                setRowCount((int) faseService.count(filtro));
                return lista;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Fase getRowData(String key) {
                return faseService.encontrarFaseId(new Integer(key));
            }
        };
    }

    public void clear() {
        filtro = new Filtro<Fase>(new Fase());
    }

    public List<String> completeDescricao(String query) {
        List<String> resultado = faseService.getDescricao(query);
        return resultado;
    }

    public void encontrarId(Integer id) {
        if (id == null) {
            throw new BusinessException("Informe código da fase");
        }
        selecaoFase.add(faseService.encontrarFaseId(id));
    }

    public void delete() {
        int numFase = 0;
        for (Fase faseSelecionada : selecaoFase) {
            numFase++;
            faseService.remover(faseSelecionada);
        }
        selecaoFase.clear();
        
        adicionaMensagem(numFase + " Fase apagada com sucesso!");
    }

    public List<Fase> getSelecaoFase() {
        return selecaoFase;
    }

    public List<Fase> getValorFiltrado() {
        return valorFiltrado;
    }

    public void setValorFiltrado(List<Fase> valorFiltrado) {
        this.valorFiltrado = valorFiltrado;
    }

    public void setSelecaoFase(List<Fase> selecaoFase) {
        this.selecaoFase = selecaoFase;
    }

    public LazyDataModel<Fase> getFase() {
        return fase;
    }

    public void setFase(LazyDataModel<Fase> fase) {
        this.fase = fase;
    }

    public Filtro<Fase> getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro<Fase> filtro) {
        this.filtro = filtro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
