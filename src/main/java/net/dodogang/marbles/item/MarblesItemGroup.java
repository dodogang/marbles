package net.dodogang.marbles.item;

import com.google.common.collect.ImmutableList;
import me.andante.chord.client.gui.item_group.ItemGroupTab;
import me.andante.chord.item.item_group.AbstractTabbedItemGroup;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.tag.MarblesItemTags;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.tag.Tag;
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
            createTab(MarblesBlocks.PINK_SALT, MarblesItemTags.CHORD_GROUP_PINK_SALT_AND_TRAVERTINE),
            createTab(MarblesBlocks.DAWN_SAND, MarblesItemTags.CHORD_GROUP_SUNSET_GROTTO),
            createTab(MarblesBlocks.CUT_ICE, MarblesItemTags.CHORD_GROUP_ICE),
            createTab(MarblesBlocks.GRISP_PODZOL, MarblesItemTags.CHORD_GROUP_ASPEN_CREVICES),
            createTab(MarblesBlocks.YELLOW_BAMBOO, MarblesItemTags.CHORD_GROUP_BAMBOO_VALLEY)
        );
    }

    protected ItemGroupTab createTab(ItemConvertible item, Tag.Identified<Item> tag) {
        String path = tag.getId().getPath();
        return super.createTab(item, path.substring(path.lastIndexOf("/") + 1));
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
