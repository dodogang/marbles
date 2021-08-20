package net.dodogang.marbles.init;

import me.andante.chord.item.CScaffoldingItem;
import net.dodogang.marbles.Marbles;
import net.dodogang.marbles.item.MarblesItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class MarblesItems {
    public static final Item YELLOW_SCAFFOLDING = register("yellow_scaffolding", new CScaffoldingItem(MarblesBlocks.YELLOW_SCAFFOLDING, new FabricItemSettings().group(MarblesItemGroup.INSTANCE)));

    public static final Item PINK_SALT_SHARD = register("pink_salt_shard");
    public static final Item UMBRAL_LAZULI = register("umbral_lazuli");

    public static final Item KOI_BUCKET = register("koi_bucket", new EntityBucketItem(MarblesEntities.KOI, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).group(MarblesItemGroup.INSTANCE)));

    public static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Marbles.MOD_ID, id), item);
    }
    public static Item register(String id) {
        return register(id, new Item(new FabricItemSettings().group(MarblesItemGroup.INSTANCE)));
    }
}
