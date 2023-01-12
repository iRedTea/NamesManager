package me.redtea.namesmanager.handler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.service.gui.GuiLockService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InventoryHandler implements Listener {
    private final GuiLockService guiLockService;

    private final Plugin plugin;

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        //If i do this without BukkitRunnable, this throws to me StackOverflowException
        if(!guiLockService.canCloseGui(event.getPlayer())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        event.getPlayer().openInventory(event.getInventory());
                    } catch (Throwable ex) {
                        Bukkit.getLogger().warning("Could not open inventory for player " + event.getPlayer() + ", ex: "
                                + ex.getMessage());
                    }

                }
            }.runTaskLater(plugin, 5);
        }

    }
}
