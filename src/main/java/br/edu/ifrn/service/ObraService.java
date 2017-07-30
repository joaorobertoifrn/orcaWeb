package br.edu.ifrn.service;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.infra.model.SortOrder;
import br.edu.ifrn.model.Obra;
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
public class ObraService implements Serializable {

    @Inject
    List<Obra> todasComposicoes;

    public List<Obra> porNomeObra(String nomeObra) {
        return todasComposicoes.stream()
                .filter(c -> c.getNomeObra().equalsIgnoreCase(nomeObra))
                .collect(Collectors.toList());

    }

    public List<Obra> paginacao(Filtro<Obra> filtro) {
        List<Obra> paginadaObras = new ArrayList<>();
        if (has(filtro.getSortOrder()) && !SortOrder.UNSORTED.equals(filtro.getSortOrder())) {
            paginadaObras = todasComposicoes.stream().
                    sorted((c1, c2) -> {
                        if (filtro.getSortOrder().isAscending()) {
                            return c1.getIdObra().compareTo(c2.getIdObra());
                        } else {
                            return c2.getIdObra().compareTo(c1.getIdObra());
                        }
                    })
                    .collect(Collectors.toList());
        }

        int page = filtro.getPrimeiro() + filtro.getTamanhoPagina();
        if (filtro.getParametros().isEmpty()) {
            paginadaObras = paginadaObras.subList(filtro.getPrimeiro(), page > todasComposicoes.size() ? todasComposicoes.size() : page);
            return paginadaObras;
        }

        List<Predicate<Obra>> predicates = configuraFiltro(filtro);

        List<Obra> pagedList = todasComposicoes.stream().filter(predicates
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
                            return c1.getIdObra().compareTo(c2.getIdObra());
                        } else {
                            return c2.getIdObra().compareTo(c1.getIdObra());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Obra>> configuraFiltro(Filtro<Obra> filtro) {
        
        List<Predicate<Obra>> predicates = new ArrayList<>();
        
        if (filtro.temParamentro("idObra")) {
            Predicate<Obra> idPredicate = (Obra c) -> c.getIdObra().equals(filtro.getParametro("idObra"));
            predicates.add(idPredicate);
        }

        if (filtro.temParamentro("minObra") && filtro.temParamentro("maxObra")) {
            Predicate<Obra> minMaxObraPredicate = (Obra c) -> c.getIdObra()
                    >= Double.valueOf((String) filtro.getParametro("minObra")) && c.getIdObra()
                    <= Double.valueOf((String) filtro.getParametro("maxObra"));
            predicates.add(minMaxObraPredicate);
        } else if (filtro.temParamentro("minObra")) {
            Predicate<Obra> minObraPredicate = (Obra c) -> c.getIdObra()
                    >= Double.valueOf((String) filtro.getParametro("minObra"));
            predicates.add(minObraPredicate);
        } else if (filtro.temParamentro("maxObra")) {
            Predicate<Obra> maxObraPredicate = (Obra c) -> c.getIdObra()
                    <= Double.valueOf((String) filtro.getParametro("maxObra"));
            predicates.add(maxObraPredicate);
        }

        if (has(filtro.getEntidade())) {
            Obra filterEntity = filtro.getEntidade();
            if (has(filterEntity.getNomeObra())) {
                Predicate<Obra> nomeObraPredicate = (Obra c) -> c.getNomeObra().toLowerCase().contains(filterEntity.getNomeObra().toLowerCase());
                predicates.add(nomeObraPredicate);
            }           
        }
        return predicates;
    }

    public List<String> getNomeObra(String query) {
        return todasComposicoes.stream().filter(c -> c.getNomeObra()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Obra::getNomeObra)
                .collect(Collectors.toList());
    }

    public void inserir(Obra obra) {
        validar(obra);
        obra.setIdObra(todasComposicoes.stream()
                .mapToInt(c -> c.getIdObra())
                .max()
                .getAsInt() + 1);
        todasComposicoes.add(obra);
    }

    public void validar(Obra obra) {
        BusinessException be = new BusinessException();
        if (!obra.temNomeObra()) {
            be.addException(new BusinessException("O nome da Obra não pode estar vazio"));
        }

        if (!has(obra.getBDI())) {
            be.addException(new BusinessException("BDI não pode estar vazio"));
        }
        
        if (todasComposicoes.stream()
                .filter(c -> c.getNomeObra().equalsIgnoreCase(obra.getNomeObra())
                && c.getIdObra() != c.getIdObra()).count() > 0) {
            be.addException(new BusinessException("Nome da Obra tem que ser único"));
        }
        
        if (has(be.getExceptionList())) {
            throw be;
        }
    }

    public void remover(Obra obra) {
        todasComposicoes.remove(obra);
    }

    public long count(Filtro<Obra> filter) {
        return todasComposicoes.stream()
                .filter(configuraFiltro(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Obra encontrarObraId(Integer id) {
        return todasComposicoes.stream()
                .filter(c -> c.getIdObra().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Obra não encontrado com o código " + id));
    }

    public void atualizar(Obra obra) {
        validar(obra);
        todasComposicoes.remove(todasComposicoes.indexOf(obra));
        todasComposicoes.add(obra);
    }
}
