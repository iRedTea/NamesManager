package me.redtea.namesmanager.placeholder.strategy.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.domain.UserDto;
import me.redtea.namesmanager.placeholder.strategy.Placeholder;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import me.redtea.namesmanager.service.crime.CrimeService;
import org.bukkit.OfflinePlayer;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CrimeColorPlaceholder implements Placeholder {
    private final UserRepo userRepo;

    private final CrimeService crimeService;

    @Override
    public String prefix() {
        return "crimecolor";
    }

    @Override
    public String render(OfflinePlayer player, String[] args) {
        UserDto userDto = userRepo.get(player.getUniqueId()).orElseThrow();
        return crimeService.crimeByKills(userDto.getKills()).getColor().toString();
    }
}
