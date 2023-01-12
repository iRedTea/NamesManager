package me.redtea.namesmanager.service.skin.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.SkinDto;
import me.redtea.namesmanager.hook.skinsrestorer.SkinsRestorerHook;
import me.redtea.namesmanager.repo.impl.skin.SkinRepo;
import me.redtea.namesmanager.service.skin.SkinService;
import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Singleton
public class SkinServiceImpl implements SkinService {
    private final SkinRepo repo;
    private final SkinsRestorerHook skinsRestorerHook;
    private final Map<String, ItemStack> heads = new HashMap<>();
    private final Set<SkinDto> maleSkins = new HashSet<>();
    private final Set<SkinDto> femaleSkins = new HashSet<>();

    @Inject
    public SkinServiceImpl(SkinRepo repo, SkinsRestorerHook skinsRestorerHook) {
        this.repo = repo;
        this.skinsRestorerHook = skinsRestorerHook;
        init();
    }

    @Override
    public void set(String player, String url) {
        try {
            skinsRestorerHook.set(player, url);
        } catch (SkinRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<SkinDto> skinsByGender(Gender gender) {
        return switch (gender) {
            case MALE -> maleSkins;
            default -> femaleSkins;
        };
    }

    @Override
    public ItemStack headByName(String name) {
        return heads.getOrDefault(name, skinsRestorerHook.headByName(name));
    }

    @Override
    public ItemStack head(String url) throws SkinRequestException {
        return skinsRestorerHook.head(url);
    }

    @Override
    @SneakyThrows
    public void setByName(String player, String name) {
        skinsRestorerHook.setByName(player, name);
    }

    @Override
    public void init() {
        for (SkinDto skinDto : repo.all()) {
            if (skinDto.getGender().equals(Gender.MALE)) maleSkins.add(skinDto);
            else femaleSkins.add(skinDto);
            try {
                skinsRestorerHook.set(skinDto.getName(), skinDto.getUrl());
                heads.put(skinDto.getName(), skinsRestorerHook.headByName(skinDto.getName()));
            } catch (Exception e) {
                Bukkit.getLogger().severe("Could not load skin " + skinDto.getName());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        maleSkins.clear();
        femaleSkins.clear();
        heads.clear();
    }
}
