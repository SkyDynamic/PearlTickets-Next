package dev.skydynamic.pearltickets;

import dev.skydynamic.pearltickets.utils.LangArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import dev.skydynamic.pearltickets.command.PearlTicketsCommand;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

public class PearlTicketNext implements ModInitializer
{

    @Override
    public void onInitialize()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> new PearlTicketsCommand().registerDebugCommand(dispatcher));
        ArgumentTypeRegistry.registerArgumentType(
            new Identifier("pearltickets", "lang"), LangArgumentType.class, ConstantArgumentSerializer.of(LangArgumentType::lang)
        );
    }

}
