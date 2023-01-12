package me.redtea.namesmanager.placeholder.handler.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.component.Container;
import me.redtea.namesmanager.placeholder.handler.PlaceholderHandler;
import me.redtea.namesmanager.placeholder.strategy.Placeholder;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PlaceholderHandlerImpl implements PlaceholderHandler {
    private final Set<Placeholder> registered = new HashSet<>();

    private final Container messages;

    @Override
    public void register(Placeholder placeholder) {
        registered.add(placeholder);
    }

    @Override
    public String request(OfflinePlayer player, String[] args) {
        try {
            String[] paramsForPlaceholder = Arrays.copyOfRange(args, 1, args.length);
            return registered.stream().filter(placeholder -> placeholder.prefix().equalsIgnoreCase(args[0]))
                    .findAny()
                    .orElseThrow()
                    .render(player, paramsForPlaceholder);
        } catch (Throwable ex) {
            return messages.get("undefined").toParsedString();
        }

    }


}
