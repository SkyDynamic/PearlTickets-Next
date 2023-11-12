package dev.skydynamic.pearltickets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import dev.skydynamic.pearltickets.command.PearlTicketsCommand;

public class PearlTicketNext implements ModInitializer
{

    @Override
    public void onInitialize()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> new PearlTicketsCommand().registerDebugCommand(dispatcher));
    }

}
