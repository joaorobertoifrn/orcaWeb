package br.edu.ifrn.control;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.model.Orcamento;
import br.edu.ifrn.service.OrcamentoService;
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
public class OrcaListaMB implements Serializable {

    @Inject
    OrcamentoService orcamentoService;

    Integer id;

    LazyDataModel<Orcamento> orcamento;

    Filtro<Orcamento> filtro = new Filtro<>(new Orcamento());

    List<Orcamento> selecaoOrcamento; // orcamentos selecionados na coluna do check-box  

    List<Orcamento> valorFiltrado;   // datatable valorFiltrado attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        orcamento = new LazyDataModel<Orcamento>() {
            @Override
            public List<Orcamento> load(int primeiro, int tamanhoPagina,
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
                List<Orcamento> lista = orcamentoService.paginacao(filtro);
                setRowCount((int) orcamentoService.count(filtro));
                return lista;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Orcamento getRowData(String key) {
                return orcamentoService.encontrarOrcamentoId(new Integer(key));
            }
        };
    }

    public void clear() {
        filtro = new Filtro<Orcamento>(new Orcamento());
    }

    public List<String> completeDescricao(String query) {
        List<String> resultado = orcamentoService.getDescricao(query);
        return resultado;
    }

    public void encontrarId(Integer id) {
        if (id == null) {
            throw new BusinessException("Informe código da orcamento");
        }
        selecaoOrcamento.add(orcamentoService.encontrarOrcamentoId(id));
    }

    public void delete() {
        int numOrcamento = 0;
        for (Orcamento orcamentoSelecionado : selecaoOrcamento) {
            numOrcamento++;
            orcamentoService.remover(orcamentoSelecionado);
        }
        selecaoOrcamento.clear();
        adicionaMensagem(numOrcamento + " Orçamento apagado com sucesso!");
    }

    public List<Orcamento> getSelecaoOrcamento() {
        return selecaoOrcamento;
    }

    public List<Orcamento> getValorFiltrado() {
        return valorFiltrado;
    }

    public void setValorFiltrado(List<Orcamento> valorFiltrado) {
        this.valorFiltrado = valorFiltrado;
    }

    public void setSelecaoOrcamento(List<Orcamento> selecaoOrcamento) {
        this.selecaoOrcamento = selecaoOrcamento;
    }

    public LazyDataModel<Orcamento> getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(LazyDataModel<Orcamento> orcamento) {
        this.orcamento = orcamento;
    }

    public Filtro<Orcamento> getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro<Orcamento> filtro) {
        this.filtro = filtro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
