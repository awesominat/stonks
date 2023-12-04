package use_case.ResetBalance;

/**
 * Contains an attribute that indicates whether the reset button
 * has been pressed
 */
public class ResetBalanceOutputData {
    public Boolean resetPressed;

    public ResetBalanceOutputData(Boolean resetPressed){
        this.resetPressed = resetPressed;
    }

    public Boolean isResetPressed() {
        return resetPressed;
    }
}
