package me.redtea.namesmanager.gui.gender.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.redtea.namesmanager.component.Container;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.gui.gender.GenderGui;
import me.redtea.namesmanager.gui.name.NameGui;
import me.redtea.namesmanager.service.gui.GuiLockService;
import me.redtea.namesmanager.service.skin.SkinService;
import org.bukkit.entity.Player;

@Singleton
public class GenderGuiImpl implements GenderGui {
    private final Container messages;

    private final SkinService skinService;

    private final NameGui nameGui;

    private final GuiLockService guiLockService;


    @Inject
    public GenderGuiImpl(Container messages, SkinService skinService, NameGui nameGui, GuiLockService guiLockService) {
        this.messages = messages;
        this.skinService = skinService;
        this.nameGui = nameGui;
        this.guiLockService = guiLockService;

    }

    private void setItems(Gui gui) {

        GuiItem signItem = ItemBuilder.from(messages.get("gui.gender.sign.material").toMaterial())
                .name(messages.get("gui.gender.sign.name"))
                .lore(messages.get("gui.gender.sign.lore").toComponentList())
                .asGuiItem();

        GuiItem maleItem = ItemBuilder.from(skinService.headByName(messages.get("gui.gender.male.headOwner").toString()))
                .name(messages.get("gui.gender.male.name"))
                .lore(messages.get("gui.gender.male.lore").toComponentList())
                .asGuiItem(event -> {
                    nameGui.open((Player) event.getWhoClicked(), Gender.MALE);
                });

        GuiItem femaleItem = ItemBuilder.from(skinService.headByName(messages.get("gui.gender.female.headOwner").toString()))
                .name(messages.get("gui.gender.female.name"))
                .lore(messages.get("gui.gender.female.lore").toComponentList())
                .asGuiItem(event -> {
                    nameGui.open((Player) event.getWhoClicked(), Gender.FEMALE);
                });
        gui.setItem(13, signItem);
        gui.setItem(11, maleItem);
        gui.setItem(15, femaleItem);
    }

    @Override
    public void open(Player player) {
        var gui = Gui.gui()
                .title(messages.get("gui.gender.title"))
                .rows(3)
                .disableAllInteractions()
                .create();
        setItems(gui);

        gui.open(player);
        guiLockService.lock(player);
    }
}
