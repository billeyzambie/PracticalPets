package billeyzambie.practicalpets.items;

public class DyeablePetHat extends PetHat implements DyeableItem{
    private final int defaultColor;

    public DyeablePetHat(String modelTextureName, float xpMultiplier, Properties properties, int defaultColor) {
        super(modelTextureName, xpMultiplier, properties);
        this.defaultColor = defaultColor;
    }
    public DyeablePetHat(String modelTextureName, int defaultColor) {
        super(modelTextureName);
        this.defaultColor = defaultColor;
    }

    @Override
    public int getDefaultColor() {
        return defaultColor;
    }
}
