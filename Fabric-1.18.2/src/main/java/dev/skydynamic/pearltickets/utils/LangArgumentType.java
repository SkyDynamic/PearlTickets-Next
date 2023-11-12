package dev.skydynamic.pearltickets.utils;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class LangArgumentType implements ArgumentType<String> {
    public static LangArgumentType lang()
    {
        return new LangArgumentType();
    }

    public static <S> String getLangString(CommandContext<S> context, String name)
    {
        return context.getArgument(name, String.class);
    }

    @Override
    public String parse(StringReader reader)
    {
        int argBeginning = reader.getCursor();
        if (!reader.canRead())
        {
            reader.skip();
        }

        while (reader.canRead() && reader.peek() != ' ')
        {
            reader.skip();
        }

        return reader.getString().substring(argBeginning, reader.getCursor());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
    {
        var prefix = builder.getRemaining();
        if (!prefix.isEmpty())
        {
            Translate.supportLanguage
                .stream()
                .map(Object::toString)
                .filter(it -> it.startsWith(prefix))
                .forEach(builder::suggest);
        } else
        {
            Translate.supportLanguage
                .stream()
                .map(Object::toString)
                .forEach(builder::suggest);
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples()
    {
            return ArgumentType.super.getExamples();
    }

}
