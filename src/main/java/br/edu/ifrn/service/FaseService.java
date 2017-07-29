package br.edu.ifrn.service;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.infra.model.SortOrder;
import br.edu.ifrn.model.Fase;
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
public class FaseService implements Serializable {

    @Inject
    List<Fase> todasFases;

    public List<Fase> porDescricao(String nomeFase) {
        return todasFases.stream()
                .filter(c -> c.getDescricao()
                .equalsIgnoreCase(nomeFase))
                .collect(Collectors.toList());

    }

    public List<Fase> paginacao(Filtro<Fase> filtro) {
        List<Fase> paginadaFases = new ArrayList<>();
        if (has(filtro.getSortOrder()) && !SortOrder.UNSORTED.equals(filtro.getSortOrder())) {
            paginadaFases = todasFases.stream().
                    sorted((c1, c2) -> {
                        if (filtro.getSortOrder().isAscending()) {
                            return c1.getIdFase().compareTo(c2.getIdFase());
                        } else {
                            return c2.getIdFase().compareTo(c1.getIdFase());
                        }
                    })
                    .collect(Collectors.toList());
        }

        int page = filtro.getPrimeiro() + filtro.getTamanhoPagina();
        if (filtro.getParametros().isEmpty()) {
            paginadaFases = paginadaFases.subList(filtro.getPrimeiro(), page > todasFases.size() ? todasFases.size() : page);
            return paginadaFases;
        }

        List<Predicate<Fase>> predicates = configuraFiltro(filtro);

        List<Fase> pagedList = todasFases.stream().filter(predicates
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
                            return c1.getIdFase().compareTo(c2.getIdFase());
                        } else {
                            return c2.getIdFase().compareTo(c1.getIdFase());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Fase>> configuraFiltro(Filtro<Fase> filtro) {
        
        List<Predicate<Fase>> predicates = new ArrayList<>();
        
        if (filtro.temParamentro("idFase")) {
            Predicate<Fase> idPredicate = (Fase c) -> c.getIdFase().equals(filtro.getParametro("idFase"));
            predicates.add(idPredicate);
        }

//        if (filtro.temParamentro("minFase") && filtro.temParamentro("maxFase")) {
//            Predicate<Fase> minMaxFasePredicate = (Fase c) -> c.getIdFase()
//                    >= Double.valueOf((String) filtro.getParametro("minFase")) && c.getIdFase()
//                    <= Double.valueOf((String) filtro.getParametro("maxFase"));
//            predicates.add(minMaxFasePredicate);
//        } else if (filtro.temParamentro("minFase")) {
//            Predicate<Fase> minFasePredicate = (Fase c) -> c.getIdFase()
//                    >= Double.valueOf((String) filtro.getParametro("minFase"));
//            predicates.add(minFasePredicate);
//        } else if (filtro.temParamentro("maxFase")) {
//            Predicate<Fase> maxFasePredicate = (Fase c) -> c.getIdFase()
//                    <= Double.valueOf((String) filtro.getParametro("maxFase"));
//            predicates.add(maxFasePredicate);
//        }

        if (has(filtro.getEntidade())) {
            Fase filtroEntidade = filtro.getEntidade();
            if (has(filtroEntidade.getDescricao())) {
                Predicate<Fase> nomeFasePredicate = (Fase c) -> c.getDescricao().toLowerCase().contains(filtroEntidade.getDescricao().toLowerCase());
                predicates.add(nomeFasePredicate);
            }           
        }
        return predicates;
    }

    public List<String> getDescricao(String query) {
        return todasFases.stream().filter(c -> c.getDescricao()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Fase::getDescricao)
                .collect(Collectors.toList());
    }

    public void inserir(Fase fase) {
        validar(fase);
        fase.setIdFase(todasFases.stream()
                .mapToInt(c -> c.getIdFase())
                .max()
                .getAsInt() + 1);
        todasFases.add(fase);
    }

    public void validar(Fase fase) {
        BusinessException be = new BusinessException();
        if (!fase.temItem()) {
            be.addException(new BusinessException("O item não pode estar vazio"));
        }
        if (todasFases.stream()
                .filter(c -> c.getDescricao().equalsIgnoreCase(fase.getDescricao())
                && c.getIdFase() != c.getIdFase()).count() > 0) {
            be.addException(new BusinessException("A descrição da Fase tem que ser única"));
        }
        if (has(be.getExceptionList())) {
            throw be;
        }
    }

    public void remover(Fase fase) {
        todasFases.remove(fase);
    }

    public long count(Filtro<Fase> filter) {
        return todasFases.stream()
                .filter(configuraFiltro(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Fase encontrarFaseId(Integer id) {
        return todasFases.stream()
                .filter(c -> c.getIdFase().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Fase não encontrado com o código " + id));
    }

    public void atualizar(Fase fase) {
        validar(fase);
        todasFases.remove(todasFases.indexOf(fase));
        todasFases.add(fase);
    }
}
