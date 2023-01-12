package me.redtea.namesmanager.service.skin;

import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.SkinDto;
import me.redtea.namesmanager.reload.Reloadable;
import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public interface SkinService extends Reloadable {
    void set(String player, String name);

    Collection<SkinDto> skinsByGender(Gender gender);

    ItemStack headByName(String name);

    ItemStack head(String url) throws SkinRequestException;

    default ItemStack headByName(SkinDto skinDto) {
        return headByName(skinDto.getUrl());
    }

    default void set(Player player, SkinDto skinDto) {
        set(player.getName(), skinDto.getName());
    }

    default void set(Player player, String url) {
        set(player.getName(), url);
    }

    void setByName(String player, String name);
}
