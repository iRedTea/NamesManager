package me.redtea.namesmanager.hook.skinsrestorer;

import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.inventory.ItemStack;

public interface SkinsRestorerHook {
    void set(String player, String url) throws SkinRequestException;

    void setByName(String player, String skinName) throws SkinRequestException;

    ItemStack headByName(String name);

    ItemStack head(String url) throws SkinRequestException;
}
