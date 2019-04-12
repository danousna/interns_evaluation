package src.dao;

public class DAOException extends RuntimeException {
    /*
     * Constructors
     */
    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public DAOException(Throwable ex) {
        super(ex);
    }
}