package me.redtea.namesmanager.repo.impl.crime.impl;

import com.google.inject.Inject;
import me.redtea.namesmanager.domain.CrimeDto;
import me.redtea.namesmanager.repo.YamlRepo;
import me.redtea.namesmanager.repo.impl.crime.CrimeRepo;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CrimeRepoImpl extends YamlRepo<String, CrimeDto> implements CrimeRepo {
    private CrimeDto defaultCrime;

    @Inject
    public CrimeRepoImpl(Plugin plugin) {
        super(new File(plugin.getDataFolder(), "crimes.yml").toPath(), plugin);
        init();
    }

    public void init() {
        var conf = initFile();
        for (String key : conf.getKeys(false)) {
            var color = ChatColor.valueOf(conf.getString(key + ".color"));
            int kills = conf.getInt(key + ".kills");
            CrimeDto crimeDto = new CrimeDto(key, kills, color);
            if (key.equalsIgnoreCase("default")) defaultCrime = crimeDto;
            data.put(key, crimeDto);
        }
    }

    @Override
    public Optional<CrimeDto> findByKills(int kill) throws NoSuchElementException {
        if (kill <= 0) return Optional.ofNullable(defaultCrime);
        return all().stream().filter(it -> it.getKills() == kill).findAny();
    }

    @Override
    public CrimeDto defaultCrime() {
        return defaultCrime;
    }
}
