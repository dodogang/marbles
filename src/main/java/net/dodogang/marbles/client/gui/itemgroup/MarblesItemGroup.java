package net.dodogang.marbles.client.gui.itemgroup;

import com.google.common.collect.ImmutableList;
import me.andante.chord.item.item_group.AbstractTabbedItemGroup;
import me.andante.chord.item.item_group.ItemGroupTab;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class MarblesItemGroup extends AbstractTabbedItemGroup {
    // TODO Why is this here and not in the MarblesItems class?
    public static final Item LOGO = Registry.register(Registry.ITEM, new Identifier(Marbles.MOD_ID, "logo"), new Item(new FabricItemSettings().food(new FoodComponent.Builder().alwaysEdible().hunger(20).build())));

    public MarblesItemGroup() {
        super(Marbles.MOD_ID);
    }

    @Override
    public List<ItemGroupTab> initTabs() {
        return ImmutableList.of(
            createTab(MarblesBlocks.TRAVERTINE_BLOCKS.RAW, "travertine"),
            createTab(MarblesBlocks.ASPEN.LOG, "wood"),
            createTab(MarblesBlocks.DAWN_SAND, "sand")
        );
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(LOGO);
    }
}
