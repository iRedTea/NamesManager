package me.redtea.namesmanager.placeholder.strategy;

import org.bukkit.OfflinePlayer;

public interface Placeholder {
    String prefix();

    String render(OfflinePlayer player, String[] args);
}
