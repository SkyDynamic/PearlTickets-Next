package dev.skydynamic.pearltickets.mixin;

import dev.skydynamic.pearltickets.utils.Translate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

import dev.skydynamic.pearltickets.config.Config;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServer_coreMixin
{

    @Shadow
    public abstract Path getSavePath(WorldSavePath worldSavePath);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onServerInit(CallbackInfo ci) {
        var configPath = getSavePath(WorldSavePath.ROOT).resolve("PearlTicket-Next.json");
        Config.INSTANCE.setConfigPath(configPath).load();
        Translate.handleResourceReload(Config.INSTANCE.getLangString());
    }

}
