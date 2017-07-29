package br.edu.ifrn.service;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.infra.model.SortOrder;
import br.edu.ifrn.model.Orcamento;
import com.github.adminfaces.template.exception.BusinessException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.adminfaces.template.util.Assert.has;

@Stateless
public class OrcamentoService implements Serializable {

    @Inject
    List<Orcamento> todosOrcamentos;

    public List<Orcamento> poDescricao(String descricao) {
        return todosOrcamentos.stream()
                .filter(c -> c.getDescricao().equalsIgnoreCase(descricao))
                .collect(Collectors.toList());

    }

    public List<Orcamento> paginacao(Filtro<Orcamento> filtro) {
        List<Orcamento> paginadaOrcamentos = new ArrayList<>();
        if (has(filtro.getSortOrder()) && !SortOrder.UNSORTED.equals(filtro.getSortOrder())) {
            paginadaOrcamentos = todosOrcamentos.stream().
                    sorted((c1, c2) -> {
                        if (filtro.getSortOrder().isAscending()) {
                            return c1.getIdOrcamento().compareTo(c2.getIdOrcamento());
                        } else {
                            return c2.getIdOrcamento().compareTo(c1.getIdOrcamento());
                        }
                    })
                    .collect(Collectors.toList());
        }

        int page = filtro.getPrimeiro() + filtro.getTamanhoPagina();
        if (filtro.getParametros().isEmpty()) {
            paginadaOrcamentos = paginadaOrcamentos.subList(filtro.getPrimeiro(), page > todosOrcamentos.size() ? todosOrcamentos.size() : page);
            return paginadaOrcamentos;
        }

        List<Predicate<Orcamento>> predicates = configuraFiltro(filtro);

        List<Orcamento> pagedList = todosOrcamentos.stream().filter(predicates
                .stream().reduce(Predicate::or).orElse(t -> true))
                .collect(Collectors.toList());

        if (page < pagedList.size()) {
            pagedList = pagedList.subList(filtro.getPrimeiro(), page);
        }

        if (has(filtro.getSortField())) {
            pagedList = pagedList.stream().
                    sorted((c1, c2) -> {
                        boolean asc = SortOrder.ASCENDING.equals(filtro.getSortOrder());
                        if (asc) {
                            return c1.getIdOrcamento().compareTo(c2.getIdOrcamento());
                        } else {
                            return c2.getIdOrcamento().compareTo(c1.getIdOrcamento());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Orcamento>> configuraFiltro(Filtro<Orcamento> filtro) {
        
        List<Predicate<Orcamento>> predicates = new ArrayList<>();
        
        if (filtro.temParamentro("idOrcamento")) {
            Predicate<Orcamento> idPredicate = (Orcamento c) -> c.getIdOrcamento().equals(filtro.getParametro("idOrcamento"));
            predicates.add(idPredicate);
        }

//        if (filtro.temParamentro("minTotal") && filtro.temParamentro("maxTotal")) {
//            Predicate<Orcamento> minMaxOrcamentoPredicate = (Orcamento c) -> c.getIdOrcamento()
//                    >= Double.valueOf((String) filtro.getParametro("minTotal")) && c.getIdOrcamento()
//                    <= Double.valueOf((String) filtro.getParametro("maxTotal"));
//            predicates.add(minMaxOrcamentoPredicate);
//        } else if (filtro.temParamentro("minTotal")) {
//            Predicate<Orcamento> minOrcamentoPredicate = (Orcamento c) -> c.getIdOrcamento()
//                    >= Double.valueOf((String) filtro.getParametro("minTotal"));
//            predicates.add(minOrcamentoPredicate);
//        } else if (filtro.temParamentro("maxTotal")) {
//            Predicate<Orcamento> maxOrcamentoPredicate = (Orcamento c) -> c.getIdOrcamento()
//                    <= Double.valueOf((String) filtro.getParametro("maxTotal"));
//            predicates.add(maxOrcamentoPredicate);
//        }

        if (has(filtro.getEntidade())) {
            Orcamento filtrorEntidade = filtro.getEntidade();
            if (has(filtrorEntidade.getDescricao())) {
                Predicate<Orcamento> orcaPredicate = (Orcamento c) 
                        -> c.getDescricao()
                                .toLowerCase()
                                .contains(filtrorEntidade.getDescricao().toLowerCase());
                predicates.add(orcaPredicate);
            }           
        }
        
        return predicates;
    }

    public List<String> getDescricao(String query) {
        return todosOrcamentos.stream().filter(c -> c.getDescricao()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Orcamento::getDescricao)
                .collect(Collectors.toList());
    }

    public void inserir(Orcamento orcamento) {
        validar(orcamento);
        orcamento.setIdOrcamento(todosOrcamentos.stream()
                .mapToInt(c -> c.getIdOrcamento())
                .max()
                .getAsInt() + 1);
        todosOrcamentos.add(orcamento);
    }

    public void validar(Orcamento orcamento) {
        BusinessException be = new BusinessException();
        if (!orcamento.temDescricao()) {
            be.addException(new BusinessException("O nome do Orçamento não pode estar vazio"));
        }

//        if (!has(orcamento.getBDI())) {
//            be.addException(new BusinessException("BDI não pode estar vazio"));
//        }
        
        if (todosOrcamentos.stream()
                .filter(c -> c.getDescricao().equalsIgnoreCase(orcamento.getDescricao())
                && c.getIdOrcamento() != c.getIdOrcamento()).count() > 0) {
            be.addException(new BusinessException("Descrição tem que ser única"));
        }
        
        if (has(be.getExceptionList())) {
            throw be;
        }
    }

    public void remover(Orcamento orcamento) {
        todosOrcamentos.remove(orcamento);
    }

    public long count(Filtro<Orcamento> filter) {
        return todosOrcamentos.stream()
                .filter(configuraFiltro(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Orcamento encontrarOrcamentoId(Integer id) {
        return todosOrcamentos.stream()
                .filter(c -> c.getIdOrcamento().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Orçamento não encontrado: " + id));
    }

    public void atualizar(Orcamento orcamento) {
        validar(orcamento);
        todosOrcamentos.remove(todosOrcamentos.indexOf(orcamento));
        todosOrcamentos.add(orcamento);
    }
}
