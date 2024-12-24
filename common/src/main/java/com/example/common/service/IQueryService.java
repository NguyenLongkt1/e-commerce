package com.example.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IQueryService<T>{
    Page<T> getPaginationItem(Pageable pageable,T searchModel);
}
