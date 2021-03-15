package net.dodogang.marbles.client.gui.itemgroup;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.List;

public abstract class AbstractTabbedItemGroup extends ItemGroup {
    protected final Identifier id;
    private final List<ItemGroupTab> tabs = Lists.newArrayList();
    private int selectedTabIndex = 0;
    private boolean initialized = false;

    protected AbstractTabbedItemGroup(Identifier id) {
        super(AbstractTabbedItemGroup.getItemGroupIndex(), id.getNamespace() + "." + id.getPath());
        this.id = id;
    }

    private static int getItemGroupIndex() {
        ((ItemGroupExtensions) ItemGroup.GROUPS[ItemGroup.GROUPS.length - 1]).fabric_expandArray();
        return ItemGroup.GROUPS.length - 1;
    }

    protected ItemGroupTab createTab(ItemConvertible item, String id, Tag<Item> tag) {
        return new ItemGroupTab(new ItemStack(item), new Identifier(this.id.getNamespace(), id), tag);
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        for (Item item : Registry.ITEM) {
            ItemGroupTab tab = this.getSelectedItemTab();
            if (tab.matches(item)) {
                stacks.add(new ItemStack(item));
            } else if (tab.getId().getPath().equals("all")) {
                for (ItemGroupTab i : tabs) {
                    if (i.matches(item)) {
                        stacks.add(new ItemStack(item));
                        break;
                    } else if (item.isIn(this)) {
                        stacks.add(new ItemStack(item));
                        break;
                    }
                }
            }
        }
    }

    public void init() {
        tabs.add(createAllTab());
        tabs.addAll(this.initTabs());

        initialized = true;
    }

    protected abstract List<ItemGroupTab> initTabs();

    protected ItemGroupTab createAllTab() {
        return new ItemGroupTab(getIcon(), new Identifier(id.getNamespace(), "all"), null);
    }

    public ItemGroupTab getSelectedItemTab() {
        return tabs.get(this.selectedTabIndex);
    }

    public List<ItemGroupTab> getTabs() {
        return this.tabs;
    }

    public int getSelectedTabIndex() {
        return this.selectedTabIndex;
    }

    public void setSelectedTabIndex(int selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public Text getTranslationKey() {
        return this.selectedTabIndex != 0 ? new TranslatableText("itemGroup." + id.getNamespace(), this.getSelectedItemTab().getTranslationKey()) : super.getTranslationKey();
    }
}
