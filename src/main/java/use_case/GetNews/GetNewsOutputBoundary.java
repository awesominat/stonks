package use_case.GetNews;

public interface GetNewsOutputBoundary {
    void prepareSuccessView(GetNewsOutputData result);

    void prepareFailView(String error);
}