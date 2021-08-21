package net.dodogang.marbles.item;

import com.google.common.collect.ImmutableList;
import me.andante.chord.client.gui.item_group.ItemGroupTab;
import me.andante.chord.item.TabbedItemGroupAppendLogic;
import me.andante.chord.item.item_group.AbstractTabbedItemGroup;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.tag.MarblesItemTags;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class MarblesItemGroup extends AbstractTabbedItemGroup {
    public static final ItemGroup INSTANCE = new MarblesItemGroup();

    public static final Item ICON = Registry.register(Registry.ITEM, new Identifier(Marbles.MOD_ID, "icon"), new Item(new FabricItemSettings().food(new FoodComponent.Builder().alwaysEdible().hunger(20).build())));

    private static final ArrayList<Tag.Identified<Item>> tags = new ArrayList<>();

    private MarblesItemGroup() {
        super(Marbles.MOD_ID);
    }

    @Override
    public List<ItemGroupTab> initTabs() {
        return ImmutableList.of(
            createTab(MarblesBlocks.TRAVERTINE.RAW, MarblesItemTags.CHORD_GROUP_TRAVERTINE_STRAWS),
            createTab(MarblesBlocks.PINK_SALT, MarblesItemTags.CHORD_GROUP_PINK_SALT_CAVES),
            createTab(MarblesBlocks.DAWN.SAND, MarblesItemTags.CHORD_GROUP_SUNSET_GROTTO),
            createTab(MarblesBlocks.GRISP_PODZOL, MarblesItemTags.CHORD_GROUP_ASPEN_CREVICES),
            createTab(MarblesBlocks.YELLOW_BAMBOO, MarblesItemTags.CHORD_GROUP_BAMBOO_VALLEY),
            createTab(MarblesBlocks.CUT_ICE, MarblesItemTags.CHORD_GROUP_ICE)
        );
    }

    protected ItemGroupTab createTab(ItemConvertible item, Tag.Identified<Item> tag) {
        tags.add(tag);

        String path = tag.getId().getPath();
        return super.createTab(item, path.substring(path.lastIndexOf("/") + 1));
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ArrayList<ItemConvertible> items = new ArrayList<>();
        int selectedTabIndex = this.getSelectedTabIndex();

        if (selectedTabIndex == 0) {
            tags.forEach(tag -> tag.values().forEach(item -> appendStack(item, items, stacks)));

            Registry.ITEM.forEach(item -> {
                if (item.getGroup() == this && !items.contains(item)) {
                    appendStack(item, items, stacks);
                }
            });
        } else {
            tags.get(selectedTabIndex - 1).values().forEach(item -> appendStack(item, items, stacks));
        }
    }
    protected void appendStack(Item item, ArrayList<ItemConvertible> items, DefaultedList<ItemStack> stacks) {
        if (item instanceof TabbedItemGroupAppendLogic) {
            ((TabbedItemGroupAppendLogic) item).appendStacksToTab(this, stacks);
        } else {
            items.add(item);
            stacks.add(item.getDefaultStack());
        }
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
