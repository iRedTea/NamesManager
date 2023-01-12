package me.redtea.namesmanager.schema;

import me.redtea.namesmanager.reload.Reloadable;

import java.util.Collection;

public interface SchemaStrategy<K, V> extends Reloadable {
    Collection<V> all();

    V get(K key);

    void insert(V value);

    void remove(K key);
}
