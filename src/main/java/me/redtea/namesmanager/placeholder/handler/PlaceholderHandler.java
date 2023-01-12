package me.redtea.namesmanager.placeholder.handler;

import me.redtea.namesmanager.placeholder.strategy.Placeholder;
import org.bukkit.OfflinePlayer;

public interface PlaceholderHandler {
    void register(Placeholder placeholder);

    String request(OfflinePlayer player, String[] args);
}
