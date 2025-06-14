package billeyzambie.animationcontrollers;

public class ACData {
    private int stateIndex = 0;
    private int previousStateIndex = 0;
    private float timeStateStarted;
    private float timePreviousStateStarted;

    public ACData(float timeStateStarted) {
        this.timeStateStarted = timeStateStarted;
    }

    public int getStateIndex() {
        return stateIndex;
    }

    public void setStateIndex(int stateIndex, float timeStateStarted) {
        this.previousStateIndex = this.stateIndex;
        this.timePreviousStateStarted = this.timeStateStarted;
        this.stateIndex = stateIndex;
        this.timeStateStarted = timeStateStarted;
    }

    public int getPreviousStateIndex() {
        return previousStateIndex;
    }

    public float getTimeStateStarted() {
        return timeStateStarted;
    }

    public float getTimePreviousStateStarted() {
        return timePreviousStateStarted;
    }

}
