package me.redtea.namesmanager.placeholder.strategy.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.placeholder.strategy.Placeholder;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import me.redtea.namesmanager.service.config.ConfigService;
import org.bukkit.OfflinePlayer;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GenderPlaceholder implements Placeholder {
    private final UserRepo userRepo;

    private final ConfigService config;

    @Override
    public String prefix() {
        return "gender";
    }

    @Override
    public String render(OfflinePlayer player, String[] args) {
        return config.genderName(userRepo.get(player.getUniqueId()).orElseThrow().getGender());
    }
}
