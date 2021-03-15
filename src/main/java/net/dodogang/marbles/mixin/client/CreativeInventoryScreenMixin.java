package net.dodogang.marbles.mixin.client;

import com.google.common.collect.Lists;
import net.dodogang.marbles.client.gui.itemgroup.AbstractTabbedItemGroup;
import net.dodogang.marbles.client.gui.itemgroup.ItemGroupTabWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    private final List<ItemGroupTabWidget> tabButtons = Lists.newArrayList();

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory inventory, Text text) {
        super(screenHandler, inventory, text);
    }

    @Inject(at = @At("HEAD"), method = "setSelectedTab(Lnet/minecraft/item/ItemGroup;)V")
    private void setSelectedTab(ItemGroup group, CallbackInfo ci) {
        buttons.removeAll(tabButtons);
        tabButtons.clear();

        if (group instanceof AbstractTabbedItemGroup) {
            AbstractTabbedItemGroup tab = (AbstractTabbedItemGroup) group;
            if (!tab.isInitialized()) {
                tab.init();
            }

            for (int i = 0; i < tab.getTabs().size(); i++) {
                int selectTab = i;
                ItemGroupTabWidget tabWidget = new ItemGroupTabWidget(x - 24, (y + 12) + (i * 24), tab.getTabs().get(i), (btn) -> {
                    tab.setSelectedTabIndex(selectTab);
                    MinecraftClient.getInstance().openScreen(this);
                    ((ItemGroupTabWidget) btn).isSelected = true;
                });

                if (i == tab.getSelectedTabIndex()) {
                    tabWidget.isSelected = true;
                }

                tabButtons.add(tabWidget);
                addButton(tabWidget);
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V")
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo cbi) {
        tabButtons.forEach(b -> {
            if (b.isHovered()) {
                renderTooltip(matrixStack, b.getMessage(), mouseX, mouseY);
            }
        });
    }
}
