package dev.skydynamic.pearltickets.config;

import java.util.Objects;

public class ConfigStorage {
    @Ignore
    public static final ConfigStorage DEFAULT = new ConfigStorage(false, "zh_cn");
    Boolean enable;
    String lang;

    public ConfigStorage(Boolean enable, String lang)
    {
        this.enable = enable;
        this.lang = lang;
    }

    public Boolean getEnable()
    {
        return enable;
    }
    public String getLang()
    {
        return lang;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ConfigStorage that)) return false;
        return Objects.equals(getEnable(), that.getEnable());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getEnable(), getLang());
    }
}
