package app;

import data_access.FileUserDataAccessObject;
import entities.CommonUserFactory;

import java.io.IOException;

public class jsonparsing {
    public static void main(String[] args) throws IOException {
        FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject(
                "./user.json",
                new CommonUserFactory());


    }
}
