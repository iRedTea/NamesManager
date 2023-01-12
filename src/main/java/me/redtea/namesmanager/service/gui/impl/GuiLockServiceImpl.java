package me.redtea.namesmanager.service.gui.impl;

import com.google.inject.Singleton;
import me.redtea.namesmanager.service.gui.GuiLockService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.HumanEntity;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class GuiLockServiceImpl implements GuiLockService {
    private final Set<String> players = new HashSet<>();

    private String content(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    @Override
    public void lock(HumanEntity player) {
        players.add(player.getName());
    }

    @Override
    public void unlock(HumanEntity player) {
        players.remove(player.getName());
    }

    @Override
    public boolean canCloseGui(HumanEntity player) {
        return !players.contains(player.getName());
    }
}
