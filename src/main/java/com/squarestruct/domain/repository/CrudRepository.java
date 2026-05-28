package com.squarestruct.domain.repository;

import java.util.List;
import java.util.Optional;

/*
 * Contrato base para repositorios de dominio.
 * Define operaciones CRUD comunes sin acoplarse a una tecnología de persistencia concreta.
 */
public interface CrudRepository<T, ID> {

    T create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    T update(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}
