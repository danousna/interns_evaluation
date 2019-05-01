package com.sr03.dao;

import src.com.sr03.beans.UserModel;

public interface UserDao {
    void create(UserModel user) throws DAOException;
    UserModel find(String email) throws DAOException;
}

