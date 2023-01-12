package me.redtea.namesmanager.gui.name;

import me.redtea.namesmanager.domain.Gender;
import org.bukkit.entity.Player;

public interface NameGui {
    void open(Player player, Gender gender);
}
