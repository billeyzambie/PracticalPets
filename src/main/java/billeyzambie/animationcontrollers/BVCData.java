package billeyzambie.animationcontrollers;

public class BVCData {
    private boolean inOtherState = false;
    private float timeStateStarted;

    private float result;

    public BVCData(float timeStateStarted) {
        this.timeStateStarted = timeStateStarted;
    }

    public boolean getIsInOtherState() {
        return inOtherState;
    }

    public void setState(boolean inOtherState, float timeStateStarted) {
        this.inOtherState = inOtherState;
        this.timeStateStarted = timeStateStarted;
    }

    public float getTimeStateStarted() {
        return timeStateStarted;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}