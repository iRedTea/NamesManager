package me.redtea.namesmanager.service.rtp.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.redtea.namesmanager.hook.command.CommandHook;
import me.redtea.namesmanager.service.config.ConfigService;
import me.redtea.namesmanager.service.rtp.RTPService;
import org.bukkit.entity.Player;

@Singleton
public class RTPServiceImpl extends CommandHook implements RTPService {
    @Inject
    public RTPServiceImpl(ConfigService config) {
        super(config.rtpCommand());
    }

    @Override
    public void teleport(Player player) {
        dispatchCommand(player);
    }
}
