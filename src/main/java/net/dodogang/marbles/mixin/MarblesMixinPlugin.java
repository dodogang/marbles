package net.dodogang.marbles.mixin;

import com.google.common.collect.Lists;
import net.dodogang.marbles.Marbles;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MarblesMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        // We need these here just to have them under a system property
        if (Boolean.parseBoolean(System.getProperty(Marbles.MOD_ID + ".datagen"))) {
            return Lists.newArrayList(
                "datagen.DataGenMixin",
                "datagen.DataCacheMixin",
                "datagen.DataGeneratorMixin"
            );
        }
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
