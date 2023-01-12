package me.redtea.namesmanager.placeholder.strategy.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.placeholder.strategy.Placeholder;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import org.bukkit.OfflinePlayer;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class NamePlaceholder implements Placeholder {
    private final UserRepo userRepo;

    @Override
    public String prefix() {
        return "name";
    }

    @Override
    public String render(OfflinePlayer player, String[] args) {
        return userRepo.get(player.getUniqueId()).orElseThrow().getName();
    }
}
