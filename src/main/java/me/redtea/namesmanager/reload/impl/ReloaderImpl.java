package me.redtea.namesmanager.reload.impl;

import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.reload.Reloadable;
import me.redtea.namesmanager.reload.Reloader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ReloaderImpl implements Reloader {
    private final Injector injector;

    private final Set<Reloadable> reloadables = new HashSet<>();

    @Override
    public void add(Reloadable... reloadables) {
        this.reloadables.addAll(List.of(reloadables));
    }

    @Override
    public <T extends Reloadable> void add(Class<T> type) {
        this.reloadables.add(injector.getInstance(type));
    }

    @Override
    public void reload() {
        reloadables.forEach(Reloadable::reload);
    }

    @Override
    public void close() {
        reloadables.forEach(Reloadable::close);
    }
}
