/*
 * Contrato base para repositorios de dominio.
 * Define operaciones CRUD comunes sin acoplarse a una tecnologia de persistencia concreta.
 */
package com.squarestruct.domain.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    T create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    T update(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}
