package com.example.common.service;

public interface ICommandService<T> {
    T create(T entity);
    T retrieve(Long id);
    void update(T entity);
    void delete(Long id);
}
