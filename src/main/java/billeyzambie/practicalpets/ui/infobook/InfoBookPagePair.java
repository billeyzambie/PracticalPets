package billeyzambie.practicalpets.ui.infobook;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.List;

public class InfoBookPagePair {

    public static final int LEFT_TEXT_LEFT_POS = 18;
    public static final int RIGHT_TEXT_LEFT_POS = LEFT_TEXT_LEFT_POS + InfoBookScreen.IMAGE_WIDTH / 2 - 5;
    public static final int TEXT_TOP_POS = 15;
    public static final int TEXT_MAX_HEIGHT = InfoBookScreen.IMAGE_HEIGHT - 31;
    public static final int TEXT_MAX_WIDTH = InfoBookScreen.IMAGE_WIDTH / 2 - 31;

    public static final ArrayList<InfoBookPagePair> PAIRS = new ArrayList<>();

    private final LeftPage leftPage;
    private final RightPage rightPage;
    public final List<Page> pages;

    public InfoBookPagePair(LeftPage leftPage, RightPage rightPage) {
        this.leftPage = leftPage;
        this.rightPage = rightPage;
        this.pages = List.of(leftPage, rightPage);
    }

    public InfoBookPagePair() {
        this(new LeftPage(), new RightPage());
    }

    public static abstract class Page {
        public final List<DefaultPositionWidget> widgets = Lists.newArrayList();

        public record DefaultPositionWidget(int x, int y, AbstractWidget widget) {
        }

        public abstract int textLeftPos();

        public final int textTopPos() {
            return TEXT_TOP_POS;
        }

        protected AbstractWidget addRenderableWidget(AbstractWidget widget) {
            this.widgets.add(new DefaultPositionWidget(widget.getX(), widget.getY(), widget));
            return widget;
        }
    }

    public static class LeftPage extends Page {
        @Override
        public int textLeftPos() {
            return LEFT_TEXT_LEFT_POS;
        }
    }

    public static class RightPage extends Page {
        @Override
        public int textLeftPos() {
            return RIGHT_TEXT_LEFT_POS;
        }
    }

    static {
        InfoBookWriter.WRITER.writeInfoBook();
    }

}