package br.edu.ifrn.service;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.infra.model.SortOrder;
import br.edu.ifrn.model.Composicao;
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
public class ComposicaoService implements Serializable {

    @Inject
    List<Composicao> todasComposicoes;

    public List<Composicao> porDescricao(String descricao) {
        return todasComposicoes.stream()
                .filter(c -> c.getDescricao().equalsIgnoreCase(descricao))
                .collect(Collectors.toList());

    }

    public List<Composicao> paginacao(Filtro<Composicao> filtro) {
        List<Composicao> ComposicoesPaginadas = new ArrayList<>();
        if (has(filtro.getSortOrder()) && !SortOrder.UNSORTED.equals(filtro.getSortOrder())) {
            ComposicoesPaginadas = todasComposicoes.stream().
                    sorted((c1, c2) -> {
                        if (filtro.getSortOrder().isAscending()) {
                            return c1.getIdComposicao().compareTo(c2.getIdComposicao());
                        } else {
                            return c2.getIdComposicao().compareTo(c1.getIdComposicao());
                        }
                    })
                    .collect(Collectors.toList());
        }

        int page = filtro.getPrimeiro() + filtro.getTamanhoPagina();
        if (filtro.getParametros().isEmpty()) {
            ComposicoesPaginadas = ComposicoesPaginadas.subList(filtro.getPrimeiro(), page > todasComposicoes.size() ? todasComposicoes.size() : page);
            return ComposicoesPaginadas;
        }

        List<Predicate<Composicao>> predicates = configuraFiltro(filtro);

        List<Composicao> pagedList = todasComposicoes.stream().filter(predicates
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
                            return c1.getIdComposicao().compareTo(c2.getIdComposicao());
                        } else {
                            return c2.getIdComposicao().compareTo(c1.getIdComposicao());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Composicao>> configuraFiltro(Filtro<Composicao> filtro) {
        
        List<Predicate<Composicao>> predicates = new ArrayList<>();
        
        if (filtro.temParamentro("idComposicao")) {
            Predicate<Composicao> idPredicate = (Composicao c) -> c.getIdComposicao().equals(filtro.getParametro("idComposicao"));
            predicates.add(idPredicate);
        }

        if (filtro.temParamentro("minComposicao") && filtro.temParamentro("maxComposicao")) {
            Predicate<Composicao> minMaxComposicaoPredicate = (Composicao c) -> c.getIdComposicao()
                    >= Double.valueOf((String) filtro.getParametro("minComposicao")) && c.getIdComposicao()
                    <= Double.valueOf((String) filtro.getParametro("maxComposicao"));
            predicates.add(minMaxComposicaoPredicate);
        } else if (filtro.temParamentro("minComposicao")) {
            Predicate<Composicao> minComposicaoPredicate = (Composicao c) -> c.getIdComposicao()
                    >= Double.valueOf((String) filtro.getParametro("minComposicao"));
            predicates.add(minComposicaoPredicate);
        } else if (filtro.temParamentro("maxComposicao")) {
            Predicate<Composicao> maxComposicaoPredicate = (Composicao c) -> c.getIdComposicao()
                    <= Double.valueOf((String) filtro.getParametro("maxComposicao"));
            predicates.add(maxComposicaoPredicate);
        }

        if (has(filtro.getEntidade())) {
            Composicao filterEntity = filtro.getEntidade();
            if (has(filterEntity.getDescricao())) {
                Predicate<Composicao> nomeComposicaoPredicate = (Composicao c) -> c.getDescricao().toLowerCase().contains(filterEntity.getDescricao().toLowerCase());
                predicates.add(nomeComposicaoPredicate);
            }           
        }
        return predicates;
    }

    public List<String> getNomeComposicao(String query) {
        return todasComposicoes.stream().filter(c -> c.getDescricao()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Composicao::getDescricao)
                .collect(Collectors.toList());
    }

    public void inserir(Composicao obra) {
        validar(obra);
        obra.setIdComposicao(todasComposicoes.stream()
                .mapToInt(c -> c.getIdComposicao())
                .max()
                .getAsInt() + 1);
        todasComposicoes.add(obra);
    }

    public void validar(Composicao obra) {
        BusinessException be = new BusinessException();
        if (!obra.temDescricao()) {
            be.addException(new BusinessException("O nome da Composicao não pode estar vazio"));
        }

//        if (!has(obra.getBDI())) {
//            be.addException(new BusinessException("BDI não pode estar vazio"));
//        }
        
        if (todasComposicoes.stream()
                .filter(c -> c.getDescricao().equalsIgnoreCase(obra.getDescricao())
                && c.getIdComposicao() != c.getIdComposicao()).count() > 0) {
            be.addException(new BusinessException("Nome da Composicao tem que ser único"));
        }
        
        if (has(be.getExceptionList())) {
            throw be;
        }
    }

    public void remover(Composicao obra) {
        todasComposicoes.remove(obra);
    }

    public long count(Filtro<Composicao> filter) {
        return todasComposicoes.stream()
                .filter(configuraFiltro(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Composicao encontrarComposicaoId(Integer id) {
        return todasComposicoes.stream()
                .filter(c -> c.getIdComposicao().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Composicao não encontrado com o código " + id));
    }

    public void atualizar(Composicao obra) {
        validar(obra);
        todasComposicoes.remove(todasComposicoes.indexOf(obra));
        todasComposicoes.add(obra);
    }
}
