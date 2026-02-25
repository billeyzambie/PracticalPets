package billeyzambie.practicalpets.ui.infobook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

public class InfoBookClickableText extends Button {
    private final Component message;
    private final Component underlinedMessage;

    public static final Font FONT = InfoBookTextWidget.FONT;
    private static final int TEXT_COLOR = InfoBookTextWidget.TEXT_COLOR;

    public InfoBookClickableText(int p_211755_, int p_211756_, Component text, OnPress p_211760_) {
        super(p_211755_, p_211756_, 0, 0, text, p_211760_, DEFAULT_NARRATION);
        this.width = FONT.width(text);
        this.height = FONT.lineHeight;
        this.message = text;
        this.underlinedMessage = ComponentUtils.mergeStyles(text.copy(), Style.EMPTY.withUnderlined(true));
    }

    public InfoBookClickableText(Component text, OnPress onPress) {
        this(0, 0, text, onPress);
    }

    @Override
    public void renderWidget(GuiGraphics p_283309_, int p_282710_, int p_282486_, float p_281727_) {
        Component component = this.isHoveredOrFocused() ? this.underlinedMessage : this.message;
        p_283309_.drawString(FONT, component, this.getX(), this.getY(), TEXT_COLOR, false);
    }
}
