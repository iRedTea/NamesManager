package me.redtea.namesmanager;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.redtea.namesmanager.component.Container;
import me.redtea.namesmanager.handler.InventoryHandler;
import me.redtea.namesmanager.handler.PlayerHandler;
import me.redtea.namesmanager.hook.papi.NameManagerExpansion;
import me.redtea.namesmanager.placeholder.handler.PlaceholderHandler;
import me.redtea.namesmanager.placeholder.strategy.impl.CrimeColorPlaceholder;
import me.redtea.namesmanager.placeholder.strategy.impl.GenderPlaceholder;
import me.redtea.namesmanager.placeholder.strategy.impl.NamePlaceholder;
import me.redtea.namesmanager.reload.Reloader;
import me.redtea.namesmanager.reload.impl.ReloaderImpl;
import me.redtea.namesmanager.repo.impl.crime.CrimeRepo;
import me.redtea.namesmanager.repo.impl.skin.SkinRepo;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import me.redtea.namesmanager.schema.user.UserSchemaStrategy;
import me.redtea.namesmanager.schema.user.impl.UserJsonSchemaStrategy;
import me.redtea.namesmanager.service.config.ConfigService;
import me.redtea.namesmanager.service.name.NameService;
import me.redtea.namesmanager.service.skin.SkinService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Path;

public final class NamesManager extends JavaPlugin {
    private Reloader reloader;

    private Injector injector;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new NamesManagerModule(this));

        var playerHandler = injector.getInstance(PlayerHandler.class);
        var inventoryHandler = injector.getInstance(InventoryHandler.class);

        Bukkit.getPluginManager().registerEvents(inventoryHandler, this);
        Bukkit.getPluginManager().registerEvents(playerHandler, this);

        reloader = new ReloaderImpl(injector);

        reloader.add(CrimeRepo.class);
        reloader.add(SkinRepo.class);
        reloader.add(UserRepo.class);
        reloader.add(UserSchemaStrategy.class);
        reloader.add(ConfigService.class);
        reloader.add(NameService.class);
        reloader.add(SkinService.class);

        registerPlaceholders();

    }

    public void registerPlaceholders() {
        var papiExpansion = injector.getInstance(NameManagerExpansion.class);
        papiExpansion.register();
        var placeholderHandler = injector.getInstance(PlaceholderHandler.class);
        placeholderHandler.register(injector.getInstance(CrimeColorPlaceholder.class));
        placeholderHandler.register(injector.getInstance(GenderPlaceholder.class));
        placeholderHandler.register(injector.getInstance(NamePlaceholder.class));
    }

    @Override
    public void onDisable() {
        reloader.close();
    }

    public void onReload() {
        reloader.reload();
    }

    public UserSchemaStrategy initUserSchema() {
        return new UserJsonSchemaStrategy(Path.of(getDataFolder().getPath(), "players"));
    }


    public Container initMessages() {
        return new Container(new File(getDataFolder(), "messages.yml"), this);
    }
}
