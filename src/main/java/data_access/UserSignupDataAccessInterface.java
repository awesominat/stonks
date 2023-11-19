package data_access;


public interface UserSignupDataAccessInterface {
    boolean existsByName(String identifier);

    void save();
}
