package dev.skydynamic.pearltickets.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

public class Translate {

    private static Map<String, String> translateMap = new HashMap<>();

    public static final Collection<String> supportLanguage = List.of("zh_cn", "en_us");

    public static Map<String, String> getTranslationFromResourcePath(String lang) {
            InputStream langFile = Translate.class.getClassLoader().getResourceAsStream("assets/pearltickets/lang/%s.json".formatted(lang));
            if (langFile == null) {
                return Collections.emptyMap();
            }
            String jsonData;
            try {
                jsonData = IOUtils.toString(langFile, StandardCharsets.UTF_8);
            } catch (IOException e) {
                return Collections.emptyMap();
            }
            Gson gson = new GsonBuilder().setLenient().create();
            return gson.fromJson(jsonData, new TypeToken<Map<String, String>>() {}.getType());
    }

    public static void handleResourceReload(String lang)
    {
        translateMap = getTranslationFromResourcePath(lang);
    }

    public static String translate(String key, Object... args){
        String fmt = translateMap.getOrDefault(key,key);
        if (!translateMap.containsKey(key))return key;
        return MessageFormat.format(fmt, args);
    }

    public static String tr(String k, Object... o){
        return translate(k,o);
    }

}
