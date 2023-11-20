package use_cases.GetInfo;

public interface GetInfoOutputBoundary {
    void prepareSuccessView(GetInfoOutputData result);

    void prepareFailView(String error);
}
