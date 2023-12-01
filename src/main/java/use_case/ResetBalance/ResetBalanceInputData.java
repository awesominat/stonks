package use_case.ResetBalance;

public class ResetBalanceInputData {
    private Boolean resetPressed;

    public ResetBalanceInputData(Boolean resetPressed) {
        this.resetPressed = resetPressed;
    }
    public void setResetPressed(Boolean resetPressed) {
        this.resetPressed = resetPressed;
    }

    public Boolean getResetPressed() {
        return resetPressed;
    }
}
