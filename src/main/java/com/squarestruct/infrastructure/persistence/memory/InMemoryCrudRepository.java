package com.squarestruct.infrastructure.persistence.memory;

import com.squarestruct.domain.repository.CrudRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * Implementación base de CRUD en memoria.
 * Usa un mapa Java como almacenamiento temporal y no depende de MySQL ni JDBC.
 */
abstract class InMemoryCrudRepository<T> implements CrudRepository<T, Long> {

    private final Map<Long, T> storage = new LinkedHashMap<>();
    private long nextId = 1L;

    protected InMemoryCrudRepository(Map<Long, T> initialData) {
        if (initialData != null) {
            /*
             * Los datos semilla pueden traer IDs propios. Se respetan y se avanza la secuencia
             * interna para que las nuevas entidades no colisionen con ellos.
             */
            for (Map.Entry<Long, T> entry : initialData.entrySet()) {
                Long id = Objects.requireNonNull(entry.getKey(), "El id inicial no puede ser null");
                T entity = Objects.requireNonNull(entry.getValue(), "La entidad inicial no puede ser null");

                storage.put(id, entity);
                nextId = Math.max(nextId, id + 1);
            }
        }
    }

    @Override
    public T create(T entity) {
        Objects.requireNonNull(entity, "La entidad no puede ser null");

        Long id = getId(entity);
        if (id == null) {
            /*
             * En memoria se simula el autoincremento de una base de datos asignando IDs
             * cuando la entidad llega sin identificador.
             */
            id = nextId++;
            setId(entity, id);
        } else {
            if (storage.containsKey(id)) {
                throw new IllegalArgumentException("Ya existe una entidad con id " + id);
            }
            nextId = Math.max(nextId, id + 1);
        }

        storage.put(id, entity);
        return entity;
    }

    @Override
    public Optional<T> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity, "La entidad no puede ser null");

        Long id = getId(entity);
        if (id == null) {
            throw new IllegalArgumentException("No se puede actualizar una entidad sin id");
        }

        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("No existe una entidad con id " + id);
        }

        storage.put(id, entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            storage.remove(id);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return id != null && storage.containsKey(id);
    }

    protected Stream<T> stream() {
        return storage.values().stream();
    }

    protected boolean containsIgnoreCase(String value, String query) {
        if (value == null || query == null) {
            return false;
        }

        return value.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT));
    }

    protected boolean equalsIgnoreCase(String value, String query) {
        if (value == null || query == null) {
            return false;
        }

        return value.equalsIgnoreCase(query);
    }

    protected boolean isBetweenInclusive(LocalDate value, LocalDate start, LocalDate end) {
        if (value == null) {
            return false;
        }

        /*
         * Un límite nulo se interpreta como rango abierto para reutilizar la misma ayuda
         * en búsquedas por fecha con inicio, fin o ambos valores.
         */
        boolean afterStart = start == null || !value.isBefore(start);
        boolean beforeEnd = end == null || !value.isAfter(end);
        return afterStart && beforeEnd;
    }

    protected abstract Long getId(T entity);

    protected abstract void setId(T entity, Long id);
}
