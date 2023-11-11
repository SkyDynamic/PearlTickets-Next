package dev.skydynamic.pearltickets.config;

import java.util.Objects;

public class ConfigStorage {
    @Ignore
    public static final ConfigStorage DEFAULT = new ConfigStorage(false);
    Boolean enable;

    public ConfigStorage(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getPermissions() {
        return enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigStorage that)) return false;
        return Objects.equals(getPermissions(), that.getPermissions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPermissions());
    }
}
