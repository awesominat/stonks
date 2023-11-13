package app;

import data_access.FileUserDataAccessObject;
import data_access.UserSignupDataAccessInterface;
import entities.CommonUser;
import entities.CommonUserFactory;
import entities.User;

import java.io.IOException;

public class loggingIntesting {
    public static void main(String[] args) throws IOException {
        UserSignupDataAccessInterface userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());

        System.out.println(userDataAccessObject.existsByName("myname"));

        User newUser = new CommonUser("myname", "mypass :O");
        userDataAccessObject.save(newUser);

        System.out.println(userDataAccessObject.existsByName("myname"));
    }
}
