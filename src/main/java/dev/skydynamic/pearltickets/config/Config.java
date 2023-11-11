package dev.skydynamic.pearltickets.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;

//@SuppressWarnings("all")
public class Config
{
    public static Config INSTANCE = new Config();

    private final Object lock = new Object();
    private Path configPath;
    private ConfigStorage configStorage;
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    public Config setConfigPath(Path path)
    {
        this.configPath = path;
        return this;
    }

    public boolean containsTrue()
    {
        synchronized (lock)
        {
            return configStorage.enable;
        }
    }

    public String getLangString()
    {
        synchronized (lock)
        {
            return configStorage.lang;
        }
    }

    private void saveModifiedConfig(ConfigStorage c)
    {
        synchronized (lock)
        {
            try {
                if (configPath.toFile().exists()) configPath.toFile().delete();
                if (!configPath.toFile().exists()) configPath.toFile().createNewFile();
                var writer = new FileWriter(configPath.toFile());
                gson.toJson(fixFields(c), writer);
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void load()
    {
        synchronized (lock)
        {
            try {
                if (!configPath.toFile().exists()) {
                    saveModifiedConfig(ConfigStorage.DEFAULT);
                }
                var reader = new FileReader(configPath.toFile());
                var result = gson.fromJson(reader, ConfigStorage.class);
                this.configStorage = fixFields(result);
                reader.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setEnableValue(boolean value)
    {
        synchronized (lock)
        {
            configStorage.enable = value;
            saveModifiedConfig(fixFields(configStorage));
        }
    }

    public void setLangValue(String lang)
    {
        synchronized (lock)
        {
            configStorage.lang = lang;
            saveModifiedConfig(fixFields(configStorage));
        }
    }

    private <T> T fixFields(T t)
    {
        if (t == null)
        {
            throw new NullPointerException();
        }
        if (t == ConfigStorage.DEFAULT)
        {
            return t;
        }
        try
        {
            var clazz = t.getClass();
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (Arrays.stream(declaredField.getDeclaredAnnotations()).anyMatch(it -> it.annotationType() == Ignore.class))
                    continue;
                declaredField.setAccessible(true);
                var value = declaredField.get(t);
                var dv = declaredField.get(ConfigStorage.DEFAULT);
                if (value == null) {
                    declaredField.set(t, dv);
                }
            }
            return t;
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


}