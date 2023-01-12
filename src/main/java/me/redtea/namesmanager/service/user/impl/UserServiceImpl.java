package me.redtea.namesmanager.service.user.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.UserDto;
import me.redtea.namesmanager.factory.UserFactory;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import me.redtea.namesmanager.service.user.UserService;
import org.bukkit.entity.Player;

import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserServiceImpl implements UserService {
    private final UserFactory factory;
    private final UserRepo repo;

    @Override
    public UserDto createIfNotExists(Player player, String name, Gender gender) {
        var result = factory.create(player.getUniqueId(), name, gender, 0);
        player.setDisplayName(result.getName());
        player.setCustomName(result.getName());
        player.setCustomNameVisible(true);
        return result;
    }

    @Override
    public UserDto removeIfExists(UUID uuid) {
        return repo.remove(uuid);
    }
}
