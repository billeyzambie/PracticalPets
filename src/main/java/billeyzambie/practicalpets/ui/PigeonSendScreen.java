package billeyzambie.practicalpets.ui;

import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.misc.PPNetworking;
import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.network.PigeonSendPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PigeonSendScreen extends Screen {
    private static final ResourceLocation BACKGROUND =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/pigeon_send_menu.png");
    private static final int IMAGE_WIDTH = 176;
    private static final int IMAGE_HEIGHT = 166;
    private static final int SEARCH_TOP = 6;
    private static final int SEARCH_HEIGHT = 14;
    private static final int LIST_TOP = 24;
    private static final int LIST_BOTTOM = 134;
    private static final int LIST_LEFT = 8;
    private static final int LIST_RIGHT = 168;
    private final Pigeon pigeon;
    private PlayerList playerList;
    private Button sendButton;
    private EditBox searchBox;
    private String lastSearch = "";
    private int leftPos;
    private int topPos;

    public PigeonSendScreen(Pigeon pigeon) {
        super(Component.translatable("ui.practicalpets.pigeon_send.title"));
        this.pigeon = pigeon;
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - IMAGE_WIDTH) / 2;
        this.topPos = (this.height - IMAGE_HEIGHT) / 2;

        String previousSearch = this.searchBox != null ? this.searchBox.getValue() : "";

        this.searchBox = new EditBox(
                this.font,
                this.leftPos + 8,
                this.topPos + SEARCH_TOP,
                IMAGE_WIDTH - 16,
                SEARCH_HEIGHT,
                Component.translatable("ui.practicalpets.pigeon_send.search")
        );
        this.searchBox.setMaxLength(24);
        this.searchBox.setValue(previousSearch);
        this.searchBox.setHint(Component.translatable("ui.practicalpets.pigeon_send.search"));
        this.searchBox.setResponder(this::checkSearchStringUpdate);
        this.addRenderableWidget(this.searchBox);

        this.playerList = new PlayerList();
        this.addRenderableWidget(this.playerList);

        int buttonY = this.topPos + IMAGE_HEIGHT - 27;
        this.sendButton = this.addRenderableWidget(Button.builder(
                Component.translatable("ui.practicalpets.pigeon_send.send", pigeon.getDisplayName()),
                button -> sendSelectedPlayer()
        ).pos(this.leftPos + 8, buttonY).size(78, 20).build());

        Button cancelButton = this.addRenderableWidget(Button.builder(
                Component.translatable("ui.practicalpets.pigeon_send.cancel"),
                button -> onClose()
        ).pos(this.leftPos + 90, buttonY).size(78, 20).build());

        this.sendButton.active = false;

        checkSearchStringUpdate(this.searchBox.getValue());
    }


    @Override
    public void tick() {
        if (shouldClose()) {
            this.onClose();
        }
        super.tick();
        if (this.searchBox != null) {
            this.searchBox.tick();
        }
    }

    private boolean shouldClose() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return true;
        if (!pigeon.isAlive() || pigeon.isRemoved())
            return true;
        double maxDist = 8;
        return mc.player.distanceToSqr(pigeon) > maxDist * maxDist;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics) {
        super.renderBackground(graphics);
        graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    private void sendSelectedPlayer() {
        PlayerList.Entry entry = playerList.getSelected();
        if (entry == null) {
            return;
        }

        PPNetworking.CHANNEL.sendToServer(new PigeonSendPacket(pigeon.getId(), entry.playerId));
        onClose();
    }

    private void checkSearchStringUpdate(String search) {
        String normalized = search.toLowerCase(Locale.ROOT);
        if (!normalized.equals(this.lastSearch)) {
            this.playerList.applyFilters(normalized);
            this.lastSearch = normalized;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private class PlayerList extends AbstractSelectionList<PlayerList.Entry> {

        private final java.util.ArrayList<Entry> allEntries = new java.util.ArrayList<>();

        private PlayerList() {
            super(
                    Minecraft.getInstance(),
                    LIST_RIGHT - LIST_LEFT,
                    LIST_BOTTOM - LIST_TOP,
                    PigeonSendScreen.this.topPos + LIST_TOP,
                    PigeonSendScreen.this.topPos + LIST_BOTTOM,
                    20
            );

            this.setLeftPos(PigeonSendScreen.this.leftPos + LIST_LEFT);

            this.setRenderBackground(false);
            this.setRenderTopAndBottom(false);

            rebuildCache();
            applyFilters(PigeonSendScreen.this.searchBox != null
                    ? PigeonSendScreen.this.searchBox.getValue().toLowerCase(Locale.ROOT)
                    : "");
        }

        @Override
        public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            super.render(graphics, mouseX, mouseY, partialTicks);

            if (this.children().isEmpty()) {
                Component component = Component.translatable("ui.practicalpets.pigeon_send.no_targets");

                int pad = 4;

                int wrapWidth = (this.x1 - this.x0) - pad * 2;

                int x = this.x0 + pad;
                int y = this.y0 + pad;

                graphics.drawWordWrap(
                        Minecraft.getInstance().font,
                        component,
                        x,
                        y,
                        wrapWidth,
                        0xA0A0A0
                );
            }
        }



        private void rebuildCache() {
            this.allEntries.clear();

            if (minecraft.getConnection() == null) return;

            List<PlayerInfo> players = minecraft.getConnection().getOnlinePlayers().stream()
                    .sorted(Comparator.comparing(info -> info.getProfile().getName(), String.CASE_INSENSITIVE_ORDER))
                    .toList();

            for (PlayerInfo info : players) {
                UUID id = info.getProfile().getId();
                String name = info.getProfile().getName();
                this.allEntries.add(new Entry(id, name));
            }
        }

        private void applyFilters(String searchLower) {
            this.clearEntries();

            if (minecraft.level == null || minecraft.player == null) {
                this.setSelected(null);
                return;
            }

            final var localPlayer = minecraft.player;

            for (Entry entry : this.allEntries) {
                if (entry.playerId.equals(localPlayer.getUUID())) {
                    continue;
                }

                if (!searchLower.isEmpty() && !entry.name.toLowerCase(Locale.ROOT).contains(searchLower)) {
                    continue;
                }

                Player target = minecraft.level.getPlayerByUUID(entry.playerId);
                if (target != null && pigeon.isInvalidMissionTarget(target)) {
                    continue;
                }

                this.addEntry(entry);
            }

            if (this.getSelected() != null && !this.children().contains(this.getSelected())) {
                this.setSelected(null);
            }
        }

        @Override
        public int getRowWidth() {
            return LIST_RIGHT - LIST_LEFT - 8;
        }

        @Override
        protected int getScrollbarPosition() {
            return PigeonSendScreen.this.leftPos + LIST_RIGHT - 6;
        }

        @Override
        public void setSelected(Entry entry) {
            super.setSelected(entry);
            sendButton.active = entry != null;
        }

        @Override
        public void updateNarration(@NotNull NarrationElementOutput narration) {
        }

        private class Entry extends AbstractSelectionList.Entry<Entry> {
            private final UUID playerId;
            private final String name;

            private Entry(UUID playerId, String name) {
                this.playerId = playerId;
                this.name = name;
            }

            @Override
            public void render(@NotNull GuiGraphics graphics, int index, int y, int x, int width, int height,
                               int mouseX, int mouseY, boolean hovered, float partialTicks) {
                int color = hovered ? 0xFFFFFF : 0xC0C0C0;
                graphics.drawString(PigeonSendScreen.this.font, name, x + 2, y + 6, color, false);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                PlayerList.this.setSelected(this);
                return true;
            }
        }
    }
}
