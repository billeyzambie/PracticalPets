package billeyzambie.practicalpets.ui.infobook;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

public class HomeButton extends Button {
    public static final ResourceLocation TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/gui/info_book/home.png"
    );
    public static final int WIDTH = 12;
    public static final int HEIGHT = 15;

    public HomeButton(int x, int y, OnPress onPress) {
        super(x, y, WIDTH, HEIGHT, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
    }


    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int p_282922_, int p_283637_, float p_282459_) {
        int i = 0;
        if (this.isHoveredOrFocused()) {
            i += WIDTH;
        }

        graphics.blit(TEXTURE, this.getX(), this.getY(), i, 0, WIDTH, HEIGHT, WIDTH * 2, HEIGHT);
    }


    @Override
    public void playDownSound(SoundManager p_99231_) {
        p_99231_.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
    }
}
