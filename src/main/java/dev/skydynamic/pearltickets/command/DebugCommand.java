package dev.skydynamic.pearltickets.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import dev.skydynamic.pearltickets.PearlTicketNext;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class DebugCommand {

    private static final LiteralArgumentBuilder<ServerCommandSource> debugCommand = literal("pearlTicketsDebug")
        .executes(it -> execute(it.getSource()));

    private static int execute(ServerCommandSource commandSource) {
        PearlTicketNext.modStatus.SetDebugValue();
        boolean debug = PearlTicketNext.modStatus.debug;
        commandSource.getPlayer().sendMessage(Text.of("当前Debug状态: " + String.valueOf(debug)));
        return 0;
    }

    public void registerDebugCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(debugCommand);
    }

}
