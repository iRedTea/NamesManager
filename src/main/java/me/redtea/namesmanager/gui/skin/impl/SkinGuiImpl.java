package me.redtea.namesmanager.gui.skin.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.redtea.namesmanager.component.Container;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.SkinDto;
import me.redtea.namesmanager.gui.skin.SkinGui;
import me.redtea.namesmanager.repo.impl.skin.SkinRepo;
import me.redtea.namesmanager.service.gui.GuiLockService;
import me.redtea.namesmanager.service.rtp.RTPService;
import me.redtea.namesmanager.service.skin.SkinService;
import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SkinGuiImpl implements SkinGui {
    private final Container messages;

    private final SkinService skinService;

    private final GuiLockService guiLockService;

    private final RTPService rtpService;


    @SneakyThrows
    private Gui initGui(Gui gui, Gender gender) {
        int i = 1;

        for (SkinDto skinDto : skinService.skinsByGender(gender)) {
            GuiItem guiItem = ItemBuilder.from(skinService.head(skinDto.getUrl()))
                    .name(messages.get("gui.skin.item.name").replace("%num%", i))
                    .lore(messages.get("gui.skin.item.lore"))
                    .asGuiItem(event -> {
                        skinService.setByName(event.getWhoClicked().getName(), skinDto.getName());
                        guiLockService.unlock(event.getWhoClicked());
                        event.getWhoClicked().closeInventory();
                        rtpService.teleport((Player) event.getWhoClicked());
                        messages.get("registerFinished").send(event.getWhoClicked());
                    });
            gui.setItem(i-1, guiItem);
            i++;
        }

        return gui;
    }



    @Override
    public void open(Player player, Gender gender) {
        var gui = initGui(Gui.gui()
                .title(messages.get("gui.skin.title"))
                .rows(3)
                .disableAllInteractions()
                .create(), gender);

        guiLockService.unlock(player);
        gui.open(player);
        guiLockService.lock(player);
    }

}
