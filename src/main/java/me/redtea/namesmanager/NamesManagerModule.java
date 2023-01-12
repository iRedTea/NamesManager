package me.redtea.namesmanager;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.component.Container;
import me.redtea.namesmanager.factory.UserFactory;
import me.redtea.namesmanager.gui.gender.GenderGui;
import me.redtea.namesmanager.gui.gender.impl.GenderGuiImpl;
import me.redtea.namesmanager.gui.name.NameGui;
import me.redtea.namesmanager.gui.name.impl.NameGuiImpl;
import me.redtea.namesmanager.gui.skin.SkinGui;
import me.redtea.namesmanager.gui.skin.impl.SkinGuiImpl;
import me.redtea.namesmanager.hook.skinsrestorer.SkinsRestorerHook;
import me.redtea.namesmanager.hook.skinsrestorer.impl.SkinsRestorerHookImpl;
import me.redtea.namesmanager.placeholder.handler.PlaceholderHandler;
import me.redtea.namesmanager.placeholder.handler.impl.PlaceholderHandlerImpl;
import me.redtea.namesmanager.repo.impl.crime.CrimeRepo;
import me.redtea.namesmanager.repo.impl.crime.impl.CrimeRepoImpl;
import me.redtea.namesmanager.repo.impl.skin.SkinRepo;
import me.redtea.namesmanager.repo.impl.skin.SkinRepoImpl;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import me.redtea.namesmanager.repo.impl.user.UserRepoImpl;
import me.redtea.namesmanager.schema.user.UserSchemaStrategy;
import me.redtea.namesmanager.service.ban.BanService;
import me.redtea.namesmanager.service.ban.impl.BanServiceImpl;
import me.redtea.namesmanager.service.config.ConfigService;
import me.redtea.namesmanager.service.config.impl.ConfigServiceImpl;
import me.redtea.namesmanager.service.crime.CrimeService;
import me.redtea.namesmanager.service.crime.impl.CrimeServiceImpl;
import me.redtea.namesmanager.service.gui.GuiLockService;
import me.redtea.namesmanager.service.gui.impl.GuiLockServiceImpl;
import me.redtea.namesmanager.service.name.NameService;
import me.redtea.namesmanager.service.name.impl.NameServiceImpl;
import me.redtea.namesmanager.service.rtp.RTPService;
import me.redtea.namesmanager.service.rtp.impl.RTPServiceImpl;
import me.redtea.namesmanager.service.skin.SkinService;
import me.redtea.namesmanager.service.skin.impl.SkinServiceImpl;
import me.redtea.namesmanager.service.user.UserService;
import me.redtea.namesmanager.service.user.impl.UserServiceImpl;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class NamesManagerModule extends AbstractModule {
    private final NamesManager plugin;

    @Override
    protected void configure() {
        bind(Plugin.class).toInstance(plugin);
        bind(NamesManager.class).toInstance(plugin);
        bind(Container.class).toInstance(plugin.initMessages());
        bind(UserFactory.class).to(UserRepoImpl.class);
        bind(GenderGui.class).to(GenderGuiImpl.class);
        bind(NameGui.class).to(NameGuiImpl.class);
        bind(SkinGui.class).to(SkinGuiImpl.class);
        bind(SkinsRestorerHook.class).to(SkinsRestorerHookImpl.class);
        bind(PlaceholderHandler.class).to(PlaceholderHandlerImpl.class);
        bind(CrimeRepo.class).to(CrimeRepoImpl.class);
        bind(SkinRepo.class).to(SkinRepoImpl.class);
        bind(UserRepo.class).to(UserRepoImpl.class);
        bind(UserSchemaStrategy.class).toInstance(plugin.initUserSchema());
        bind(BanService.class).to(BanServiceImpl.class);
        bind(ConfigService.class).to(ConfigServiceImpl.class);
        bind(CrimeService.class).to(CrimeServiceImpl.class);
        bind(NameService.class).to(NameServiceImpl.class);
        bind(RTPService.class).to(RTPServiceImpl.class);
        bind(SkinService.class).to(SkinServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(GuiLockService.class).to(GuiLockServiceImpl.class);
    }
}