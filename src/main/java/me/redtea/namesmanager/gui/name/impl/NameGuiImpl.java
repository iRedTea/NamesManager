package me.redtea.namesmanager.gui.name.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.redtea.namesmanager.component.Container;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.gui.name.NameGui;
import me.redtea.namesmanager.gui.skin.SkinGui;
import me.redtea.namesmanager.service.gui.GuiLockService;
import me.redtea.namesmanager.service.name.NameService;
import me.redtea.namesmanager.service.user.UserService;
import org.bukkit.entity.Player;

@Singleton
public class NameGuiImpl implements NameGui {
    private final Container messages;

    private final NameService nameService;

    private final UserService userService;

    private final SkinGui skinGui;

    private final GuiLockService guiLockService;

    @Inject
    public NameGuiImpl(Container messages, NameService nameService, UserService userService, SkinGui skinGui, GuiLockService guiLockService) {
        this.messages = messages;
        this.nameService = nameService;
        this.userService = userService;
        this.skinGui = skinGui;
        this.guiLockService = guiLockService;

    }

    @Override
    public void open(Player player, Gender gender) {
        var gui = Gui.gui()
                .title(messages.get("gui.name.title"))
                .rows(3)
                .disableAllInteractions()
                .create();
        guiLockService.lock(player);
        for (int i = 11; i <= 15; i++) {
            String name = nameService.randName(gender);
            GuiItem guiItem = ItemBuilder.from(messages.get("gui.name.item.material").toMaterial())
                    .name(messages.get("gui.name.item.name").replace("%name%", name))
                    .lore(messages.get("gui.name.item.lore").toComponentList())
                    .asGuiItem(event -> {
                        userService.createIfNotExists(player, name, gender);
                        skinGui.open(player, gender);
                    });
            gui.setItem(i, guiItem);
        }

        GuiItem signItem = ItemBuilder.from(messages.get("gui.name.sign.material").toMaterial())
                .name(messages.get("gui.name.sign.name"))
                .lore(messages.get("gui.name.sign.lore").toComponentList())
                .asGuiItem();
        gui.setItem(4, signItem);

        guiLockService.unlock(player);
        gui.open(player);
        guiLockService.lock(player);
    }


}
