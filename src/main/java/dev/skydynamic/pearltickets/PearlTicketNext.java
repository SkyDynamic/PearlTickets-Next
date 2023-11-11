package dev.skydynamic.pearltickets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import dev.skydynamic.pearltickets.command.DebugCommand;

public class PearlTicketNext implements ModInitializer {

    public static class ModStatus{
        public boolean debug = false;

        public void SetDebugValue() {
            if (this.debug) {
                this.debug = false;
            }
            else {
                this.debug = true;
            }
        }
    }

    public static ModStatus modStatus = new ModStatus();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> new DebugCommand().registerDebugCommand(dispatcher));
    }
}
