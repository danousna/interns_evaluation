package com.sr03.dao;

public interface DAO<T> {
    void create(T t) throws DAOException;
    T get(Long id) throws DAOException;
    void update (T t) throws DAOException;
    void delete(T t) throws  DAOException;
}

