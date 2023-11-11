package dev.skydynamic.pearltickets.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import dev.skydynamic.pearltickets.config.Config;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class PearlTicketsCommand
{

    private static final LiteralArgumentBuilder<ServerCommandSource> PearlTicketsCommand = literal("pearlTickets-next")
        .requires(src -> src.hasPermissionLevel(3))
        .then(literal("on").executes(it -> execute(it.getSource(), true)))
        .then(literal("off").executes(it -> execute(it.getSource(), false)));

    private static int execute(ServerCommandSource commandSource, boolean status)
    {
        if (Config.INSTANCE.containsTrue() == status)
        {
            commandSource.sendFeedback(() -> Text.of("珍珠自加载已经设置为§a§l " + status + " §r了"), true);
            return 0;
        }
        Config.INSTANCE.setEnableValue(status);
        commandSource.sendFeedback(() -> Text.of("珍珠自加载设置为: §a§l" + status), true);
        return 1;
    }

    public void registerDebugCommand(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(PearlTicketsCommand);
    }

}
