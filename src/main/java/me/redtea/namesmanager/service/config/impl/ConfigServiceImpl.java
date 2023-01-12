package me.redtea.namesmanager.service.config.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.service.config.ConfigService;
import org.bukkit.plugin.Plugin;

@Singleton
public class ConfigServiceImpl implements ConfigService {
    private final Plugin plugin;

    private String maleDisplay;

    private String femaleDisplay;

    private String rtpCommand;

    private String banCommand;

    @Inject
    public ConfigServiceImpl(Plugin plugin) {
        this.plugin = plugin;
        init();
    }

    @Override
    public void init() {
        plugin.saveDefaultConfig();
        var config = plugin.getConfig();
        maleDisplay = config.getString("gender.maleDisplay");
        femaleDisplay = config.getString("gender.femaleDisplay");
        rtpCommand = config.getString("commands.rtp");
        banCommand = config.getString("commands.ban");
    }

    public String genderName(Gender gender) {
        return switch (gender) {
            case MALE -> maleDisplay;
            default -> femaleDisplay;
        };
    }

    @Override
    public String maleDisplay() {
        return maleDisplay;
    }

    @Override
    public String femaleDisplay() {
        return femaleDisplay;
    }

    @Override
    public String rtpCommand() {
        return rtpCommand;
    }

    @Override
    public String banCommand() {
        return banCommand;
    }

    @Override
    public void close() {
        plugin.saveConfig();
    }

    @Override
    public void reload() {
        close();
        plugin.reloadConfig();
        init();
    }
}
