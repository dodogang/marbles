package net.dodogang.marbles.item;

import com.google.common.collect.ImmutableList;
import me.andante.chord.client.gui.item_group.ItemGroupTab;
import me.andante.chord.item.item_group.AbstractTabbedItemGroup;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class MarblesItemGroup extends AbstractTabbedItemGroup {
    public static final ItemGroup INSTANCE = new MarblesItemGroup();

    public static final Item ICON = Registry.register(Registry.ITEM, new Identifier(Marbles.MOD_ID, "icon"), new Item(new FabricItemSettings().food(new FoodComponent.Builder().alwaysEdible().hunger(20).build())));

    private MarblesItemGroup() {
        super(Marbles.MOD_ID);
    }

    @Override
    public List<ItemGroupTab> initTabs() {
        return ImmutableList.of(
            createTab(MarblesBlocks.PINK_SALT, "pink_salt_and_travertine"),
            createTab(MarblesBlocks.DAWN_SAND, "sunset_grotto"),
            createTab(MarblesBlocks.CUT_ICE, "ice"),
            createTab(MarblesBlocks.GRISP_PODZOL, "aspen_crevices"),
            createTab(MarblesBlocks.YELLOW_BAMBOO, "bamboo_valley")
        );
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(MarblesItemGroup.ICON);
    }

    @Override
    public String getName() {
        return Marbles.MOD_ID;
    }
}
