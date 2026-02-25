package billeyzambie.practicalpets.ui.infobook;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ImageWidget extends AbstractWidget {
    private final ResourceLocation texture;

    private ImageWidget(int p_93629_, int p_93630_, int p_93631_, int p_93632_, ResourceLocation texture) {
        super(p_93629_, p_93630_, p_93631_, p_93632_, Component.empty());
        this.texture = texture;
    }

    public ImageWidget(int width, int height, ResourceLocation texture) {
        this(0, 0, width, height, texture);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(texture, this.getX(), this.getY(), 0, 0, this.width, this.height, this.width, this.height);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

    @Override
    public void playDownSound(SoundManager p_93665_) {
    }
}
