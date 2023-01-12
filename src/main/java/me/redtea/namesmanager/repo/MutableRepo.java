package me.redtea.namesmanager.repo;

import me.redtea.namesmanager.reload.Reloadable;

public interface MutableRepo<K, V> extends Repo<K, V>, Reloadable {

    V update(V value);

    V remove(K key);

    void saveAll();
}
