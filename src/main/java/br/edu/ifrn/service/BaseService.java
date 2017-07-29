package br.edu.ifrn.service;

import br.edu.ifrn.infra.model.Filtro;
import br.edu.ifrn.infra.model.SortOrder;
import br.edu.ifrn.model.Base;
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
public class BaseService implements Serializable {

    @Inject
    List<Base> todasBases;

    public List<Base> porMesAno(String mesAno) {
        return todasBases.stream()
                .filter(c -> c.getMesAno().equalsIgnoreCase(mesAno))
                .collect(Collectors.toList());

    }

    public List<Base> paginacao(Filtro<Base> filtro) {
        List<Base> paginadaBases = new ArrayList<>();
        if (has(filtro.getSortOrder()) && !SortOrder.UNSORTED.equals(filtro.getSortOrder())) {
            paginadaBases = todasBases.stream().
                    sorted((c1, c2) -> {
                        if (filtro.getSortOrder().isAscending()) {
                            return c1.getIdBase().compareTo(c2.getIdBase());
                        } else {
                            return c2.getIdBase().compareTo(c1.getIdBase());
                        }
                    })
                    .collect(Collectors.toList());
        }

        int page = filtro.getPrimeiro() + filtro.getTamanhoPagina();
        if (filtro.getParametros().isEmpty()) {
            paginadaBases = paginadaBases.subList(filtro.getPrimeiro(), page > todasBases.size() ? todasBases.size() : page);
            return paginadaBases;
        }

        List<Predicate<Base>> predicates = configuraFiltro(filtro);

        List<Base> pagedList = todasBases.stream().filter(predicates
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
                            return c1.getIdBase().compareTo(c2.getIdBase());
                        } else {
                            return c2.getIdBase().compareTo(c1.getIdBase());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Base>> configuraFiltro(Filtro<Base> filtro) {
        
        List<Predicate<Base>> predicates = new ArrayList<>();
        
        if (filtro.temParamentro("idBase")) {
            Predicate<Base> idPredicate = (Base c) -> c.getIdBase().equals(filtro.getParametro("idBase"));
            predicates.add(idPredicate);
        }
/*
        if (filtro.temParamentro("minBase") && filtro.temParamentro("maxBase")) {
            Predicate<Base> minMaxBasePredicate = (Base c) -> c.getIdBase()
                    >= Double.valueOf((String) filtro.getParametro("minBase")) && c.getIdBase()
                    <= Double.valueOf((String) filtro.getParametro("maxBase"));
            predicates.add(minMaxBasePredicate);
        } else if (filtro.temParamentro("minBase")) {
            Predicate<Base> minBasePredicate = (Base c) -> c.getIdBase()
                    >= Double.valueOf((String) filtro.getParametro("minBase"));
            predicates.add(minBasePredicate);
        } else if (filtro.temParamentro("maxBase")) {
            Predicate<Base> maxBasePredicate = (Base c) -> c.getIdBase()
                    <= Double.valueOf((String) filtro.getParametro("maxBase"));
            predicates.add(maxBasePredicate);
        }
*/

        if (has(filtro.getEntidade())) {
            Base filterEntity = filtro.getEntidade();
            if (has(filterEntity.getMesAno())) {
                Predicate<Base> nomeBasePredicate = (Base c) -> c.getMesAno().toLowerCase().contains(filterEntity.getMesAno().toLowerCase());
                predicates.add(nomeBasePredicate);
            }           
        }
        return predicates;
    }

    public List<String> getMesAno(String query) {
        return todasBases.stream().filter(c -> c.getMesAno()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Base::getMesAno)
                .collect(Collectors.toList());
    }

    public void inserir(Base base) {
        validar(base);
        base.setIdBase(todasBases.stream()
                .mapToInt(c -> c.getIdBase())
                .max()
                .getAsInt() + 1);
        todasBases.add(base);
    }

    public void validar(Base base) {
        BusinessException be = new BusinessException();
        if (!base.temMesAno()) {
            be.addException(new BusinessException("MES/ANO não pode estar vazio"));
        }

        if (!has(base.getUrl())) {
            be.addException(new BusinessException("URL não pode estar vazia"));
        }
        
        if (todasBases.stream()
                .filter(c -> c.getMesAno().equalsIgnoreCase(base.getMesAno())
                && c.getIdBase() != c.getIdBase()).count() > 0) {
            be.addException(new BusinessException("MES/ANO já incluido"));
        }
        
        if (has(be.getExceptionList())) {
            throw be;
        }
    }

    public void remover(Base base) {
        todasBases.remove(base);
    }

    public long count(Filtro<Base> filter) {
        return todasBases.stream()
                .filter(configuraFiltro(filter)
                .stream()
                .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Base encontrarBaseId(Integer id) {
        return todasBases.stream()
                .filter(c -> c
                .getIdBase()
                .equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Base de Preços não encontrado com o código " + id));
    }

    public void atualizar(Base base) {
        validar(base);
        todasBases.remove(todasBases.indexOf(base));
        todasBases.add(base);
    }
}
