package me.redtea.namesmanager.service.name.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.service.name.NameService;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class NameServiceImpl implements NameService {
    private final List<String> maleFirstNames = new ArrayList<>();

    private final List<String> maleLastNames = new ArrayList<>();

    private final List<String> femaleFirstNames = new ArrayList<>();

    private final List<String> femaleLastNames = new ArrayList<>();

    private final File namesFile;

    private final Plugin plugin;

    @Inject
    public NameServiceImpl(Plugin plugin) {
        this.namesFile = new File(plugin.getDataFolder(), "names.yml");
        this.plugin = plugin;
        init();
    }

    public void init() {
        if(!namesFile.exists()) plugin.saveResource("names.yml", false);

        FileConfiguration conf = YamlConfiguration.loadConfiguration(namesFile);

        maleFirstNames.addAll(conf.getStringList("male.first"));
        maleLastNames.addAll(conf.getStringList("male.last"));
        femaleFirstNames.addAll(conf.getStringList("female.first"));
        femaleLastNames.addAll(conf.getStringList("female.last"));
    }


    private String randFromList(List<String> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
    }


    private String randMaleName() {
        return String.format("%s %s",
                randFromList(maleFirstNames),
                randFromList(maleLastNames));
    }


    private String randFemaleName() {
        return String.format("%s %s",
                randFromList(femaleFirstNames),
                randFromList(femaleLastNames));
    }

    @Override
    public String randName(Gender gender) {
        return switch (gender) {
            case MALE -> randMaleName();
            default -> randFemaleName();
        };
    }

    @Override
    public void close() {
        maleFirstNames.clear();
        maleLastNames.clear();
        femaleFirstNames.clear();
        femaleLastNames.clear();
    }
}
