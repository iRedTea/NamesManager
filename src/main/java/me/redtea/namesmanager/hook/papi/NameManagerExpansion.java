package me.redtea.namesmanager.hook.papi;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.redtea.namesmanager.placeholder.handler.PlaceholderHandler;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class NameManagerExpansion extends PlaceholderExpansion {
    private final PlaceholderHandler placeholderHandler;


    @Override
    public @NotNull String getIdentifier() {
        return "namesmanager";
    }

    @Override
    public @NotNull String getAuthor() {
        return "itzRedTea";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        return placeholderHandler.request(player, params.split("_"));
    }
}
