package me.redtea.namesmanager.service.user;

import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.UserDto;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface UserService {
    UserDto createIfNotExists(Player player, String name, Gender gender);

    UserDto removeIfExists(UUID uuid);

    default UserDto removeIfExists(Player player) {
        return removeIfExists(player.getUniqueId());
    }
}
