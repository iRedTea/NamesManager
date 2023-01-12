package me.redtea.namesmanager.reload;

public interface Reloadable {
    void init();

    void close();

    default void reload() {
        close();
        init();
    }
}
