package adminfaces.service;

import adminfaces.infra.model.Filtro;
import adminfaces.infra.model.SortOrder;
import adminfaces.model.Insumos;
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
public class InsumosService implements Serializable {

    @Inject
    List<Insumos> todosInsumos;

    public List<Insumos> listByDescricao(String model) {
        return todosInsumos.stream()
                .filter(c -> c.getDescricao().equalsIgnoreCase(model))
                .collect(Collectors.toList());

    }

    public List<Insumos> paginate(Filtro<Insumos> filter) {
        List<Insumos> pagedInsumos = new ArrayList<>();
        if (has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
            pagedInsumos = todosInsumos.stream().
                    sorted((c1, c2) -> {
                        if (filter.getSortOrder().isAscending()) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
        }

        int page = filter.getFirst() + filter.getPageSize();
        if (filter.getParams().isEmpty()) {
            pagedInsumos = pagedInsumos.subList(filter.getFirst(), page > todosInsumos.size() ? todosInsumos.size() : page);
            return pagedInsumos;
        }

        List<Predicate<Insumos>> predicates = configuraFiltro(filter);

        List<Insumos> pagedList = todosInsumos.stream().filter(predicates
                .stream().reduce(Predicate::or).orElse(t -> true))
                .collect(Collectors.toList());

        if (page < pagedList.size()) {
            pagedList = pagedList.subList(filter.getFirst(), page);
        }

        if (has(filter.getSortField())) {
            pagedList = pagedList.stream().
                    sorted((c1, c2) -> {
                        boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
                        if (asc) {
                            return c1.getId().compareTo(c2.getId());
                        } else {
                            return c2.getId().compareTo(c1.getId());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Insumos>> configuraFiltro(Filtro<Insumos> filter) {
        List<Predicate<Insumos>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Insumos> idPredicate = (Insumos c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (filter.hasParam("minPrice") && filter.hasParam("maxPrice")) {
            Predicate<Insumos> minMaxPricePredicate = (Insumos c) -> c.getPreco()
                    >= Double.valueOf((String) filter.getParam("minPrice")) && c.getPreco()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(minMaxPricePredicate);
        } else if (filter.hasParam("minPrice")) {
            Predicate<Insumos> minPricePredicate = (Insumos c) -> c.getPreco()
                    >= Double.valueOf((String) filter.getParam("minPrice"));
            predicates.add(minPricePredicate);
        } else if (filter.hasParam("maxPrice")) {
            Predicate<Insumos> maxPricePredicate = (Insumos c) -> c.getPreco()
                    <= Double.valueOf((String) filter.getParam("maxPrice"));
            predicates.add(maxPricePredicate);
        }

        if (has(filter.getEntity())) {
            Insumos filterEntity = filter.getEntity();
            if (has(filterEntity.getDescricao())) {
                Predicate<Insumos> modelPredicate = (Insumos c) -> c.getDescricao().toLowerCase().contains(filterEntity.getDescricao().toLowerCase());
                predicates.add(modelPredicate);
            }

            if (has(filterEntity.getPreco())) {
                Predicate<Insumos> pricePredicate = (Insumos c) -> c.getPreco().equals(filterEntity.getPreco());
                predicates.add(pricePredicate);
            }

            if (has(filterEntity.getUnidade())) {
                Predicate<Insumos> namePredicate = (Insumos c) -> c.getUnidade().toLowerCase().contains(filterEntity.getUnidade().toLowerCase());
                predicates.add(namePredicate);
            }
        }
        return predicates;
    }

    public List<String> getDescricao(String query) {
        return todosInsumos.stream().filter(c -> c.getDescricao()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Insumos::getDescricao)
                .collect(Collectors.toList());
    }

    public void inserir(Insumos insumo) {
        validar(insumo);
        insumo.setId(todosInsumos.stream()
                .mapToInt(c -> c.getId())
                .max()
                .getAsInt() + 1);
        todosInsumos.add(insumo);
    }

    public void validar(Insumos insumo) {
        BusinessException be = new BusinessException();
        if (!insumo.temDescricao()) {
            be.addException(new BusinessException("Descrição do Insumo não pode estar vazia"));
        }
        if (!insumo.temUnidade()) {
            be.addException(new BusinessException("Unidade do Insumo não pode estar vazia"));
        }

        if (!has(insumo.getPreco())) {
            be.addException(new BusinessException("Preço do insumo não pode estar vazio"));
        }

        if (todosInsumos.stream().filter(c -> c.getUnidade().equalsIgnoreCase(insumo.getUnidade())
                && c.getId() != c.getId()).count() > 0) {
            be.addException(new BusinessException("Insumo não encontrado"));
        }
        if (has(be.getExceptionList())) {
            throw be;
        }
    }

    public void remover(Insumos insumo) {
        todosInsumos.remove(insumo);
    }

    public long count(Filtro<Insumos> filter) {
        return todosInsumos.stream()
                .filter(configuraFiltro(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Insumos findById(Integer id) {
        return todosInsumos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Insumo não encontrado com o código " + id));
    }

    public void atualizar(Insumos insumo) {
        validar(insumo);
        todosInsumos.remove(todosInsumos.indexOf(insumo));
        todosInsumos.add(insumo);
    }
}
