package use_case.ResetBalance;

public class ResetBalanceOutputData {
    public Boolean resetPressed;

    public ResetBalanceOutputData(Boolean resetPressed){
        this.resetPressed = resetPressed;
    }

    public Boolean isResetPressed() {
        return resetPressed;
    }
}
