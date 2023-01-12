package me.redtea.namesmanager.hook.skinsrestorer.impl;


import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.redtea.namesmanager.hook.skinsrestorer.SkinsRestorerHook;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import net.skinsrestorer.api.property.IProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class SkinsRestorerHookImpl implements SkinsRestorerHook {
    private final SkinsRestorerAPI api;

    private final Map<String, IProperty> properties = new HashMap<>();

    @Inject
    public SkinsRestorerHookImpl(Plugin plugin) {
        if (Bukkit.getPluginManager().isPluginEnabled("SkinsRestorer")) {
            api = SkinsRestorerAPI.getApi();
        } else {
            Bukkit.getLogger().info("SkinsRestorer is required for this plugin to work!");
            Bukkit.getLogger().info("Please, install latest version of SkinsRestorer.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            api = null;
        }
    }

    private IProperty getProperty(String url) throws SkinRequestException {
        return properties.getOrDefault(url, properties.put(url, api.genSkinUrl(url, null)));
    }



    @Override
    public void set(String player, String url) throws SkinRequestException {
        IProperty property = getProperty(url);
        api.setSkinData(player, property);
        try {
            api.applySkin(new PlayerWrapper(Bukkit.getPlayer(player)), property);
        } catch (Throwable ignored) {}

    }

    @Override
    public void setByName(String player, String skinName) throws SkinRequestException {
        api.setSkin(player, skinName);

        try {
            api.applySkin(new PlayerWrapper(Bukkit.getPlayer(player)), skinName);
        } catch (Throwable ignored) {}
    }

    @Override
    public ItemStack headByName(String name) {
        ItemStack itemStack = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta meta = (SkullMeta) Objects.requireNonNull(itemStack).getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public ItemStack head(String url) throws SkinRequestException {
        ItemStack itemStack = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta meta = (SkullMeta) Objects.requireNonNull(itemStack).getItemMeta();
        SkullUtils.applySkin(meta, getProperty(url).getValue());
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
