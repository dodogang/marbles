package net.dodogang.marbles.core.mixin.data;

import net.dodogang.marbles.data.DataMain;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mixin(Main.class)
public class DataGenMixin {
    @Inject(method = "main", at = @At("HEAD"), cancellable = true)
    private static void onDataMain(CallbackInfo info) {
        boolean data = Boolean.parseBoolean(System.getProperty("marbles.datagen"));
        if (data) {
            String path = System.getProperty("marbles.datagen.path");

            String[] paths = path == null ? new String[0] : path.split(";");

            List<String> args = new ArrayList<>();
            args.add("-client");
            args.add("-server");
            String pathOptName = "-output";
            for (String pth : paths) {
                args.add(pathOptName);
                args.add(pth);
                pathOptName = "-extra";
            }

            try {
                DataMain.main(args.toArray(new String[0]));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                info.cancel();
            }
        }
    }
}
