package src.dao;

public interface UserDao {
    void create(models.UserModel user) throws DAOException;
    models.UserModel find(String email) throws DAOException;
}

