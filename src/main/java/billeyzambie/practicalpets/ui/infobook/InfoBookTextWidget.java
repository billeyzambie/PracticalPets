package billeyzambie.practicalpets.ui.infobook;

import java.util.List;
import java.util.OptionalInt;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.SingleKeyCache;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class InfoBookTextWidget extends AbstractStringWidget {
    private OptionalInt maxWidth = OptionalInt.empty();
    private OptionalInt maxRows = OptionalInt.empty();
    private final SingleKeyCache<InfoBookTextWidget.CacheKey, MultiLineLabel> cache;

    private List<FormattedCharSequence> lines = List.of();

    public static final Font FONT = Minecraft.getInstance().font;
    public static final int TEXT_COLOR = 0x94745A;

    public InfoBookTextWidget(Component p_270532_) {
        this(0, 0, p_270532_, FONT);
        this.setColor(TEXT_COLOR).setMaxWidth(InfoBookPagePair.TEXT_MAX_WIDTH);
    }

    public InfoBookTextWidget(List<FormattedCharSequence> lines) {
        this(Component.empty());
        this.lines = lines;
    }

    public InfoBookTextWidget(int p_270325_, int p_270355_, Component p_270069_, Font p_270673_) {
        super(p_270325_, p_270355_, 0, 0, p_270069_, p_270673_);
        this.cache = Util.singleKeyCache((p_270516_) -> {
            return p_270516_.maxRows.isPresent() ? MultiLineLabel.create(p_270673_, p_270516_.message, p_270516_.maxWidth, p_270516_.maxRows.getAsInt()) : MultiLineLabel.create(p_270673_, p_270516_.message, p_270516_.maxWidth);
        });
        this.active = false;
    }

    @Override
    public InfoBookTextWidget setColor(int p_270378_) {
        super.setColor(p_270378_);
        return this;
    }

    public InfoBookTextWidget setMaxWidth(int p_270776_) {
        this.maxWidth = OptionalInt.of(p_270776_);
        this.lines = FONT.split(this.getMessage(), this.maxWidth.orElse(Integer.MAX_VALUE));
        return this;
    }

    public InfoBookTextWidget setMaxRows(int p_270085_) {
        this.maxRows = OptionalInt.of(p_270085_);
        return this;
    }

    @Override
    public int getWidth() {
        return this.cache.getValue(this.getFreshCacheKey()).getWidth();
    }

    @Override
    public int getHeight() {
        return this.lines.size() * FONT.lineHeight;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

        int x = this.getX();
        int y = this.getY();
        int color = this.getColor();

        for (FormattedCharSequence line : this.lines) {
            graphics.drawString(FONT, line, x, y, color, false);
            y += FONT.lineHeight;
        }
    }

    private InfoBookTextWidget.CacheKey getFreshCacheKey() {
        return new InfoBookTextWidget.CacheKey(this.getMessage(), this.maxWidth.orElse(Integer.MAX_VALUE), this.maxRows);
    }

    @OnlyIn(Dist.CLIENT)
    record CacheKey(Component message, int maxWidth, OptionalInt maxRows) {
    }
}