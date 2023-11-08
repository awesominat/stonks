package data_access;


import entities.User;

public interface UserSignupDataAccessInterface {
    boolean existsByName(String identifier);

    void save(User user);
}
