package me.redtea.namesmanager.service.gui;

import org.bukkit.entity.HumanEntity;

public interface GuiLockService {
    void lock(HumanEntity player);

    void unlock(HumanEntity player);

    boolean canCloseGui(HumanEntity player);

}
