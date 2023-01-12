package me.redtea.namesmanager.service.ban.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.redtea.namesmanager.hook.command.CommandHook;
import me.redtea.namesmanager.service.ban.BanService;
import me.redtea.namesmanager.service.config.ConfigService;
import org.bukkit.OfflinePlayer;

@Singleton
public class BanServiceImpl extends CommandHook implements BanService {
    @Inject
    public BanServiceImpl(ConfigService config) {
        super(config.banCommand());
    }

    @Override
    public void ban(OfflinePlayer player) {
        dispatchCommand(player);
    }
}
