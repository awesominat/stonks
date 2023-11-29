package use_case.Dashboard;

import entity.User;

public interface DashboardDataAccessInterface {
    void save();
    User get();
}
