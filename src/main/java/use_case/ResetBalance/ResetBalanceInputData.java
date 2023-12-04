package use_case.ResetBalance;

/**
 * Contains an attribute that indicates whether the reset button
 * has been pressed
 */
public class ResetBalanceInputData {
    private Boolean resetPressed;

    public ResetBalanceInputData(Boolean resetPressed) {
        this.resetPressed = resetPressed;
    }

    public Boolean getResetPressed() {
        return resetPressed;
    }
}
