package me.redtea.namesmanager.hook.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public abstract class CommandHook {
    protected final String command;

    protected CommandHook(String command) {
        this.command = command;
    }

    protected void dispatchCommand(String player, String placeholder) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll(placeholder, player));
    }

    protected void dispatchCommand(String player) {
        dispatchCommand(player, "%player%");
    }

    protected void dispatchCommand(OfflinePlayer player) {
        dispatchCommand(player.getName());
    }
}
