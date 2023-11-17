package use_cases.GetNews;

public interface GetNewsOutputBoundary {
    void prepareSuccessView(GetNewsOutputData result);

    void prepareFailView(String error);
}
