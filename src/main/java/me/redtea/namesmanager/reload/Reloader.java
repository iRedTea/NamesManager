package me.redtea.namesmanager.reload;

public interface Reloader {
    void add(Reloadable... reloadables);

    <T extends Reloadable> void add(Class<T> type);

    void reload();

    void close();

}
