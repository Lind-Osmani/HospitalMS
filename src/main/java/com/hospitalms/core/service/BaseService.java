package com.hospitalms.core.service;

import java.util.List;

public interface BaseService<T, ID> {

    T create(T object);

    T update(ID id, T object);

    T findById(ID id);

    List<T> findAll();

    void deleteById(ID id);
}