package net.dodogang.marbles.client.gui.itemgroup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ItemGroupTab {
    private final Tag<Item> tag;
    private final ItemStack icon;
    private final Identifier id;

    public ItemGroupTab(ItemStack icon, Identifier id, Tag<Item> tag) {
        this.tag = tag;
        this.icon = icon;
        this.id = id;
    }

    public Identifier getId() {
        return this.id;
    }

    public TranslatableText getTranslationKey() {
        return new TranslatableText("itemGroup.tab." + id);
    }

    public ItemStack getIcon() {
        return icon;
    }

    public boolean matches(Item item) {
        return tag != null && tag.contains(item);
    }
}
