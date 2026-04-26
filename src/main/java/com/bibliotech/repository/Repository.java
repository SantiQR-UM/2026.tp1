package com.bibliotech.repository;

// src/com/bibliotech/repository/Repository.java
public interface Repository<T, ID> {
    void guardar(T entidad);
    Optional<T> buscarPorId(ID id);
    List<T> buscarTodos();
}