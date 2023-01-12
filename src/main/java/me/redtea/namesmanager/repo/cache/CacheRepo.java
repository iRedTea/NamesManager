package me.redtea.namesmanager.repo.cache;

import me.redtea.namesmanager.repo.MutableRepo;

public interface CacheRepo<K, V> extends MutableRepo<K, V> {
    void loadToCache(K k);

    void removeFromCache(K k);
}
