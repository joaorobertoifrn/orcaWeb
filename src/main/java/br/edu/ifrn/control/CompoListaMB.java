package br.edu.ifrn.control;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.model.Composicao;
import br.edu.ifrn.service.ComposicaoService;
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
public class CompoListaMB implements Serializable {

    @Inject
    ComposicaoService composicaoService;

    Integer id;

    LazyDataModel<Composicao> composicao;

    Filtro<Composicao> filtro = new Filtro<>(new Composicao());

    List<Composicao> selecaoComposicao; // composicaos selecionados na coluna do check-box  

    List<Composicao> valorFiltrado;  // datatable valorFiltrado attribute (column filters)

    @PostConstruct
    public void initDataModel() {
        composicao = new LazyDataModel<Composicao>() {
            @Override
            public List<Composicao> load(int primeiro, int tamanhoPagina,
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
                List<Composicao> lista = composicaoService.paginacao(filtro);
                setRowCount((int) composicaoService.count(filtro));
                return lista;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Composicao getRowData(String key) {
                return composicaoService.encontrarComposicaoId(new Integer(key));
            }
        };
    }

    public void clear() {
        filtro = new Filtro<Composicao>(new Composicao());
    }

    public List<String> completeNomeComposicao(String query) {
        List<String> resultado = composicaoService.getNomeComposicao(query);
        return resultado;
    }

    public void encontrarId(Integer id) {
        if (id == null) {
            throw new BusinessException("Informe código da composicao");
        }
        selecaoComposicao.add(composicaoService.encontrarComposicaoId(id));
    }

    public void delete() {
        int numComposicao = 0;
        for (Composicao composicaoSelecionada : selecaoComposicao) {
            numComposicao++;
            composicaoService.remover(composicaoSelecionada);
        }
        selecaoComposicao.clear();
        adicionaMensagem(numComposicao + " Composicao apagada com sucesso!");
    }

    public List<Composicao> getSelecaoComposicao() {
        return selecaoComposicao;
    }

    public List<Composicao> getValorFiltrado() {
        return valorFiltrado;
    }

    public void setValorFiltrado(List<Composicao> valorFiltrado) {
        this.valorFiltrado = valorFiltrado;
    }

    public void setSelecaoComposicao(List<Composicao> selecaoComposicao) {
        this.selecaoComposicao = selecaoComposicao;
    }

    public LazyDataModel<Composicao> getComposicao() {
        return composicao;
    }

    public void setComposicao(LazyDataModel<Composicao> composicao) {
        this.composicao = composicao;
    }

    public Filtro<Composicao> getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro<Composicao> filtro) {
        this.filtro = filtro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
