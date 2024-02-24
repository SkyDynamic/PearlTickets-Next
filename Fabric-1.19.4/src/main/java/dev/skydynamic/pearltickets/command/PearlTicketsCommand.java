package dev.skydynamic.pearltickets.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import dev.skydynamic.pearltickets.config.Config;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

import dev.skydynamic.pearltickets.utils.Translate;

import static dev.skydynamic.pearltickets.utils.Translate.supportLanguage;
import static dev.skydynamic.pearltickets.utils.Translate.tr;

public class PearlTicketsCommand
{

    private static final LiteralArgumentBuilder<ServerCommandSource> PearlTicketsCommand = literal("pearlTickets-next")
        .requires(src -> src.hasPermissionLevel(3))
        .then(literal("on").executes(it -> execute(it.getSource(), true)))
        .then(literal("off").executes(it -> execute(it.getSource(), false)))
        .then(literal("lang")
            .then(literal("zh_cn").executes(it -> changeLang(it.getSource(), "zh_cn")))
            .then(literal("en_us").executes(it -> changeLang(it.getSource(), "en_us")))
        );

    private static int changeLang(ServerCommandSource commandSource, String lang)
    {
        if (supportLanguage.contains(lang))
        {
            Translate.handleResourceReload(lang);
            commandSource.sendFeedback(Text.of(tr("pearltickets.change_lang").formatted(lang)), true);
            Config.INSTANCE.setLangValue(lang);
            return 1;
        }
        else
        {
            commandSource.sendFeedback(Text.of(tr("pearltickets.lang_not_exist")), true);
            return 0;
        }
    }

    private static int execute(ServerCommandSource commandSource, boolean status)
    {
        if (Config.INSTANCE.containsTrue() == status)
        {
            commandSource.sendFeedback(Text.of(tr("pearltickets.already_setting_enable").formatted(status)), true);
            return 0;
        }
        Config.INSTANCE.setEnableValue(status);
        commandSource.sendFeedback(Text.of(tr("pearltickets.success_setting_enable").formatted(status)), true);
        return 1;
    }

    public void registerDebugCommand(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(PearlTicketsCommand);
    }

}
