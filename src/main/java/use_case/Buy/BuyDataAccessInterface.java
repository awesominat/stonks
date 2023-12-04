package use_case.Buy;

import entity.User;

/**
 * Defines the methods that will be used by the BuyInteractor class.
 */
public interface BuyDataAccessInterface {

    /**
     * Saves the user's data.
     */
    void save();

    /**
     * @return the user's data.
     */
    User get();

}
