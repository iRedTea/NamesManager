package me.redtea.namesmanager.repo.impl.user;

import me.redtea.namesmanager.domain.UserDto;
import me.redtea.namesmanager.repo.cache.CacheRepo;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface UserRepo extends CacheRepo<UUID, UserDto> {
    default void loadToCache(Player player) {
        loadToCache(player.getUniqueId());
    }

    default void removeFromCache(Player player) {
        removeFromCache(player.getUniqueId());
    }
}
