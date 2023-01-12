package me.redtea.namesmanager.handler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.gui.gender.GenderGui;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import me.redtea.namesmanager.service.ban.BanService;
import me.redtea.namesmanager.service.crime.CrimeService;
import me.redtea.namesmanager.service.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.NoSuchElementException;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PlayerHandler implements Listener {
    private final UserService userService;

    private final GenderGui genderGui;

    private final UserRepo userRepo;

    private final CrimeService crimeService;

    private final BanService banService;

    private final Plugin plugin;

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        if(userRepo.get(event.getPlayer().getUniqueId()).isEmpty()) {
            genderGui.open(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onQuit(PlayerJoinEvent event) {
        userRepo.removeFromCache(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getPlayer().getKiller() != null) crimeService.increase(event.getPlayer().getKiller());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        try {
            userService.removeIfExists(event.getPlayer());
        } catch (NoSuchElementException e) {
            Bukkit.getLogger().warning("Could not remove data of " + event.getPlayer().getName()
                    + " because player data is empty");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                banService.ban(event.getPlayer());
            }
        }.runTaskLater(plugin, 5);

    }

}
