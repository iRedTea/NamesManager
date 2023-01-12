package me.redtea.namesmanager.service.crime;

import me.redtea.namesmanager.domain.CrimeDto;
import me.redtea.namesmanager.domain.UserDto;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface CrimeService {
    //If not exists returns nearest minimum
    CrimeDto crimeByKills(int kills);

    //Raises the level of criminalism of the player
    UserDto increase(UUID uuid);

    default UserDto increase(Player player) {
        return increase(player.getUniqueId());
    }
}
