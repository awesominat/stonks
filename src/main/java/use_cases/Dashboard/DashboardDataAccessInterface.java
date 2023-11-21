package use_cases.Dashboard;

import entities.User;

public interface DashboardDataAccessInterface {
    void save();
    User get();
}
