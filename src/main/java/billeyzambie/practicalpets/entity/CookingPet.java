package billeyzambie.practicalpets.entity;

public interface CookingPet {
    void setIsCooking(boolean value);
    boolean isCooking();
    void incrementCookingTicks();
    boolean cookingTimerRanOut();
    boolean isCookingFinished();
    void cookingSuccess();
    void cookingInterrupted();
}
