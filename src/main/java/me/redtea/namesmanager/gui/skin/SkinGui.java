package me.redtea.namesmanager.gui.skin;

import me.redtea.namesmanager.domain.Gender;
import org.bukkit.entity.Player;

public interface SkinGui {
    void open(Player player, Gender gender);
}
