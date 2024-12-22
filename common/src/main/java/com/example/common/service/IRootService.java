package com.example.common.service;

public interface IRootService<T> {
    T create(T entity);
    T retrieve(Integer id);
    void update(T entity);
    void delete(Integer id);
}
