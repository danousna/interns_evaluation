package com.sr03.dao;

public class DAOConfigurationException extends RuntimeException {
    /*
     * Constructors
     */
    public DAOConfigurationException(String msg) {
        super(msg);
    }

    public DAOConfigurationException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public DAOConfigurationException(Throwable ex) {
        super(ex);
    }
}

