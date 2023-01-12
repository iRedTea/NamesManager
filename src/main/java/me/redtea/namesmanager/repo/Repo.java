package me.redtea.namesmanager.repo;

import me.redtea.namesmanager.reload.Reloadable;

import java.util.Collection;
import java.util.Optional;

public interface Repo<K, V> extends Reloadable {
    Collection<V> all();

    Optional<V> get(K key);
}