package net.dodogang.marbles.client.gui.itemgroup;

import com.google.common.collect.ImmutableList;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.tag.MarblesItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class MarblesItemGroup extends AbstractTabbedItemGroup {
    public MarblesItemGroup(Identifier id) {
        super(id);
    }

    @Override
    public List<ItemGroupTab> initTabs() {
        return ImmutableList.of(
            createTab(MarblesBlocks.TRAVERTINE, "travertine", MarblesItemTags.CreativeTabs.TRAVERTINE),
            createTab(MarblesBlocks.ASPEN.LOG, "wood", MarblesItemTags.CreativeTabs.WOOD),
            createTab(MarblesBlocks.DAWN_SAND, "sand", MarblesItemTags.CreativeTabs.SAND)
        );
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Marbles.LOGO);
    }
}
