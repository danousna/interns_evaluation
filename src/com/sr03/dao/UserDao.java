package com.sr03.dao;

import com.sr03.beans.User;

public interface UserDao {
    void create(User user) throws DAOException;
    User find(String email) throws DAOException;
}

